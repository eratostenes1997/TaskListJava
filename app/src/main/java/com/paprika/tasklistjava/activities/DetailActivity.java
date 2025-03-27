package com.paprika.tasklistjava.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.paprika.tasklistjava.R;

public class DetailActivity extends AppCompatActivity {
    private MaterialSwitch mySwitch;
    private TextView tvTask;
    private LinearLayout myLinear;
    private EditText etTaskDetail;
    private MaterialButton btnEliminar;
    private MaterialButton btnEditar;
    private String task;
    private int position;
    private TextView tvEdicion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        mySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            validarSwitch(isChecked);
        });

        btnEliminar.setOnClickListener(this::eliminarTareas);

        btnEditar.setOnClickListener(this::editarTareas);


    }

    private void editarTareas(View view) {
        String newTask = etTaskDetail.getText().toString().trim();
        if (!newTask.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra("position", position);
            intent.putExtra("newTask", newTask);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void eliminarTareas(View view) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void validarSwitch(boolean isChecked) {
        if (isChecked) {
            tvEdicion.setText("Desactivar edición");
            myLinear.setVisibility(View.VISIBLE);
        } else {
            tvEdicion.setText("Activar edición");
            myLinear.setVisibility(View.GONE);
        }
    }

    private void init(){
        mySwitch = findViewById(R.id.switchEdicion);
        myLinear = findViewById(R.id.linearEdicion);
        tvTask = findViewById(R.id.tvTaskDetail);
        etTaskDetail = findViewById(R.id.etTaskEdit);
        btnEliminar = findViewById(R.id.btn_eliminar_tarea);
        tvEdicion = findViewById(R.id.activarEdicion);
        btnEditar = findViewById(R.id.btn_editar_tarea);


        task = getIntent().getStringExtra("task");
        position = getIntent().getIntExtra("position", -1);

        tvTask.setText(task);

    }
}