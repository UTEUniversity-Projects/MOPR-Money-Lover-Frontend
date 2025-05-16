package com.moneylover.ui.custom.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import lombok.Setter;

public class ArcProgressView extends View {

    private Paint backgroundPaint;
    private Paint progressPaint;
    private RectF arcRect;

    private int strokeWidth = 20;
    @Setter
    private int max = 100;
    private float progress = 0;
    private float animatedProgress = 0;

    public ArcProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.LTGRAY);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND); // QUAN TRỌNG

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(Color.parseColor("#4CAF50"));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND); // QUAN TRỌNG
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (arcRect == null) {
            float padding = strokeWidth / 2f + 10;
            arcRect = new RectF(padding, padding, getWidth() - padding, getHeight() * 2 - padding);
        }

        // Vẽ background
        canvas.drawArc(arcRect, 180, 180, false, backgroundPaint);

        // Vẽ progress
        float sweepAngle = 180 * animatedProgress / max;
        canvas.drawArc(arcRect, 180, sweepAngle, false, progressPaint);

        // Vẽ chấm tròn tại vị trí progress hiện tại
        float angleRad = (float) Math.toRadians(180 + sweepAngle); // tính từ 180°

        float radius = arcRect.width() / 2f;
        float cx = arcRect.centerX() + (float) (radius * Math.cos(angleRad));
        float cy = arcRect.centerY() + (float) (radius * Math.sin(angleRad));

// Vẽ viền xanh
        Paint outerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerCirclePaint.setColor(Color.parseColor("#4CAF50"));
        canvas.drawCircle(cx, cy, strokeWidth + 5, outerCirclePaint);


    // Vẽ tâm trắng
        Paint innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint.setColor(Color.WHITE);
        canvas.drawCircle(cx, cy, strokeWidth - 5, innerCirclePaint);

    }

    public void setProgressAnimated(int value) {
        ValueAnimator animator = ValueAnimator.ofFloat(animatedProgress, value);
        animator.setDuration(1000); // 1s
        animator.addUpdateListener(animation -> {
            animatedProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }

}
