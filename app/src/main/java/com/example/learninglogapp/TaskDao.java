package com.example.learninglogapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM TaskEntity")
    List<TaskEntity> getAll();

    @Insert
    void insert(TaskEntity task);

    @Update
    void update(TaskEntity task);

    @Delete
    void delete(TaskEntity task);
}
