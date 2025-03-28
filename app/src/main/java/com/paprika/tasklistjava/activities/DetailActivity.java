package com.paprika.tasklistjava.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.paprika.tasklistjava.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DetailActivity extends AppCompatActivity {
    private MaterialSwitch mySwitch;
    private TextView tvTask;
    private LinearLayout myLinear;
    private EditText etTaskDetail;
    private MaterialButton btnEliminar;
    private MaterialButton btnEditar;
    private String task;
    private int position;
    private SharedPreferences sharedPreferences;
    private List<String> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        mySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> validarSwitch(isChecked));
        btnEliminar.setOnClickListener(this::eliminarTarea);
        btnEditar.setOnClickListener(this::editarTarea);
    }

    private void init() {
        mySwitch = findViewById(R.id.switchEdicion);
        myLinear = findViewById(R.id.linearEdicion);
        tvTask = findViewById(R.id.tvTaskDetail);
        etTaskDetail = findViewById(R.id.etTaskEdit);
        btnEliminar = findViewById(R.id.btn_eliminar_tarea);
        btnEditar = findViewById(R.id.btn_editar_tarea);
        sharedPreferences = getSharedPreferences("TaskPrefs", Context.MODE_PRIVATE);
        taskList = loadTasks();
        task = getIntent().getStringExtra("task");
        position = getIntent().getIntExtra("position", -1);
        tvTask.setText(task);
    }

    private void editarTarea(View view) {
        String newTask = etTaskDetail.getText().toString().trim();
        if (!newTask.isEmpty() && position >= 0) {
            taskList.set(position, newTask);
            saveTasks(taskList);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("position", position);
            resultIntent.putExtra("newTask", newTask);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Por favor ingresa la tarea actualizada", Toast.LENGTH_LONG).show();
        }
    }

    private void eliminarTarea(View view) {
        if (position >= 0 && position < taskList.size()) {
            taskList.remove(position);
            saveTasks(taskList);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("position", position);
            resultIntent.putExtra("deleted", true);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private void validarSwitch(boolean isChecked) {
        myLinear.setVisibility(isChecked ? View.VISIBLE : View.GONE);
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
}
