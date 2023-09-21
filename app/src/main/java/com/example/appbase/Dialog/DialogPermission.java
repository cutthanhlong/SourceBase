package com.example.appbase.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.example.appbase.databinding.DialogPermissionsBinding;

public class DialogPermission extends Dialog {

    DialogPermissionsBinding binding;

    public DialogPermission(@NonNull Context context, boolean isCancelable) {
        super(context);
        binding = DialogPermissionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setCancelable(isCancelable);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.tvAgree.setOnClickListener(v -> {
//            AppOpenManager.getInstance().disableAppResumeWithActivity(context.getClass());
            dismiss();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        });
    }
}
