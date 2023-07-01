package com.example.appbase.Feature.about;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;

import com.example.appbase.Base.BaseActivity;
import com.example.appbase.BuildConfig;
import com.example.appbase.R;
import com.example.appbase.databinding.ActivityAboutBinding;


public class AboutScreenActivity extends BaseActivity<ActivityAboutBinding> {

    @Override
    public ActivityAboutBinding getBinding() {
        return ActivityAboutBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initView() {
        setStatusBarGradiant(this, R.drawable.bg_gradient_app);

        binding.tvVersion.setText(getString(R.string.version) + " " + BuildConfig.VERSION_NAME);
    }

    @Override
    public void bindView() {
        binding.btnBack.setOnClickListener(view -> onBackPressed());

        binding.btnPolicy.setOnClickListener(view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://firebasestorage.googleapis.com/v0/b/flashlight-2e019.appspot.com/o/Privacy Policy .html?alt=media&token=6a28089e-1e25-4263-9fa7-556b93d1d14a&_gl=1*1r9f2sk*_ga*NjkyOTgwOTE4LjE2ODI2NzIyNzg.*_ga_CW55HF8NVT*MTY4NTk1MDE5OS45LjEuMTY4NTk1MjI4MC4wLjAuMA..")));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
