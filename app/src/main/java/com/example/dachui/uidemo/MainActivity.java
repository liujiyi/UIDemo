package com.example.dachui.uidemo;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;

import com.example.dachui.cuttomview.ProgressView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressView view = (ProgressView) findViewById(R.id.view);
        view.setFitWidth(150);

        ProgressView view1 = (ProgressView) findViewById(R.id.view1);
        view1.setFitWidth(170);

        ProgressView view2 = (ProgressView) findViewById(R.id.view2);
//        view2.setFitWidth(270);

        lineAnim(view2);
    }
        /**
         * 虚线动画
         */
    private void lineAnim(final ProgressView view) {
        ValueAnimator animator = ValueAnimator.ofInt(0, 270);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int value = (int) animator.getAnimatedValue();
                view.setFitWidth(value);
            }
        });
        animator.setDuration(2000);
        animator.start();
    }
}
