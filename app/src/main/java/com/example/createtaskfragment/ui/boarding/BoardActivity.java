package com.example.createtaskfragment.ui.boarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.animation.AnimatorInflater;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;

import com.example.createtaskfragment.MainActivity;
import com.example.createtaskfragment.R;
import com.example.createtaskfragment.databinding.ActivityBoardBinding;
import com.example.createtaskfragment.utils.Constants;

public class BoardActivity extends AppCompatActivity {

    ActivityBoardBinding binding;
    boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewPager();
        onClickSkip();
        onClickArrow();
        onClickNext();
        onClickBack();
        checkShow();

    }

    private void checkShow() {
        SharedPreferences sharedPreferences = BoardActivity.this.getSharedPreferences(Constants.BOARD_FILE, Context.MODE_PRIVATE);
        isShow = sharedPreferences.getBoolean(Constants.IS_SHOW, false);
        if (isShow){
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void onClickArrow() {
        binding.imageArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.viewPagerBoard.getCurrentItem() == 2) {
                    SharedPreferences sharedPreferences = BoardActivity.this.getSharedPreferences(Constants.BOARD_FILE, Context.MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean(Constants.IS_SHOW, true).apply();
                    Intent intent = new Intent(BoardActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void onClickBack() {
        binding.txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewPagerBoard.setCurrentItem(binding.viewPagerBoard.getCurrentItem() -1);
                binding.txtNext.setVisibility(View.VISIBLE);
                if (binding.viewPagerBoard.getCurrentItem() == 0 || binding.viewPagerBoard.getCurrentItem() == 1){
                    binding.imageArrow.setImageResource(R.drawable.ic_baseline_arrow_forward_24);
                }
            }
        });
    }

    private void onClickNext() {
        binding.txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewPagerBoard.setCurrentItem(binding.viewPagerBoard.getCurrentItem() +1);
                if (binding.viewPagerBoard.getCurrentItem() == 2){
                    binding.txtNext.setVisibility(View.GONE);
                    binding.imageArrow.setImageResource(R.drawable.ic_baseline_check_24);
                }
            }
        });
    }

    private void onClickSkip() {
        binding.btnSkipp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViewPager() {
        binding.viewPagerBoard.setAdapter(new BoardAdapter(getSupportFragmentManager()));
        binding.wormDotsIndicator.setViewPager(binding.viewPagerBoard);
        binding.txtBack.setAlpha(0f);
        binding.txtBack.animate().alpha(1f).setDuration(1500);
        binding.txtNext.setAlpha(0f);
        binding.txtNext.animate().alpha(1f).setDuration(1500);
        binding.btnSkipp.setAlpha(0f);
        binding.btnSkipp.animate().alpha(1f).setDuration(1500);
        binding.imageArrow.setAlpha(0f);
        binding.imageArrow.animate().alpha(1f).setDuration(1500);
        binding.imageArrow.animate().rotation(360).setDuration(1000);
        binding.wormDotsIndicator.setAlpha(0f);
        binding.wormDotsIndicator.animate().alpha(1f).setDuration(1500);
    }
}