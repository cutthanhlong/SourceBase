package com.example.appbase.Dialog;

import android.content.Context;

import com.example.appbase.Base.BaseDialog;
import com.example.appbase.Interface.IClickDialogExit;
import com.example.appbase.databinding.DialogExitAppBinding;

public class DialogExitApp extends BaseDialog<DialogExitAppBinding> {
    IClickDialogExit iBaseListener;
    Context context;

    public DialogExitApp(Context context, Boolean cancelAble, IClickDialogExit iBaseListener) {
        super(context, cancelAble);
        this.context = context;
        this.iBaseListener = iBaseListener;
    }


    @Override
    protected DialogExitAppBinding setBinding() {
        return DialogExitAppBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void bindView() {
        binding.btnCancelQuitApp.setOnClickListener(view -> iBaseListener.onCancel());

        binding.btnQuitApp.setOnClickListener(view -> iBaseListener.onExit());
    }
}
