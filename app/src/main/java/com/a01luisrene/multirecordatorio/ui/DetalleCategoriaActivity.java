package com.a01luisrene.multirecordatorio.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.ui.fragmentos.AgregarCategoriaFragmento;

public class DetalleCategoriaActivity extends AppCompatActivity {

    Toolbar mToolbar;
    AgregarCategoriaFragmento mAgregarCatRecordatorioFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_categoria);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mAgregarCatRecordatorioFrag = new AgregarCategoriaFragmento();

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_detalle_categoria, mAgregarCatRecordatorioFrag)
                    .commit();
        }
    }

    /*Se cumple cuando la activity se finaliza
    @Override
    public void finish() {
        //Se ejecuta solo si AgregarCategotiaRecordatorioFragmento existe
        if(mAgregarCatRecordatorioFrag != null){

            mAgregarCatRecordatorioFrag.enviarNuevasCategoriasRecordatorios();

        }
        super.finish();
    }
    */
}
