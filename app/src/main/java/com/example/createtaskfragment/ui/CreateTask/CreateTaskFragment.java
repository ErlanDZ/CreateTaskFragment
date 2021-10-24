package com.example.createtaskfragment.ui.CreateTask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.createtaskfragment.R;
import com.example.createtaskfragment.databinding.FragmentCreateTaskBinding;
import com.example.createtaskfragment.utils.Constants;

import java.util.Calendar;
import java.util.Random;

public class CreateTaskFragment extends Fragment {

    // Send send;
    Calendar date;
    String title;
    String userChoosedDate;
    String image;
    String time;
    int  myView;
    private FragmentCreateTaskBinding binding;
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    image = uri.toString();
                    Glide.with(binding.imageCreate)
                            .load(image)
                            .centerCrop()
                            .into(binding.imageCreate);
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setUpImageView();
        return root;
    }


    private void setUpImageView() {
        binding.imageCreate.setOnClickListener(v ->
                mGetContent.launch("image/*"));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSetDate.setOnClickListener(v ->
                showDateTimePicker());

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        binding.btnCreateTaskFragment.setOnClickListener(v -> {
            Log.e("tag", "click");
            title = binding.edCreateTaskFragment.getText().toString();
            TaskModel model = new TaskModel(R.layout.item_task, title, userChoosedDate + "/" + time, image);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.TITLE, model);
            navController.navigate(R.id.nav_home_main, bundle);

        });

    }

    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();

        date = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (view, year, monthOfYear, dayOfMonth) -> {
            date.set(year, monthOfYear, dayOfMonth);
            new TimePickerDialog(requireContext(), (view1, hourOfDay, minute) -> {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);

                time = hourOfDay + " : " + minute;
                userChoosedDate = date.get(Calendar.MONTH) + "." + date.get(Calendar.DAY_OF_MONTH);

                Log.v("ololo", "The choosen one " + date.getTime());
                binding.txtSetTimeCreateTask.setText(time);
                binding.txtSetDateCreateTask.setText(userChoosedDate);

            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    public interface Send {
//        void getList(ArrayList<TaskModel> list);
//    }
}
