package com.example.createtaskfragment.ui.CreateTask;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.createtaskfragment.databinding.ItemTaskBinding;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    ArrayList<TaskModel> list;
    ItemTaskBinding binding;


    public TaskAdapter(ArrayList<TaskModel> list){
        this.list = list;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.onFill(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        ItemTaskBinding binding;

        public TaskViewHolder(ItemTaskBinding itemTaskBinding) {
            super(itemTaskBinding.getRoot());
            this.binding = itemTaskBinding;
        }
        public void onFill(TaskModel model){
            binding.txtTitle.setText(model.title);
            binding.txtDate.setText(model.date);
            binding.viewColor.setBackgroundColor(model.color);
            Glide.with(binding.imageItemTask).load(model.imageGallery).centerCrop().into(binding.imageItemTask);
        }
    }

}
