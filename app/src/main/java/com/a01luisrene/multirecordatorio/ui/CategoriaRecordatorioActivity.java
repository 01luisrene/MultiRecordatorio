package com.a01luisrene.multirecordatorio.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.fragments.AgregarCategotiaRecordatorioFragment;
import com.a01luisrene.multirecordatorio.fragments.ListaCategoriaRecordatoriosFragment;


public class CategoriaRecordatorioActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_recordatorio);

        AgregarCategotiaRecordatorioFragment fragment = new AgregarCategotiaRecordatorioFragment();

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_contenedor_tipo_recordatorio, fragment)
                    .commit();
        }

    }

}
