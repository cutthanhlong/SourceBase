package com.example.baseproduct.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.baseproduct.R;
import com.example.baseproduct.base.BaseActivity;
import com.example.baseproduct.databinding.ActivityHomeBinding;
import com.example.baseproduct.dialog.exit.ExitAppDialog;
import com.example.baseproduct.dialog.exit.IClickDialogExit;
import com.example.baseproduct.dialog.rate.IClickDialogRate;
import com.example.baseproduct.dialog.rate.RatingDialog;
import com.example.baseproduct.ui.language.LanguageActivity;
import com.example.baseproduct.ui.main.MainActivity;
import com.example.baseproduct.ui.policy.PolicyActivity;
import com.example.baseproduct.util.SharePrefUtils;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {

    ReviewManager manager;
    ReviewInfo reviewInfo;
    ArrayList<String> exitRate = new ArrayList<String>(Arrays.asList("2", "4", "6", "8", "10"));


    @Override
    public ActivityHomeBinding getBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {

    }

    @Override
    public void bindView() {
        binding.rlLanguage.setOnClickListener(v -> startNextActivity(LanguageActivity.class, null));

        binding.rlRate.setOnClickListener(v -> rateApp(false));

        binding.rlShare.setOnClickListener(v -> shareApp());

        binding.rlPolicy.setOnClickListener(v -> startNextActivity(PolicyActivity.class, null));

        binding.rlMain.setOnClickListener(v -> startNextActivity(MainActivity.class, null));
    }

    private void rateApp(boolean isQuitApp) {
        RatingDialog ratingDialog = new RatingDialog(HomeActivity.this, true);
        ratingDialog.init(new IClickDialogRate() {
            @Override
            public void send() {
                binding.rlRate.setVisibility(View.GONE);
                ratingDialog.dismiss();
                String uriText = "mailto:" + SharePrefUtils.email + "?subject=" + "Review for " + SharePrefUtils.subject + "&body=" + SharePrefUtils.subject + "\nRate : " + ratingDialog.getRating() + "\nContent: ";
                Uri uri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                try {
                    if (isQuitApp){
                        finishAffinity();
                    }
                    startActivity(Intent.createChooser(sendIntent, getString(R.string.Send_Email)));
                    SharePrefUtils.forceRated(HomeActivity.this);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(HomeActivity.this, getString(R.string.There_is_no), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void rate() {
                manager = ReviewManagerFactory.create(HomeActivity.this);
                Task<ReviewInfo> request = manager.requestReviewFlow();
                request.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        reviewInfo = task.getResult();
                        Task<Void> flow = manager.launchReviewFlow(HomeActivity.this, reviewInfo);
                        flow.addOnSuccessListener(result -> {
                            binding.rlRate.setVisibility(View.GONE);
                            SharePrefUtils.forceRated(HomeActivity.this);
                            ratingDialog.dismiss();
                            if (isQuitApp){
                                finishAffinity();
                            }
                        });
                    } else {
                        ratingDialog.dismiss();
                    }
                });
            }

            @Override
            public void later() {
                ratingDialog.dismiss();
                if (isQuitApp){
                    finishAffinity();
                }
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

    private void exitApp() {
        ExitAppDialog exitAppDialog = new ExitAppDialog(this, true);
        exitAppDialog.init(new IClickDialogExit() {
            @Override
            public void cancel() {
                exitAppDialog.dismiss();
            }

            @Override
            public void quit() {
                exitAppDialog.dismiss();
                finishAffinity();
            }
        });

        try {
            exitAppDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (!SharePrefUtils.isRated(this)) {
            if (exitRate.contains(String.valueOf(SharePrefUtils.getCountOpenApp(this)))) {
                rateApp(true);
            } else {
                exitApp();
            }
        } else {
            exitApp();
        }
    }
}
