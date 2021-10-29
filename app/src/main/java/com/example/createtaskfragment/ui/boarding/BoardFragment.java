package com.example.createtaskfragment.ui.boarding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.createtaskfragment.R;
import com.example.createtaskfragment.databinding.FragmentBoardBinding;
import com.example.createtaskfragment.utils.Constants;


public class BoardFragment extends Fragment {

    FragmentBoardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPositionFromViewPagerAdapter();
    }

    private void getPositionFromViewPagerAdapter() {
        if (getArguments() != null) {
            int position = getArguments().getInt(Constants.POSITION_FRAGMENT);
            switch (position){
                case 0:
                    binding.txtTitle.setText(R.string.title1);
                    binding.txtDescription.setText(R.string.title1_1);
                    binding.imageOnBoard.setAnimation("completing.tasks.json");

                    break;
                case 1:
                    binding.txtTitle.setText(R.string.title2);
                    binding.imageOnBoard.setAnimation("logo.animation.json");
                    break;
                case 2:
                    binding.txtTitle.setText(R.string.title3);
                    binding.imageOnBoard.setAnimation("to.do.list.animation.json");
                    break;
            }
        }

    }
}