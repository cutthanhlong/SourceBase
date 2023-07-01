package com.example.appbase.Feature.setting;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appbase.Base.BaseActivity;
import com.example.appbase.Dialog.DialogRating;
import com.example.appbase.Feature.about.AboutScreenActivity;
import com.example.appbase.Interface.IClickDialogRate;
import com.example.appbase.Feature.language.LanguageActivity;
import com.example.appbase.R;
import com.example.appbase.Utils.SharePrefUtils;
import com.example.appbase.Utils.SystemUtil;
import com.example.appbase.databinding.ActivitySettingBinding;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

public class SettingScreenActivity extends BaseActivity<ActivitySettingBinding> {

    ReviewManager manager;
    ReviewInfo reviewInfo;

    @Override
    public ActivitySettingBinding getBinding() {
        return ActivitySettingBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    public void getData() {
        setStatusBarGradiant(this, R.drawable.bg_gradient_app);
    }

    @Override
    public void initView() {
        String codeLang = SystemUtil.getPreLanguage(this);
        switch (codeLang) {
            case "en":
                binding.tvLang.setText(getResources().getString(R.string.english));
                break;
            case "pt":
                binding.tvLang.setText(getResources().getString(R.string.portuguese));
                break;
            case "es":
                binding.tvLang.setText(getResources().getString(R.string.spanish));
                break;
            case "de":
                binding.tvLang.setText(getResources().getString(R.string.german));
                break;
            case "fr":
                binding.tvLang.setText(getResources().getString(R.string.french));
                break;
            case "zh":
                binding.tvLang.setText(getResources().getString(R.string.china));
                break;
            case "hi":
                binding.tvLang.setText(getResources().getString(R.string.hindi));
                break;
            case "in":
                binding.tvLang.setText(getResources().getString(R.string.indonesia));
                break;
        }

        binding.btnMoreApp.setVisibility(View.GONE);

        if (SharePrefUtils.isRated(this)) {
            binding.btnRate.setVisibility(View.GONE);
        }
    }

    @Override
    public void bindView() {
        binding.btnBack.setOnClickListener(view -> onBackPressed());

        binding.btnLanguage.setOnClickListener(view -> {
            startActivity(new Intent(this, LanguageActivity.class));
        });

        binding.btnRate.setOnClickListener(view -> {
            if (!SharePrefUtils.isRated(this)) {
                rateApp();
            }
        });

        binding.btnShare.setOnClickListener(view -> {
            shareApp();
        });

        binding.btnMoreApp.setOnClickListener(view -> {
            moreApp();
        });

        binding.btnAbout.setOnClickListener(view -> {
            startActivity(new Intent(this, AboutScreenActivity.class));
        });
    }

    private void rateApp() {
        DialogRating ratingDialog = new DialogRating(this, true);
        ratingDialog.init(new IClickDialogRate() {
            @Override
            public void send() {
                ratingDialog.dismiss();
                String uriText = "mailto:" + SharePrefUtils.email + "?subject=" + "Review for " + SharePrefUtils.subject + "&body=" + SharePrefUtils.subject + "\nRate : " + ratingDialog.getRating() + "\nContent: ";
                Uri uri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                try {
                    binding.btnRate.setVisibility(View.GONE);
                    startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.Send_Email)));
                    SharePrefUtils.forceRated(SettingScreenActivity.this);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SettingScreenActivity.this, getResources().getString(R.string.There_is_no), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void rating() {
                manager = ReviewManagerFactory.create(SettingScreenActivity.this);
                Task<ReviewInfo> request = manager.requestReviewFlow();
                request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
                    @Override
                    public void onComplete(@NonNull Task<ReviewInfo> task) {
                        if (task.isSuccessful()) {
                            reviewInfo = task.getResult();
                            Log.e("ReviewInfo", "" + reviewInfo);
                            Task<Void> flow = manager.launchReviewFlow(SettingScreenActivity.this, reviewInfo);
                            flow.addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    SharePrefUtils.forceRated(SettingScreenActivity.this);
                                    ratingDialog.dismiss();
                                    binding.btnRate.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            ratingDialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void later() {
                ratingDialog.dismiss();
            }

        });
        try {
            ratingDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    private void shareApp() {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intentShare.putExtra(Intent.EXTRA_TEXT, "Download application :" + "https://play.google.com/store/apps/details?id=" + getPackageName());
        startActivity(Intent.createChooser(intentShare, "Share with"));
    }

    private void moreApp() {
//        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("link")));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}