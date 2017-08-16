package com.a01luisrene.multirecordatorio.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.fragmentos.AgregarCategotiaRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.fragmentos.ListaCategoriaRecordatoriosFragmento;


public class CategoriaRecordatorioActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_recordatorio);

        AgregarCategotiaRecordatorioFragmento fragment = new AgregarCategotiaRecordatorioFragmento();

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_categoria_recordatorio, fragment)
                    .commit();
        }

    }

}
