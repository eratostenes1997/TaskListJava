package com.paprika.tasklistjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
}