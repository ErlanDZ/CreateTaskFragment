package com.example.createtaskfragment.utils;

import android.app.Application;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.createtaskfragment.room.DataBase;

public class App extends Application {
    public static App instance;
    public static DataBase  dataBase =  null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        getInstance();
    }
    public static DataBase getInstance(){
        if (dataBase == null){
            dataBase = Room.databaseBuilder(instance.getApplicationContext(),DataBase.class, "todo" )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return dataBase;
    }
}
