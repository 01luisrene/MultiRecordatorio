package com.a01luisrene.multirecordatorio.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.fragments.ListaCategoriaRecordatoriosFragment;


public class TipoRecordatorioActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_recordatorio);

        ListaCategoriaRecordatoriosFragment fragment = new ListaCategoriaRecordatoriosFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_contenedor_tipo_recordatorio, fragment)
                .commit();
        //Código para reemplazar un fragment
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main_type_reminder, AgregarCategotiaRecordatorioFragment.crear())
                .commit();
        */

    }

}
