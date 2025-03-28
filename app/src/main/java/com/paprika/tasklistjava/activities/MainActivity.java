package com.paprika.tasklistjava.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.paprika.tasklistjava.R;
import com.paprika.tasklistjava.adapter.TaskAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private EditText etTask;
    private MaterialButton buttonTask;
    private RecyclerView recyclerTask;
    private TaskAdapter adapter;
    private SharedPreferences sharedPreferences;
    private List<String> itemList;
    private List<String> filteredList;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("TaskPrefs", Context.MODE_PRIVATE);
        init();
        setupRecyclerView();
        setupSearchView();

        buttonTask.setOnClickListener(this::addTask);
    }

    private void init() {
        searchView = findViewById(R.id.searchView);
        etTask = findViewById(R.id.et_task);
        buttonTask = findViewById(R.id.buttonAdd);
        recyclerTask = findViewById(R.id.rvTask);
    }

    private void setupRecyclerView() {
        itemList = loadTasks();
        filteredList = new ArrayList<>(itemList);
        adapter = new TaskAdapter(this, filteredList);
        recyclerTask.setLayoutManager(new LinearLayoutManager(this));
        recyclerTask.setAdapter(adapter);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(itemList);
        } else {
            for (String task : itemList) {
                if (task.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(task);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void addTask(View view) {
        String taskText = etTask.getText().toString().trim();
        if (!taskText.isEmpty()) {
            itemList.add(taskText);
            saveTasks(itemList);
            filterList(searchView.getQuery().toString());
            adapter.notifyItemInserted(filteredList.size() - 1);
            etTask.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Por favor ingresa una tarea", Toast.LENGTH_LONG).show();
        }
    }

    private void saveTasks(List<String> tasks) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> taskSet = new HashSet<>(tasks);
        editor.putStringSet("tasks", taskSet);
        editor.apply();
    }

    private List<String> loadTasks() {
        Set<String> taskSet = sharedPreferences.getStringSet("tasks", new HashSet<>());
        return new ArrayList<>(taskSet);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("newTask") && data.hasExtra("position")) {
                String newTask = data.getStringExtra("newTask");
                int position = data.getIntExtra("position", -1);
                if (position >= 0 && position < itemList.size()) {
                    itemList.set(position, newTask);
                    filterList(searchView.getQuery().toString());
                    adapter.notifyItemChanged(position);
                    saveTasks(itemList);
                }
            } else if (data != null && data.hasExtra("deleted") && data.hasExtra("position")) {
                int position = data.getIntExtra("position", -1);
                if (position >= 0 && position < itemList.size()) {
                    itemList.remove(position);
                    filterList(searchView.getQuery().toString());
                    adapter.notifyItemRemoved(position);
                    saveTasks(itemList);
                }
            }
        }
    }
}

