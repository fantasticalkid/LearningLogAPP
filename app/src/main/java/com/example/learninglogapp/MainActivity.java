package com.example.learninglogapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.room.Room;


public class MainActivity extends AppCompatActivity {
    public AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "task-database")
                .fallbackToDestructiveMigration() // Add this line
                .build();

        CalendarView calendarView = findViewById(R.id.calendarView);
        FloatingActionButton fab = findViewById(R.id.fab);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // 實現跳轉到該日期的完成項目查詢頁面
            Intent intent = new Intent(MainActivity.this, CompletedItemsActivity.class);
            intent.putExtra("year", year);
            intent.putExtra("month", month);
            intent.putExtra("day", dayOfMonth);
            startActivity(intent);
        });

        fab.setOnClickListener(v -> {
            // 跳轉到待完成學習目標頁面
            Intent intent = new Intent(MainActivity.this, ToDoListActivity.class);
            startActivity(intent);
        });
    }
}