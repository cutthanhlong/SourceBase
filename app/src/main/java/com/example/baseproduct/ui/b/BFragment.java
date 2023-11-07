package com.example.baseproduct.ui.b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.baseproduct.base.BaseFragment;
import com.example.baseproduct.databinding.FragmentABinding;
import com.example.baseproduct.databinding.FragmentBBinding;

public class BFragment extends BaseFragment<FragmentBBinding> {


    @Override
    public FragmentBBinding setBinding(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return FragmentBBinding.inflate(inflater,container,false);
    }

    @Override
    public void initView() {

    }

    @Override
    public void bindView() {
        binding.rlOnclickFb.setOnClickListener(view -> onClickAnimation(view));
    }
}
