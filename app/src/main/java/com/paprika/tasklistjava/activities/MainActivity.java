package com.paprika.tasklistjava.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paprika.tasklistjava.R;
import com.paprika.tasklistjava.adapter.TaskAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText etTask;
    private Button buttonTask;
    private RecyclerView recyclerTask;
    private ArrayList itemList;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setupRecyclerView();
        buttonTask.setOnClickListener(this::addTask);

    }

    private void setupRecyclerView() {
        itemList = new ArrayList<>();
        adapter = new TaskAdapter(itemList);
        recyclerTask.setLayoutManager(new LinearLayoutManager(this));
        recyclerTask.setAdapter(adapter);
    }

    private void init(){
        etTask = findViewById(R.id.et_task);
        buttonTask = findViewById(R.id.buttonAdd);
        recyclerTask = findViewById(R.id.rvTask);

    }


    private void addTask(View view){
        String taskText = etTask.getText().toString().trim();
        if (!taskText.isEmpty()){
            itemList.add(taskText);
            adapter.notifyItemInserted(itemList.size()-1);
            etTask.setText("");
        }else {
            Toast.makeText(getApplicationContext(), "por favor ingresa una tarea", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            int position = data.getIntExtra("position", -1);
            String newTask = data.getStringExtra("newTask");

            if (position != -1 && newTask != null) {
                itemList.set(position, newTask);
                adapter.notifyItemChanged(position);
                Toast.makeText(getApplicationContext(), "Tarea actualizada", Toast.LENGTH_LONG).show();
            } else if (data.hasExtra("position")) {
                itemList.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Tarea eliminada", Toast.LENGTH_LONG).show();
            }
        }
    }

}

