package com.example.appbase.Dialog;


import android.content.Context;
import android.view.View;
import android.widget.RatingBar;

import androidx.annotation.NonNull;

import com.example.appbase.Base.BaseDialog;
import com.example.appbase.Interface.IClickDialogRate;
import com.example.appbase.R;
import com.example.appbase.databinding.DialogRatingAppBinding;


public class DialogRating extends BaseDialog<DialogRatingAppBinding> {

    private IClickDialogRate iClickDialogRate;
    private final Context context;

    public DialogRating(@NonNull Context context, Boolean cancelAble) {
        super(context, cancelAble);
        this.context = context;
    }

    @Override
    protected DialogRatingAppBinding setBinding() {
        return DialogRatingAppBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.rtb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String getRating = String.valueOf(binding.rtb.getRating());
                switch (getRating) {
                    case "1.0":
                        binding.btnRateUs.setText(context.getResources().getString(R.string.Thankyou));
                        binding.btnNotNow.setVisibility(View.GONE);
                        binding.imgIcon.setImageResource(R.drawable.rating_1);
                        break;
                    case "2.0":
                        binding.btnNotNow.setVisibility(View.GONE);
                        binding.btnRateUs.setText(context.getResources().getString(R.string.Thankyou));
                        binding.imgIcon.setImageResource(R.drawable.rating_2);
                        break;
                    case "3.0":
                        binding.btnNotNow.setVisibility(View.GONE);
                        binding.btnRateUs.setText(context.getResources().getString(R.string.Thankyou));
                        binding.imgIcon.setImageResource(R.drawable.rating_3);
                        break;
                    case "4.0":
                        binding.btnNotNow.setVisibility(View.GONE);
                        binding.btnRateUs.setText(context.getResources().getString(R.string.Thankyou));
                        binding.imgIcon.setImageResource(R.drawable.rating_4);
                        break;
                    case "5.0":
                        binding.btnNotNow.setVisibility(View.GONE);
                        binding.btnRateUs.setText(context.getResources().getString(R.string.Thankyou));
                        binding.imgIcon.setImageResource(R.drawable.rating_5);
                        break;
                    default:
                        binding.btnRateUs.setText(context.getResources().getString(R.string.rate_us));
                        binding.btnNotNow.setVisibility(View.VISIBLE);
                        binding.imgIcon.setImageResource(R.drawable.rating_0);
                        break;
                }
            }
        });
    }

    @Override
    protected void bindView() {
        binding.btnRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.rtb.getRating() == 0) {
                    return;
                }
                if (binding.rtb.getRating() <= 3.0) {
                    binding.imgIcon.setVisibility(View.GONE);
                    iClickDialogRate.send();
                } else {
                    binding.imgIcon.setVisibility(View.VISIBLE);
                    iClickDialogRate.rating();
                }
            }
        });

        binding.btnNotNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickDialogRate.later();
            }
        });
    }

    public void init(IClickDialogRate iClickDialogRate) {
        this.iClickDialogRate = iClickDialogRate;
    }

    public String getRating() {
        return String.valueOf(this.binding.rtb.getRating());
    }

}
