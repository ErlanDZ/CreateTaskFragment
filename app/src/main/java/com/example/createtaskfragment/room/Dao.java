package com.example.createtaskfragment.room;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.createtaskfragment.ui.CreateTask.TaskModel;

import java.util.ArrayList;
import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Query("SELECT * FROM taskmodel")
    LiveData<List<TaskModel>>getAll();

    @Insert
    void insert(TaskModel taskModel);

    @Update
    void update(TaskModel taskModel);

    @Delete
    void delete(TaskModel taskModel);
}
