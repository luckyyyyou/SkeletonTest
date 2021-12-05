package com.example.skeleton;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Danni on 2021/12/5.
 */

public class LightView extends View {
    private Shader mGradient;

    private Matrix mGradientMatrix;

    private Paint mPaint;

    private int mViewWidth = 0, mViewHeight = 0;

    private float mTranslateX = 0, mTranslateY = 0;

    private boolean mAnimating = false;

    private Rect rect;

    private ValueAnimator valueAnimator;

    private boolean autoRun = true; //是否自动运行动画

    public LightView(Context context) {
        super(context);

        init();

    }

    public LightView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();

    }

    public LightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    private void init() {
        rect = new Rect();

        mPaint = new Paint();

        initGradientAnimator();

    }

    public void setAutoRun(boolean autoRun) {
        this.autoRun = autoRun;

    }

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("LightView", "onMeasure");


        rect.set(0, 0, getWidth(), getHeight());

    }

    @Override

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("LightView", "onSizeChanged");

        if (mViewWidth == 0) {
            mViewWidth = getWidth();

            mViewHeight = getHeight();

            if (mViewWidth > 0) {
//亮光闪过

                //70度
                mGradient = new LinearGradient(0, 0, mViewWidth, mViewHeight / (float) 2.75,

                        new int[]{Color.TRANSPARENT, 0x00FFFFFF, 0x33FFFFFF, 0x00FFFFFF, Color.TRANSPARENT},

                        new float[]{0.0f, (getWidth() - 100) / 2 / (float)getWidth(), ((getWidth() - 100)/2 + 100 / 3)/(float)getWidth(), ((getWidth() - 100)/2 + 100 / 3 * 2)/(float)getWidth(), 1f},

                        Shader.TileMode.CLAMP);

                mPaint.setShader(mGradient);

                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));

                mGradientMatrix = new Matrix();

                mGradientMatrix.setTranslate(-mViewWidth, 0);

                mGradient.setLocalMatrix(mGradientMatrix);

                rect.set(0, 0, w, h);

            }

        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("LightView", "onLayout");

    }


    @Override

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("LightView", "onDraw");
        if (mAnimating && mGradientMatrix != null) {
            canvas.drawRect(rect, mPaint);

        }

    }

    private void initGradientAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0, 1);

        valueAnimator.setDuration(2000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override

            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (Float) animation.getAnimatedValue();
                Log.i("TAG", "curValue is " + animation.getAnimatedValue());

//❶ 改变每次动画的平移x、y值，范围是[-mViewWidth, mViewWidth]

                mTranslateX = - (float) 0.5 * mViewWidth + mViewWidth * v;

                mTranslateY = mViewHeight * v * (float) 2.75;

//❷ 平移matrix, 设置平移量

                if (mGradientMatrix != null) {
                    mGradientMatrix.setTranslate(mTranslateX, 0);

                }

//❸ 设置线性变化的matrix

                if (mGradient != null) {
                    mGradient.setLocalMatrix(mGradientMatrix);

                }

//❹ 重绘

                invalidate();

            }

        });

        if (autoRun) {
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);

            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override

                public void onGlobalLayout() {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    mAnimating = true;

                    if (valueAnimator != null) {
                        valueAnimator.start();

                    }

                }

            });

        }

    }

//停止动画

    public void stopAnimation() {
        if (mAnimating && valueAnimator != null) {
            mAnimating = false;

            valueAnimator.cancel();

            invalidate();

        }

    }

//开始动画

    public void startAnimation() {
        if (!mAnimating && valueAnimator != null) {
            mAnimating = true;

            valueAnimator.start();

        }

    }

}

