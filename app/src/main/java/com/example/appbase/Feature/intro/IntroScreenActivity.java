package com.example.appbase.Feature.intro;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.appbase.Adapter.IntroAdapter;
import com.example.appbase.Base.BaseActivity;
import com.example.appbase.Feature.home.HomeScreenActivity;
import com.example.appbase.R;
import com.example.appbase.databinding.ActivityIntroBinding;

public class IntroScreenActivity extends BaseActivity<ActivityIntroBinding> {
    ImageView[] dots = null;
    int positionPage = 0;
    String[] title, content;
    IntroAdapter introAdapter;

    @Override
    public ActivityIntroBinding getBinding() {
        return ActivityIntroBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    public void getData() {
        dots = new ImageView[]{findViewById(R.id.cricle_1), findViewById(R.id.cricle_2), findViewById(R.id.cricle_3)};
        title = new String[]{getResources().getString(R.string.title_intro1), getResources().getString(R.string.title_intro2), getResources().getString(R.string.title_intro3)};
        content = new String[]{getResources().getString(R.string.content_intro1), getResources().getString(R.string.content_intro2), getResources().getString(R.string.content_intro3)};

        introAdapter = new IntroAdapter(this);
    }

    @Override
    public void initView() {
        setStatusBarGradiant(this, R.drawable.bg_gradient_app);

        binding.viewPager2.setAdapter(introAdapter);

        binding.viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                positionPage = position;
            }

            @Override
            public void onPageSelected(int position) {
                changeContentInit(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void bindView() {
        binding.btnNext.setOnClickListener(view -> {
            binding.viewPager2.setCurrentItem(binding.viewPager2.getCurrentItem() + 1);
        });

        binding.btnStart.setOnClickListener(view -> {
            goToNextScreen();
        });
    }

    private void changeContentInit(int position) {
        binding.tvTitle.setText(title[position]);
        binding.tvContent.setText(content[position]);
        for (int i = 0; i < 3; i++) {
            if (i == position) dots[i].setImageResource(R.drawable.ic_dot_selected);
            else dots[i].setImageResource(R.drawable.ic_dot_not_select);
        }

        switch (position) {
            case 0:
            case 1:
                binding.btnNext.setVisibility(View.VISIBLE);
                binding.btnStart.setVisibility(View.GONE);
                break;
            case 2:
                binding.btnNext.setVisibility(View.GONE);
                binding.btnStart.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void goToNextScreen() {
        startActivity(new Intent(this, HomeScreenActivity.class));
        finish();
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        changeContentInit(binding.viewPager2.getCurrentItem());
    }
}