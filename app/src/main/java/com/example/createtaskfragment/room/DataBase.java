package com.example.createtaskfragment.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.createtaskfragment.ui.CreateTask.TaskModel;

@Database(entities = {TaskModel.class}, version = 1)
public abstract  class DataBase extends RoomDatabase {
    public abstract Dao dao();
}
