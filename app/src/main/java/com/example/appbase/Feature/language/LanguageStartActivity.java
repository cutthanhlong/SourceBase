package com.example.appbase.Feature.language;

import android.content.Intent;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appbase.Adapter.LanguageStartAdapter;
import com.example.appbase.Base.BaseActivity;
import com.example.appbase.Feature.intro.IntroScreenActivity;
import com.example.appbase.Model.LanguageModel;
import com.example.appbase.R;
import com.example.appbase.Utils.SharePrefUtils;
import com.example.appbase.Utils.SystemUtil;
import com.example.appbase.databinding.ActivityLanguageStartBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LanguageStartActivity extends BaseActivity<ActivityLanguageStartBinding> {

    List<LanguageModel> listLanguage;
    String codeLang;

    @Override
    public ActivityLanguageStartBinding getBinding() {
        return ActivityLanguageStartBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    public void getData() {
        codeLang = Locale.getDefault().getLanguage();

        initData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LanguageStartAdapter languageAdapter = new LanguageStartAdapter(listLanguage, code -> codeLang = code, this);


        String codeLangDefault = Locale.getDefault().getLanguage();
        String[] langDefault = {"hi", "zh", "es", "fr", "pt", "in", "de"};
        if (!Arrays.asList(langDefault).contains(codeLangDefault)) codeLang = "en";

        languageAdapter.setCheck(codeLang);
        binding.rcvLangFirst.setLayoutManager(linearLayoutManager);
        binding.rcvLangFirst.setAdapter(languageAdapter);
    }

    @Override
    public void initView() {
        setStatusBarGradiant(this, R.drawable.bg_gradient_app);

    }

    @Override
    public void bindView() {
        binding.btnDone.setOnClickListener(view -> {
            SharePrefUtils.increaseCountFirstHelp(LanguageStartActivity.this);
            SystemUtil.saveLocale(getBaseContext(), codeLang);
            startActivity(new Intent(LanguageStartActivity.this, IntroScreenActivity.class));
            finish();
        });
    }

    private void initData() {
        listLanguage = new ArrayList<>();
        String lang = Locale.getDefault().getLanguage();
        listLanguage.add(new LanguageModel("English", "en"));
        listLanguage.add(new LanguageModel("China", "zh"));
        listLanguage.add(new LanguageModel("French", "fr"));
        listLanguage.add(new LanguageModel("German", "de"));
        listLanguage.add(new LanguageModel("Hindi", "hi"));
        listLanguage.add(new LanguageModel("Indonesia", "in"));
        listLanguage.add(new LanguageModel("Portuguese", "pt"));
        listLanguage.add(new LanguageModel("Spanish", "es"));

        for (int i = 0; i < listLanguage.size(); i++) {
            if (listLanguage.get(i).getCode().equals(lang)) {
                listLanguage.add(0, listLanguage.get(i));
                listLanguage.remove(i + 1);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
