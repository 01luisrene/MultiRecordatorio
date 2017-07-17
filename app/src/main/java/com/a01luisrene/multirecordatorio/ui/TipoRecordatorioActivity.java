package com.a01luisrene.multirecordatorio.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.fragments.AgregarTipoRecordatorioFragment;


public class TipoRecordatorioActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_recordatorio);

        AgregarTipoRecordatorioFragment fragment = new AgregarTipoRecordatorioFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.cm_tipo_recordatorio, fragment)
                .commit();
        //Código para reemplazar un fragment
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main_type_reminder, AgregarTipoRecordatorioFragment.crear())
                .commit();
        */
    }
}
