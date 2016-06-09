package xsf.customView.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;


public final class ObjAnimtor {
    public static Animator add(final View view, float x) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,
                "translationX", x, 0.0F);
        objectAnimator.setDuration(1000L);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator paramAnonymousAnimator) {
            }

            public void onAnimationEnd(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {

            }
        });
        return objectAnimator;
    }

    public static Animator down(View view, float y) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(view,
                "translationY", 0.0F, y);
        localObjectAnimator.setDuration(2000L);
        return localObjectAnimator;
    }

    public static Animator up(View view, float y) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,
                "translationY", 0.0F, y);
        objectAnimator.setDuration(2000L);
        return objectAnimator;
    }

    public static Animator up(View view, float fromY, float toY) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,
                "translationY", fromY, toY);
        objectAnimator.setDuration(2000L);
        return objectAnimator;
    }

    public static Animator upAndVanish(View view, float y) {
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] arrayOfAnimator = new Animator[2];
        float[] floats = new float[2];
        floats[0] = y;
        floats[1] = (3.0F * y);
        arrayOfAnimator[0] = ObjectAnimator.ofFloat(view, "translationY",
                floats);
        arrayOfAnimator[1] = ObjectAnimator.ofFloat(view, "alpha", 1.0F, 0.0F);
        animatorSet.playTogether(arrayOfAnimator);
        animatorSet.setDuration(2000L);
        return animatorSet;
    }

    public static Animator rotation(View view, float fromY, float toY) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,
                "rotationX", fromY, toY);
        objectAnimator.setDuration(2000L);
        return objectAnimator;
    }

}
