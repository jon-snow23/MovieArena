package com.moviearena.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.moviearena.models.MovieDetailsModel;

@Database(entities = {MovieDetailsModel.class}, version = 1)
public abstract class MyRoomDatabase extends RoomDatabase {

    private static volatile MyRoomDatabase myRoomDatabase;

    public abstract MyDao getDao();

    public static MyRoomDatabase getInstance(Context context) {
        if (myRoomDatabase == null) {
            synchronized (MyRoomDatabase.class) {
                if (myRoomDatabase == null) {
                    myRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDatabase.class, "cache_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return myRoomDatabase;
    }
}
