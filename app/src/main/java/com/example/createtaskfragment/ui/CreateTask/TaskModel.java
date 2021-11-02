package com.example.createtaskfragment.ui.CreateTask;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class TaskModel implements Serializable {

    @PrimaryKey(autoGenerate = true )
    public long id;
   public int color;
   public String title;
   public String time;
   public String imageGallery;

    public TaskModel(int color, String title, String time, String imageGallery) {
        this.color = color;
        this.title = title;
        this.time = time;
        this.imageGallery = imageGallery;
    }
}
