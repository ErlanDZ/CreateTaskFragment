package com.example.createtaskfragment.ui.CreateTask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.createtaskfragment.R;
import com.example.createtaskfragment.databinding.FragmentCreateTaskBinding;
import com.example.createtaskfragment.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateTaskFragment extends Fragment {

    Send send;
    Calendar date;
    private FragmentCreateTaskBinding binding;
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    binding.imageCreate.setImageURI(uri);
                }
            });

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
        binding.btnCreateTaskFragment.setOnClickListener(v -> {
            String title = binding.edCreateTaskFragment.getText().toString();
            Bundle bundle = new Bundle();
            if (getArguments() != null) {
                bundle.putString(Constants.TITLE, title);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_home, bundle);
                ArrayList<TaskModel> list = new ArrayList<>();
                send.getList(list);
            }
        });
        binding.btnSetDate.setOnClickListener(v -> showDateTimePicker());
    }

    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();

        date = Calendar.getInstance();
        new DatePickerDialog(requireContext(), (view, year, monthOfYear, dayOfMonth) -> {
            date.set(year, monthOfYear, dayOfMonth);
            new TimePickerDialog(requireContext(), (view1, hourOfDay, minute) -> {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                Log.v("ololo", "The choosen one " + date.getTime());
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface Send {
        void getList(ArrayList<TaskModel> list);
    }
}
