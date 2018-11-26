package com.example.dachui.uidemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dachui.cuttomview.ProgressView;


public class MainActivity extends AppCompatActivity {
    private TextView mText;
    RoundProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.text);
        bar = (RoundProgressBar) findViewById(R.id.roundProgressBar2);
//        ProgressBar pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
//        pb_loading.setProgress(10);
//        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
//        layout.getX();
//        Log.d("MainActivity", "" + layout.getX());
//        layout.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
//                            Log.i("MainActivity", layout.getHeight() + "----"
//                                    + layout.getWidth() + "====" + layout.getX());
//                        }
//                    }
//        );
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mText.getLayoutParams();
        params.setMargins(750, 0, 0, 0);
        mText.setLayoutParams(params);

        ProgressView view = (ProgressView) findViewById(R.id.view);
        view.setFitWidth(150);

        ProgressView view1 = (ProgressView) findViewById(R.id.view1);
        view1.setFitWidth(170);

        ProgressView view2 = (ProgressView) findViewById(R.id.view2);
//        view2.setFitWidth(270);

        lineAnim(view2);
    }

    public void btn(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mText, "translationX", 0, -400);
        animator.setInterpolator(new LinearInterpolator());
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                int value = (int) animator.getAnimatedValue();
//                mText.setma
//            }
//        });
        animator.setDuration(2000);
        animator.start();

        ValueAnimator animator2 = ObjectAnimator.ofInt(0, 100);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int value = (int) animator.getAnimatedValue();
                bar.setProgress(value);
            }
        });
        animator2.setDuration(2000);
        animator2.start();
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
