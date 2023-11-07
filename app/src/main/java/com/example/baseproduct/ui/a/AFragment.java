package com.example.baseproduct.ui.a;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.baseproduct.base.BaseFragment;
import com.example.baseproduct.databinding.FragmentABinding;

public class AFragment extends BaseFragment<FragmentABinding> {


    @Override
    public FragmentABinding setBinding(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return FragmentABinding.inflate(inflater,container,false);
    }

    @Override
    public void initView() {

    }

    @Override
    public void bindView() {
        binding.rlOnclickFa.setOnClickListener(view -> onClickAnimation(view));
    }
}
