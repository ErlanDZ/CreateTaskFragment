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
//        if (getArguments() != null) {
//            model = (TaskModel) getArguments().getSerializable(Constants.TITLE);
//            list.add(model);
//            Log.e("anime", "HomeFragment  " + model.toString());
//        }
        initRecyclerview();
        setUpObserve();
        swipedDelete();

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

    private void swipedDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
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
                        App.getInstance().dao().delete(models.get(viewHolder.getAdapterPosition()));
                        adapter.delete(viewHolder.getAdapterPosition());
                        Toast.makeText(getActivity(), "УДАЛЕНО", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        }).attachToRecyclerView(binding.recyclerViewHomeFragment);
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