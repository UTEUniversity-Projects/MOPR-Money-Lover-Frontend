package com.moneylover.ui.custom.lineview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;


public class LineView extends View {
    private float startX, startY, endX, endY;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float progress = 0f;

    public LineView(Context context, float startX, float startY, float endX, float endY, int color) {
        super(context);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        paint.setColor(color);
        paint.setStrokeWidth(4f);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float dx = startX + (endX - startX) * progress;
        float dy = startY + (endY - startY) * progress;
        canvas.drawLine(startX, startY, dx, dy, paint);
    }

    public void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(500);
        animator.addUpdateListener(animation -> {
            progress = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }
}

