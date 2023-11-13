package com.example.baseproduct.base;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import kotlin.jvm.functions.Function1;

public class ViewExtension {

    public static void tap(View tap, Function1 action) {
        if (tap != null && action != null) {
            tap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick(view);
                    view.setEnabled(false);
                    view.postDelayed(() -> view.setEnabled(true), 1500);
                    action.invoke(view);
                }
            });
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
