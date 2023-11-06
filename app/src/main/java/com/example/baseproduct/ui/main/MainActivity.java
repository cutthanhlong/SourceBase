package com.example.baseproduct.ui.main;

import android.view.View;

import com.example.baseproduct.base.BaseActivity;
import com.example.baseproduct.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {


    @Override
    public ActivityMainBinding getBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        binding.viewTop.tvToolBar.setText("MainActivity");
        binding.viewTop.ivCheck.setVisibility(View.INVISIBLE);
    }

    @Override
    public void bindView() {
        binding.viewTop.ivBack.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishThisActivity();
    }
}
