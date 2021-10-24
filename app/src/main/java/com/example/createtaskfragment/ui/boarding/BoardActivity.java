package com.example.createtaskfragment.ui.boarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

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
            }
        });
    }

    private void initViewPager() {
        binding.viewPagerBoard.setAdapter(new BoardAdapter(getSupportFragmentManager()));
        binding.wormDotsIndicator.setViewPager(binding.viewPagerBoard);
    }
}