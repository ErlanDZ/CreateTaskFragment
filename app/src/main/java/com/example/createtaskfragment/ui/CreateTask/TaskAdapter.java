package com.example.createtaskfragment.ui.CreateTask;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.createtaskfragment.databinding.ItemTaskBinding;

import java.util.ArrayList;
import java.util.Random;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    ArrayList<TaskModel> list;

    public TaskAdapter(ArrayList<TaskModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
            getRandomColor();
        }

        public void onFill(TaskModel model) {
            binding.txtTitle.setText(model.title);
            binding.txtDate.setText(model.time);
            Random r = new Random();
            int red=r.nextInt(255 - 0 + 1)+0;
            int green=r.nextInt(255 - 0 + 1)+0;
            int blue=r.nextInt(255 - 0 + 1)+0;

            GradientDrawable draw = new GradientDrawable();
            draw.setShape(GradientDrawable.RECTANGLE);
            draw.setColor(Color.rgb(red,green,blue));
            binding.viewColor.setBackground(draw);
            //binding.viewColor.setBackgroundColor(model.color);
            Glide.with(binding.imageItemTask)
                    .load(model.imageGallery)
                    .centerCrop()
                    .into(binding.imageItemTask);
        }
        public void getRandomColor(){
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),rnd.nextInt(256));
            binding.viewColor.setBackgroundColor(color);
        }

    }

}
