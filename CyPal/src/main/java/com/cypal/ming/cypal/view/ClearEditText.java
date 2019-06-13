package com.cypal.ming.cypal.view;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.cypal.ming.cypal.R;
import com.cypal.ming.cypal.utils.Tools;

import okhttp3.internal.platform.Platform;


/**
 * Created by huangwq on 2015/8/4.
 * 自定义EditText 实现右侧删除点击
 */
public class ClearEditText extends android.support.v7.widget.AppCompatEditText implements
        View.OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;

    private Context mContext;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    /**
     * 清空按钮被点击的回调
     */
    private OnClearClickListener onClearClickListener;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public void setOnClearClickListener(OnClearClickListener onClearClickListener) {
        this.onClearClickListener = onClearClickListener;
    }

    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        setClearDrawable();
//        mClearDrawable.setBounds(0, 0, 47, 47);
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    public void setClearDrawable() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
//          throw new NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(R.drawable.common_search_delete);
        }

//        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        mClearDrawable.setBounds(0, 0, Tools.dip2px(mContext, 16), Tools.dip2px(mContext, 16));
    }

    public void setSearchDrawable(int resId) {
        Drawable searchDrawable = getResources().getDrawable(resId);
        if (searchDrawable != null){
            searchDrawable.setBounds(0, 0, searchDrawable.getIntrinsicWidth(), searchDrawable.getIntrinsicHeight());
            setCompoundDrawables(searchDrawable,
                    getCompoundDrawables()[1],  getCompoundDrawables()[2], getCompoundDrawables()[3]);
        }

    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                    Tools.openKeyBoard(mContext, this);
                    if (this.onClearClickListener != null) {
                        onClearClickListener.onClearClick(this);
                    }
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    public void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }


    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public interface OnClearClickListener {
        void onClearClick(View view);
    }

    public void setHasFoucs(boolean hasFoucs){
        this.hasFoucs = hasFoucs;
    }
}