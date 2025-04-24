package com.moneylover.ui.custom.lineview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;


public class LineView extends View {
    private float startX, startY, endX, endY;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

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
        canvas.drawLine(startX, startY, endX, endY, paint);
    }
}

