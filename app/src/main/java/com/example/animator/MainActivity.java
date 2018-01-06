package com.example.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private Button btnStart;
    private TextView tvVolume;
    private ImageView ivNumber;
    private CameraPreview cameraPreview;

    private File mVideoFile;
    private Boolean isVedioStart = false;

    private int count = 0;
    private String mValidateData = "123456789";
    private int counts = mValidateData.length();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.button);
        tvVolume = findViewById(R.id.textView);
        cameraPreview = findViewById(R.id.camPreview_face_start);
        ivNumber = findViewById(R.id.imageView);

        CameraPreview.setVolumeListener(volumeListener);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVedioStart = !isVedioStart;

                if (isVedioStart) {
                    cameraPreview.isGetVolume = true;
                    btnStart.setText("结束");
                    mVideoFile = cameraPreview.startRecordVideo(); //开始录制视频
                    cameraPreview.getVolume();
                    stratReadNumberAnimation();
                } else {
                    btnStart.setText("开始");
                    cameraPreview.isGetVolume = false;
                    cameraPreview.stopRecordCamera();
                }
            }
        });
    }

    // 开始读数字的动画
    private void stratReadNumberAnimation() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(ivNumber, "alpha", 0.5f, 1.0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivNumber, "scaleX", 0.8f, 1.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivNumber, "scaleY", 0.8f, 1.3f);
        AnimatorSet set = new AnimatorSet();
        set.addListener(animatorListener);
        set.setDuration(2000);
        set.playTogether(alpha, scaleX, scaleY);
        set.start();
    }

    private IVolumeListener volumeListener = new IVolumeListener() {
        @Override
        public void onResult(final float value) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvVolume.setText("" + Math.round(value));
                }
            });
        }
    };


    private Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {
            count += 1;
            //count = count + 1;
            if (ivNumber.getVisibility() == View.INVISIBLE) {
                ivNumber.setVisibility(View.VISIBLE);
            }
            int number = VideoNumber.readChangedNumber(mValidateData, count);
            ivNumber.setImageResource(number);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (!isVedioStart) {
                return;
            }
            if (count >= counts) {
                ivNumber.setVisibility(View.INVISIBLE);
                count = 0;
                return;
            }

            stratReadNumberAnimation();
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        cameraPreview.onResume();
    }

    @Override
    public void onPause() {
        cameraPreview.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        cameraPreview.releaseRes();
        cameraPreview.isGetVolume = false;
        super.onDestroy();
    }

//    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
//
//        @Override
//        public void onAnimationStart(Animation animation) {
//            count += 1;
//            ivNumber.setVisibility(View.VISIBLE);
//            ivNumber.setImageResource(VideoNumber.readChangedNumber(mValidateData, count));
//        }
//
//        @Override
//        public void onAnimationEnd(Animation animation) {
//
//            // 判断显示的数字是否完成
//            if (count >= counts) {
//                ivNumber.setVisibility(View.INVISIBLE);
//                count = 0;
//                return;
//            }
//
//            // 不能重用参数那个animation
////            animation.reset();
////            animation.setAnimationListener(animationListener2);
////            animation.start();
//
//            //当动画结束时重复动画
//            Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.face_start_number);
//            anim.setAnimationListener(animationListener);
//            ivNumber.startAnimation(anim);
//
//        }
//
//        @Override
//        public void onAnimationRepeat(Animation animation) {
//        }
//    };
}
