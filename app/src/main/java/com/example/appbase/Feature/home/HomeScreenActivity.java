package com.example.appbase.Feature.home;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appbase.Base.BaseActivity;
import com.example.appbase.Dialog.DialogExitApp;
import com.example.appbase.Dialog.DialogRating;
import com.example.appbase.Feature.setting.SettingScreenActivity;
import com.example.appbase.Interface.IClickDialogExit;
import com.example.appbase.Interface.IClickDialogRate;
import com.example.appbase.R;
import com.example.appbase.Utils.SharePrefUtils;
import com.example.appbase.databinding.ActivityHomeBinding;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeScreenActivity extends BaseActivity<ActivityHomeBinding> {

    ArrayList<String> remoteRate = new ArrayList<String>(Arrays.asList("2", "4", "6", "8", "10"));
    ReviewManager manager;
    ReviewInfo reviewInfo;
    DialogExitApp dialogExitApp;

    @Override
    public ActivityHomeBinding getBinding() {
        return ActivityHomeBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    public void getData() {

    }

    @Override
    public void initView() {
        setStatusBarGradiant(this, R.drawable.bg_gradient_app);

    }

    @Override
    public void bindView() {

        binding.btnSetting.setOnClickListener(view -> {
            startActivity(new Intent(this, SettingScreenActivity.class));
        });
    }

    private void exitApp() {
        dialogExitApp = new DialogExitApp(this, true, new IClickDialogExit() {
            @Override
            public void onExit() {
                if (dialogExitApp.isShowing()) {
                    dialogExitApp.dismiss();
                    finishAffinity();
                }
            }

            @Override
            public void onCancel() {
                if (dialogExitApp.isShowing()) {
                    dialogExitApp.dismiss();
                }
            }
        });

        dialogExitApp.show();
    }


    private void rateApp() {
        DialogRating ratingDialog = new DialogRating(HomeScreenActivity.this, true);
        ratingDialog.init(new IClickDialogRate() {
            @Override
            public void send() {
                ratingDialog.dismiss();
                String uriText = "mailto:" + SharePrefUtils.email + "?subject=" + "Review for " + SharePrefUtils.subject + "&body=" + SharePrefUtils.subject + "\nRate : " + ratingDialog.getRating() + "\nContent: ";
                Uri uri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                try {
                    finishAffinity();
                    startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.Send_Email)));
                    SharePrefUtils.forceRated(HomeScreenActivity.this);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(HomeScreenActivity.this, getResources().getString(R.string.There_is_no), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void rating() {
                manager = ReviewManagerFactory.create(HomeScreenActivity.this);
                Task<ReviewInfo> request = manager.requestReviewFlow();
                request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
                    @Override
                    public void onComplete(@NonNull Task<ReviewInfo> task) {
                        if (task.isSuccessful()) {
                            reviewInfo = task.getResult();
                            Log.e("ReviewInfo", "" + reviewInfo);
                            Task<Void> flow = manager.launchReviewFlow(HomeScreenActivity.this, reviewInfo);
                            flow.addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    SharePrefUtils.forceRated(HomeScreenActivity.this);
                                    ratingDialog.dismiss();
                                    finishAffinity();
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
                finishAffinity();
                ratingDialog.dismiss();
            }

        });
        try {
            ratingDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (!SharePrefUtils.isRated(this)) {
            if (remoteRate.contains(String.valueOf(SharePrefUtils.getCountOpenApp(this)))) {
                rateApp();
            } else {
                exitApp();
            }
        } else {
            exitApp();
        }
    }
}