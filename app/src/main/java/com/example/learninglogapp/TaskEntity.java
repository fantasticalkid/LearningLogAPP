package com.example.learninglogapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String title = "";

    @NonNull
    private String description = "";

    private boolean completed;

    private String completedDate;

    // Constructor
    public TaskEntity(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
        this.completedDate = null;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    public String getCompletedDate() { return completedDate; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setCompletedDate(String completedDate) { this.completedDate = completedDate; }
}
