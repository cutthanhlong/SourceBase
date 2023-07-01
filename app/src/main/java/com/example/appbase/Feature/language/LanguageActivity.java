package com.example.appbase.Feature.language;

import android.content.Intent;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appbase.Adapter.LanguageAdapter;
import com.example.appbase.Base.BaseActivity;
import com.example.appbase.Feature.home.HomeScreenActivity;
import com.example.appbase.Model.LanguageModel;
import com.example.appbase.R;
import com.example.appbase.Utils.SystemUtil;
import com.example.appbase.databinding.ActivityLanguageBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguageActivity extends BaseActivity<ActivityLanguageBinding> {

    List<LanguageModel> listLanguage;
    String codeLang;

    @Override
    public ActivityLanguageBinding getBinding() {
        return ActivityLanguageBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    public void getData() {
        initData();

        codeLang = Locale.getDefault().getLanguage();
    }

    @Override
    public void initView() {
        setStatusBarGradiant(this, R.drawable.bg_gradient_app);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LanguageAdapter languageAdapter = new LanguageAdapter(listLanguage, code -> codeLang = code, this);


        languageAdapter.setCheck(SystemUtil.getPreLanguage(getBaseContext()));

        binding.rcvLang.setLayoutManager(linearLayoutManager);
        binding.rcvLang.setAdapter(languageAdapter);
    }

    @Override
    public void bindView() {
        binding.btnDone.setOnClickListener(view -> {
            SystemUtil.saveLocale(getBaseContext(), codeLang);
            setLangForApp();
        });

        binding.btnBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void initData() {
        listLanguage = new ArrayList<>();
        listLanguage.add(new LanguageModel("English", "en"));
        listLanguage.add(new LanguageModel("Portuguese", "pt"));
        listLanguage.add(new LanguageModel("Spanish", "es"));
        listLanguage.add(new LanguageModel("German", "de"));
        listLanguage.add(new LanguageModel("French", "fr"));
        listLanguage.add(new LanguageModel("China", "zh"));
        listLanguage.add(new LanguageModel("Hindi", "hi"));
        listLanguage.add(new LanguageModel("Indonesia", "in"));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void setLangForApp() {
        startActivity(new Intent(LanguageActivity.this, HomeScreenActivity.class));
        finishAffinity();
    }

}
