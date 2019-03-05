package com.zong.pwdview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * author: zzj
 * time: 2019/2/27
 * deprecated :
 */
public class PwdEditView extends AppCompatEditText {
    int mPaintSize = 4;
    boolean isPwdModel = true;
    int mPwdBorderStyle = 0;//0背景为方框
    int mColor = Color.parseColor("#D81B60");
    int mPwdFocusColor;
    int mNormalColor;
    public PwdEditView(Context context) {
        super(context);
    }
    public PwdEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (widthMeasureSpec == MeasureSpec.AT_MOST | heightMeasureSpec == MeasureSpec.AT_MOST) {
            setMeasuredDimension(120, 120);
        } else {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            int size = Math.max(measuredWidth, measuredHeight);
            setMeasuredDimension(size, size); // 保存测得的尺寸
        }


    }

    public void init(boolean pwdModel, int pwdBorderStyle, int normalColor, int focusColor) {
        this.isPwdModel = pwdModel;
        this.mPwdBorderStyle = pwdBorderStyle;
        this.mNormalColor = normalColor;
        this.mPwdFocusColor = focusColor;
    }

    /**
     * 设置边框颜色
     * @param mColor
     */
    public void setBorderColor(int mColor) {
        this.mColor = mColor;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorder(canvas);
        drawPoint(canvas);
    }


    /**
     * 密码模式时绘制原点
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        if (isPwdModel) {
            Editable text = getText();
            if (!TextUtils.isEmpty(text)) {
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.parseColor("#555555"));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 4, paint);
            }
        }
    }

    /**
     * 绘制边框
     * @param canvas
     */
    private void drawBorder(Canvas canvas) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(mColor);//直接颜色值
        p.setStrokeWidth(mPaintSize);
        if (mPwdBorderStyle == 0) {
            canvas.drawRect(mPaintSize, mPaintSize, getMeasuredWidth() - mPaintSize, getMeasuredHeight() - mPaintSize, p);//父控件的相对位置（PwdView）
        } else {
            canvas.drawLine(0, getMeasuredWidth() - mPaintSize, getMeasuredWidth() - mPaintSize, getMeasuredWidth() - mPaintSize, p);

        }
    }

}
