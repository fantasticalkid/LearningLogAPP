package com.example.learninglogapp;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {
    private List<TaskEntity> tasks;
    private AppDatabase db;

    public ToDoListAdapter(List<TaskEntity> tasks, AppDatabase db) {
        this.tasks = tasks;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskEntity task = tasks.get(position);
        holder.taskTitle.setText(task.getTitle());
        holder.taskDescription.setText(task.getDescription());

        holder.completeButton.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    holder.itemView.getContext(),
                    (view, year, month, dayOfMonth) -> {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                        Date completedDate = calendar.getTime();

                        // Format Date to String
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String completedDateString = dateFormat.format(completedDate);

                        // Update the task status and completed date
                        task.setCompleted(true);
                        task.setCompletedDate(completedDateString);

                        // Update the task in database
                        new DeleteTaskAsyncTask().execute(task);
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();

        });

        holder.deleteButton.setOnClickListener(v -> {
            tasks.remove(position);
            notifyItemRemoved(position);
            new DeleteTaskAsyncTask().execute(task);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;
        TextView taskDescription;
        Button completeButton;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskDescription = itemView.findViewById(R.id.task_description);
            completeButton = itemView.findViewById(R.id.complete_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }

    private class UpdateTaskAsyncTask extends AsyncTask<TaskEntity, Void, Void> {

        @Override
        protected Void doInBackground(TaskEntity... taskEntities) {
            db.taskDao().update(taskEntities[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new GetAllTasksAsyncTask().execute();
        }
    }

    private class GetAllTasksAsyncTask extends AsyncTask<Void, Void, List<TaskEntity>> {

        @Override
        protected List<TaskEntity> doInBackground(Void... voids) {
            return db.taskDao().getAll();
        }

        @Override
        protected void onPostExecute(List<TaskEntity> taskEntities) {
            tasks = taskEntities;
            notifyDataSetChanged();
        }
    }

    private class DeleteTaskAsyncTask extends AsyncTask<TaskEntity, Void, Void> {

        @Override
        protected Void doInBackground(TaskEntity... taskEntities) {
            db.taskDao().delete(taskEntities[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new GetAllTasksAsyncTask().execute();
        }
    }
}
