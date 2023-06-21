package com.example.learninglogapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CompletedItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_items);

        ListView completedItemsListView = findViewById(R.id.completedItemsListView);

        // Get the date passed from MainActivity
        int year = getIntent().getIntExtra("year", 0);
        int month = getIntent().getIntExtra("month", 0);
        int day = getIntent().getIntExtra("day", 0);

        // TODO: Fetch the completed items from the database using the date
        ArrayList<String> completedItems = new ArrayList<>();
        // For now, let's add some dummy data
        completedItems.add("Dummy completed item 1");
        completedItems.add("Dummy completed item 2");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, completedItems);
        completedItemsListView.setAdapter(adapter);
    }
}
