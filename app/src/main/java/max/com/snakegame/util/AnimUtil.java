package max.com.snakegame.util;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import max.com.snakegame.R;


/**
 * @author jelly
 * @TIME 2018/1/3
 * @DES ${TODO}
 */

public class AnimUtil {

    /**
     * 使用ValueAnimator实现图片缩放动画
     * 实现动画的view
     */
    public static void scaleANim(final View animView){
        //1.设置目标属性名及属性变化的初始值和结束值
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0, 1.3f,1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0, 1.3f,1.0f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0,1);
        ValueAnimator mAnimator = ValueAnimator.ofPropertyValuesHolder(scaleX, scaleY,alpha);
        //2.为目标对象的属性变化设置监听器
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // 3.根据属性名获取属性变化的值分别为ImageView目标对象设置X和Y轴的缩放值
                float animatorValueScaleX =  (float) valueAnimator.getAnimatedValue("scaleX");
                float animatorValueScaleY = (float) valueAnimator.getAnimatedValue("scaleY");
                animView.setScaleX(animatorValueScaleX);
                animView.setScaleY(animatorValueScaleY);
            }
        });

        //5.设置动画的持续时间、是否重复及重复次数等属性
        mAnimator.setDuration(500);
        //6.为ValueAnimator设置目标对象并开始执行动画
        mAnimator.setTarget(animView);
        mAnimator.start();
    }


    /**
     * 使用ValueAnimator实现图片漂浮动画
     * 实现动画的view
     */
    public static void rotationAnim(final View animView){
        //1.设置目标属性名及属性变化的初始值和结束值
        PropertyValuesHolder rotationX = PropertyValuesHolder.ofFloat("rotationX", 0,-50,0,50,0,-50,0,50,0);
        PropertyValuesHolder rotationY = PropertyValuesHolder.ofFloat("rotationY", 0,-10,0,10,0,-10,0,10,0);
        ValueAnimator mAnimator = ValueAnimator.ofPropertyValuesHolder(rotationX, rotationY);
        //2.为目标对象的属性变化设置监听器
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // 3.根据属性名获取属性变化的值分别为ImageView目标对象设置X和Y轴的缩放值
                float animRotationX =  (float) valueAnimator.getAnimatedValue("rotationX");
                float animRotationY = (float) valueAnimator.getAnimatedValue("rotationY");
                animView.setRotationX(animRotationX);
                animView.setRotationY(animRotationY);
            }
        });

        //5.设置动画的持续时间、是否重复及重复次数等属性
        mAnimator.setDuration(13000);
        mAnimator.setInterpolator(new DecelerateInterpolator());
        //6.为ValueAnimator设置目标对象并开始执行动画
        mAnimator.setStartDelay(1000);
        mAnimator.setTarget(animView);
        mAnimator.start();
    }


    /**
     * 实现漂浮动画
     * @param context 上下文
     * @param animView 实现动画的view
     * @param isDelay 是否延时
     */
    public static void floatAnim(Context context,final View animView,boolean isDelay){
        final Animation animationDwon = AnimationUtils.loadAnimation(context, R.anim.translate_btn_down);
        final Animation animationUp = AnimationUtils.loadAnimation(context, R.anim.translate_btn_up);
        if(isDelay){
            animationDwon.setStartOffset(500);
        }
        animView.startAnimation(animationDwon);
        animationDwon.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                animView.startAnimation(animationUp);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        animationUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                animView.startAnimation(animationDwon);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
}
