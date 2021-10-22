package com.example.createtaskfragment.ui.CreateTask;

import java.io.Serializable;

public class TaskModel implements Serializable {
    int color;
    String title;
    String time;
    String imageGallery;

    public TaskModel(int color, String title, String time, String imageGallery) {
        this.color = color;
        this.title = title;
        this.time = time;
        this.imageGallery = imageGallery;






    }
}
