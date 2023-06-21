package com.example.learninglogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.os.AsyncTask;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ToDoListAdapter adapter;
    private List<TaskEntity> tasks;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();



        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ToDoListAdapter(tasks, db);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasks = new ArrayList<>();
        adapter = new ToDoListAdapter(tasks, db);  // <-- New code
        recyclerView.setAdapter(adapter);  // <-- New code

        new GetAllTasksAsyncTask().execute();
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoListActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private class GetAllTasksAsyncTask extends AsyncTask<Void, Void, List<TaskEntity>> {

        @Override
        protected List<TaskEntity> doInBackground(Void... voids) {
            return db.taskDao().getAll();
        }

        @Override
        protected void onPostExecute(List<TaskEntity> taskEntities) {
            tasks = taskEntities;
            adapter = new ToDoListAdapter(tasks, db);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            TaskEntity task = new TaskEntity(title, description);
            new InsertTaskAsyncTask().execute(task);
        }
    }

    private class InsertTaskAsyncTask extends AsyncTask<TaskEntity, Void, Void> {
        @Override
        protected Void doInBackground(TaskEntity... taskEntities) {
            db.taskDao().insert(taskEntities[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new GetAllTasksAsyncTask().execute();
        }
    }
}
