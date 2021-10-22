package com.example.createtaskfragment.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.createtaskfragment.databinding.FragmentHomeBinding;
import com.example.createtaskfragment.ui.CreateTask.TaskAdapter;
import com.example.createtaskfragment.ui.CreateTask.TaskModel;
import com.example.createtaskfragment.utils.Constants;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<TaskModel> list = new ArrayList<>();
    private HomeViewModel homeViewModel;
    private TaskModel model;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            model = (TaskModel) getArguments().getSerializable(Constants.TITLE);
            list.add(model);
            Log.e("anime", "HomeFragment  " + model.toString());
        }
        initRecyclerview();
    }


    private void initRecyclerview() {
        TaskAdapter adapter = new TaskAdapter(list);
        binding.recyclerViewHomeFragment.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}