package com.example.appbase.Feature.splash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.appbase.Base.BaseActivity;
import com.example.appbase.Feature.intro.IntroScreenActivity;
import com.example.appbase.Feature.language.LanguageStartActivity;
import com.example.appbase.R;
import com.example.appbase.Utils.SharePrefUtils;
import com.example.appbase.databinding.ActivitySplashBinding;

public class SplashScreenActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    public ActivitySplashBinding getBinding() {
        return ActivitySplashBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    public void getData() {
        SharePrefUtils.increaseCountOpenApp(SplashScreenActivity.this);
    }

    @Override
    public void initView() {
        setStatusBarGradiant(this, R.drawable.bg_gradient_app);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showButton();
            }
        }, 4500);
    }

    @Override
    public void bindView() {
        binding.tvStart.setOnClickListener(view -> {
            startAct();
        });
    }

    public void showButton() {
        binding.tvStart.setVisibility(View.VISIBLE);
        binding.tvLoad.setVisibility(View.GONE);
        binding.pbLoading.setVisibility(View.GONE);
    }

    public void startAct() {
        if (SharePrefUtils.getCountOpenFirstHelp(this) == 0) {
            startActivity(new Intent(this, LanguageStartActivity.class));
        } else {
            startActivity(new Intent(this, IntroScreenActivity.class));
        }
        finish();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
