package com.example.createtaskfragment.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.createtaskfragment.databinding.FragmentHomeBinding;
import com.example.createtaskfragment.ui.CreateTask.TaskAdapter;
import com.example.createtaskfragment.ui.CreateTask.TaskModel;
import com.example.createtaskfragment.utils.App;
import com.example.createtaskfragment.utils.OnClick;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<TaskModel> models = new ArrayList<>();

    private HomeViewModel homeViewModel;
    private TaskAdapter adapter = new TaskAdapter();
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
        initRecyclerview();
        setUpObserve();
        onLongDelete();

    }

    private void onLongDelete() {
        adapter.setOnClick(new OnClick() {
            @Override
            public void click(TaskModel taskModel, int position) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("ВНИМАНИЕ");
                alertDialog.setMessage("ТОЧНО УДАЛИТЬ????");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetChanged();
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.getInstance().dao().delete(models.get(position));
                        adapter.delete(position);
                        Toast.makeText(getActivity(), "УДАЛЕНО", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void setUpObserve() {
        App.getInstance().dao().getAll().observe(getViewLifecycleOwner(), new Observer<List<TaskModel>>() {
            @Override
            public void onChanged(List<TaskModel> taskModels) {
                adapter.addList((ArrayList<TaskModel>) taskModels);
                models = taskModels;
                Log.e("anime", "onChanged: "+ models.toString() );
            }
        });
    }


    private void initRecyclerview() {
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