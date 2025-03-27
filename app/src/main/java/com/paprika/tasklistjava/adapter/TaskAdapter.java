package com.paprika.tasklistjava.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paprika.tasklistjava.R;
import com.paprika.tasklistjava.activities.DetailActivity;
import com.paprika.tasklistjava.activities.MainActivity;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<String> taskList;

    public TaskAdapter(List<String> taskList){
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarea, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
        String task = taskList.get(position);
        holder.tvTaskList.setText(task);

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("task", task);
            intent.putExtra("position", position);
            ((MainActivity) context).startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskList;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskList = itemView.findViewById(R.id.tvTaskList);

        }
    }


}
