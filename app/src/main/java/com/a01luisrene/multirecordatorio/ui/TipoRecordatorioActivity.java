package com.a01luisrene.multirecordatorio.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.fragments.AgregarTipoRecordatorioFragment;


public class TipoRecordatorioActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_recordatorio);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main_type_reminder, AgregarTipoRecordatorioFragment.crear())
                .commit();

    }
}
