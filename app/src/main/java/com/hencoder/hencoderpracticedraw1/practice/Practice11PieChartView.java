package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Practice11PieChartView extends View {

    private static final int DEFUALT_RADIUS = 152;

    private List<Data> datas;
    private Paint mPaint;
    private RectF rectF;
    private float mMaxData;      // 存占比最大的那个数

    float startAngle = 0f; // 开始的角度
    float sweepAngle;      // 扫过的角度
    float lineAngle;       // 当前扇形一半的角度

    float lineStartX = 0f; // 直线开始的X坐标
    float lineStartY = 0f; // 直线开始的Y坐标
    float lineEndX;        // 直线结束的X坐标
    float lineEndY;        // 直线结束的Y坐标

    public Practice11PieChartView(Context context) {
        super(context);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        datas = new ArrayList<>();
        Data data = new Data("Gingerbread", 15.0f, Color.CYAN);
        datas.add(data);
        data = new Data("Ice Cream Sandwich", 15.0f, Color.GRAY);
        datas.add(data);
        data = new Data("Jelly Bean", 45.0f, Color.GREEN);
        datas.add(data);
        data = new Data("KitKat", 115.0f, Color.BLUE);
        datas.add(data);
        data = new Data("Lollipop", 125.0f, Color.RED);
        datas.add(data);
        data = new Data("Marshmallow", 40.0f, Color.YELLOW);
        datas.add(data);
        data = new Data("Froyo", 5.0f, Color.DKGRAY);
        datas.add(data);

        for (Data d : datas) {
            mMaxData = Math.max(mMaxData, d.getNumber());
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(20);
        rectF = new RectF(-150, -150, 150, 150);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图
        // 将画布(0，0)坐标点移到画布的中心
        canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        startAngle = 0f;
        for (Data data : datas) {
            mPaint.setColor(data.getColor());
            sweepAngle = data.getNumber();
            lineAngle = startAngle + sweepAngle / 2;
            // lineAngle / 180 * Math.PI 角度换算为弧度
            lineStartX = DEFUALT_RADIUS * (float) Math.cos(lineAngle / 180 * Math.PI);
            lineStartY = DEFUALT_RADIUS * (float) Math.sin(lineAngle / 180 * Math.PI);
            lineEndX = (DEFUALT_RADIUS + 20) * (float) Math.cos(lineAngle / 180 * Math.PI);
            lineEndY = (DEFUALT_RADIUS + 20) * (float) Math.sin(lineAngle / 180 * Math.PI);
            if (data.getNumber() == mMaxData) {
                canvas.save();
                canvas.translate(lineStartX * 0.1f, lineStartY * 0.1f);
                canvas.drawArc(rectF, startAngle, sweepAngle, true, mPaint);
            } else {
                canvas.drawArc(rectF, startAngle, sweepAngle - 1.0f, true, mPaint);
            }
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, mPaint);
            if (lineAngle > 90 && lineAngle <= 270) {
                canvas.drawLine(lineEndX, lineEndY, lineEndX - 50, lineEndY, mPaint);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawText(data.getName(), lineEndX - 50 - 10 - mPaint.measureText(data.getName()), lineEndY, mPaint);
            } else {
                canvas.drawLine(lineEndX, lineEndY, lineEndX + 50, lineEndY, mPaint);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawText(data.getName(), lineEndX + 50 + 10, lineEndY, mPaint);
            }
            if (data.getNumber() == mMaxData) {
                canvas.restore();
            }
            startAngle += sweepAngle;
        }
    }

    class Data {
        private String name;
        private float number;
        private int color;

        public Data(String name, float number, int color) {
            this.name = name;
            this.number = number;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getNumber() {
            return number;
        }

        public void setNumber(float number) {
            this.number = number;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }
}
