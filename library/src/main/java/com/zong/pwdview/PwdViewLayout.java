package com.zong.pwdview;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PwdViewLayout extends RelativeLayout {
    int mPwdWidth;
    int mPwdFocusColor;
    int mPwdNomalColor;
    int mPwdPointColor;
    boolean isPwdModel;//密码和明文
    int mPwdBorderStyle;//背景样式，方，线
    int mPwdMargin;
    int mPwdPadding;
    int mPwdViewCount;
    boolean isInputNumber;
    StringBuilder builder;
    OnInputFinshedListener listener;

    boolean isNext = true;
    public PwdViewLayout(Context context) {
        super(context);
    }

    public PwdViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PwdViewLayout);
        mPwdViewCount = a.getInt(R.styleable.PwdViewLayout_pwd_count, 4);
        mPwdWidth = dp2px(a.getDimension(R.styleable.PwdViewLayout_pwd_width, 55));
        mPwdMargin = dp2px(a.getDimension(R.styleable.PwdViewLayout_pwd_margin, 15));
        mPwdPadding = dp2px(a.getDimension(R.styleable.PwdViewLayout_pwd_padding, 0));
        mPwdFocusColor = a.getColor(R.styleable.PwdViewLayout_pwd_focus_color, Color.parseColor("#D81B60"));
        mPwdNomalColor = a.getColor(R.styleable.PwdViewLayout_pwd_normal_color, Color.parseColor("#555555"));
        mPwdPointColor = a.getColor(R.styleable.PwdViewLayout_pwd_point_color, Color.parseColor("#555555"));
        isInputNumber = a.getBoolean(R.styleable.PwdViewLayout_isInputNum, true);
        isPwdModel = a.getBoolean(R.styleable.PwdViewLayout_isPwdModle, true);
        mPwdBorderStyle = a.getInt(R.styleable.PwdViewLayout_pwd_border_style, 0);
        a.recycle();
        initView();
    }

    void initView() {
        removeAllViews();
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    focus();
                } else {
                    focus();
                    catString();
                }

            }
        };
        OnKeyListener onKeyListener = new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && KeyEvent.ACTION_UP == event.getAction()) {//防止重复调用
                    delString();
                }
                return false;
            }
        };
        for (int i = 0; i < mPwdViewCount; i++) {
            PwdEditView pwdEditView = new PwdEditView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mPwdWidth, mPwdWidth);
            layoutParams.setMargins(getLeft(), getTop(), getRight(), getBottom());
            pwdEditView.setLayoutParams(layoutParams);
            pwdEditView.setGravity(Gravity.CENTER);
            pwdEditView.setTextColor(mPwdPointColor);
            pwdEditView.setCursorVisible(false);
            pwdEditView.setBackgroundColor(Color.TRANSPARENT);
            pwdEditView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            if (isPwdModel) {
                pwdEditView.setTextSize(8);//文字尽可能小，以便密文遮盖
            } else {
                pwdEditView.setTextSize(mPwdWidth / 5.5f);//框和文字大小比率
            }

            pwdEditView.addTextChangedListener(textWatcher);
            pwdEditView.setOnKeyListener(onKeyListener);
            addView(pwdEditView);
            if (isInputNumber) {  //默认弹出键盘样式
                pwdEditView.setRawInputType(InputType.TYPE_CLASS_NUMBER);
//                pwdEditView.setInputType();//时边框不显示,原因未知。
            } else {
                pwdEditView.setRawInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
            }
            if (i == 0) {
                pwdEditView.setBorderColor(mPwdFocusColor);
                pwdEditView.setFocusableInTouchMode(true);
            } else {
                pwdEditView.setBorderColor(mPwdNomalColor);
                pwdEditView.setFocusableInTouchMode(false);
            }
            pwdEditView.init(isPwdModel, mPwdBorderStyle, mPwdNomalColor, mPwdFocusColor);

        }

    }

    /**
     * 设置输入pwdview个数
     * @param num
     */
    public void setPwdViewCount(int num) {
        this.mPwdViewCount = num;
        initView();

    }

    /**
     * 清空输入内容
     */
    public void clearPwdContent(){

        initView();
    }

    private void focus() {
        int count = getChildCount();
        PwdEditView editText;
        for (int i = 0; i < count; i++) {
            editText = (PwdEditView) getChildAt(i);
            if (editText.getText().length() < 1) {
                editText.requestFocus();
                return;
            }
        }
    }

    /**
     * 输入字符
     */
    private void catString() {
        builder = new StringBuilder();
        PwdEditView pwdEditView;
        for (int i = 0; i < mPwdViewCount; i++) {
            pwdEditView = (PwdEditView) getChildAt(i);
            builder.append(!TextUtils.isEmpty(pwdEditView.getText())?pwdEditView.getText():"");
            pwdEditView.setBorderColor(mPwdNomalColor);
            pwdEditView.setFocusableInTouchMode(false);
            if (pwdEditView.getText().length() == 0) {
                pwdEditView.setFocusableInTouchMode(true);
                pwdEditView.requestFocus();
                pwdEditView.setBorderColor(mPwdFocusColor);
                break;
            }
            if (mPwdViewCount - 1 == i) {
                isNext = true;
            }
            if(i==mPwdViewCount-1&&!TextUtils.isEmpty(pwdEditView.getText())){
                if(listener!=null){
                    listener.inputFinshed(builder.toString());
                }
                pwdEditView.setFocusableInTouchMode(true);
            }

        }
    }


    /**
     * 删除字符
     */
    private void delString() {
        PwdEditView pwdEditView;
        builder = new StringBuilder();
        for (int i = mPwdViewCount - 1; i >= 0; i--) {
            pwdEditView = (PwdEditView) getChildAt(i);
            pwdEditView.setFocusableInTouchMode(false);
            pwdEditView.setBorderColor(mPwdNomalColor);
            if (i == 0 && pwdEditView.getText().length() == 0) {
                pwdEditView.requestFocus();
                pwdEditView.setBorderColor(mPwdFocusColor);
                pwdEditView.setFocusableInTouchMode(true);
                break;
            }
            if (isNext) {//最后一个位置,保留焦点
                isNext = false;
                if (i > 0 && pwdEditView.getText().length() == 0) {
                    pwdEditView.requestFocus();
                    pwdEditView.setBorderColor(mPwdFocusColor);
                    pwdEditView.setFocusableInTouchMode(true);
                    break;
                }
            } else {
                if (i >= 0 && pwdEditView.getText().length() == 1) {//清空前一个位置，且不获得焦点
                    pwdEditView.requestFocus();
                    pwdEditView.setText("");
                    pwdEditView.setBorderColor(mPwdFocusColor);
                    pwdEditView.setFocusableInTouchMode(true);
                    break;
                }

            }

        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < mPwdViewCount; i++) {
            View child = getChildAt(i);
            this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        if (mPwdViewCount > 0) {
            View child = getChildAt(0);
            int cHeight = child.getMeasuredHeight();
            int cWidth = child.getMeasuredWidth();
            int maxH = cHeight + 2 * mPwdPadding;//处理自定义padding 1.给Layout 加上padding距离
            int maxW = (cWidth + mPwdMargin) * mPwdViewCount - mPwdMargin + 2 * mPwdPadding;
            setMeasuredDimension(resolveSize(maxW, widthMeasureSpec), resolveSize(maxH, heightMeasureSpec));
        }

    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < mPwdViewCount; i++) {
            View child = getChildAt(i);
            int cWidth = child.getMeasuredWidth();
            int cHeight = child.getMeasuredHeight();
            int cl = i * (cWidth + mPwdMargin) + mPwdPadding;///处理自定义padding 2.Layout子布局的时候处理padding
            int cr = cl + cWidth;
            int ct = mPwdPadding;
            int cb = ct + cHeight;
            child.layout(cl, ct, cr, cb);
        }
    }

    protected int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }



    public interface OnInputFinshedListener{
        void inputFinshed(String content);
    }
    public void setInputFinshedListener(OnInputFinshedListener _listener){
        this.listener=_listener;
    }
}
