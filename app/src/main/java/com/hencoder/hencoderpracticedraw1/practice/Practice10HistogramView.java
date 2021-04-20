package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {

    private String[] textStr = {"Froyo", "GB", "ICS", "JB", "KitKat", "L", "M"};
    private int[] textData = {2, 8, 10, 100, 200, 280, 150};
    private Paint mPaint;

    private static final int DEFAULT_BOTTOM = 660;
    private static final int DEFAULT_GREEN_WIDTH = 80;

    public Practice10HistogramView(Context context) {
        super(context, null);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(160, 60);
        path.lineTo(160, DEFAULT_BOTTOM);
        path.rLineTo(800, 0);

        canvas.drawPath(path, mPaint);

        int firstX = 170;
        int intervalWidth = 20;
        int textHeight = 675;

        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);

        for (int index = 0; index < textData.length; index++) {
            canvas.drawRect(firstX + DEFAULT_GREEN_WIDTH * index + index * intervalWidth, DEFAULT_BOTTOM - textData[index], firstX + index * intervalWidth + (index + 1) * DEFAULT_GREEN_WIDTH, DEFAULT_BOTTOM, mPaint);
        }

        mPaint.setColor(Color.WHITE);
        for (int index = 0; index < textStr.length; index++) {
            canvas.drawText(textStr[index], (float) (firstX + (index * intervalWidth) + (index * DEFAULT_GREEN_WIDTH) + (0.5 * (DEFAULT_GREEN_WIDTH - mPaint.measureText(textStr[index])))), textHeight, mPaint);
        }
    }
}
