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
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("link")));
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
