package com.a01luisrene.multirecordatorio.ui;

import android.content.res.Configuration;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.MainActivity;
import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.fragmentos.AgregarRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.fragmentos.DetalleRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;

public class DetalleRecordatorioActivity extends AppCompatActivity implements View.OnClickListener {

    private CollapsingToolbarLayout mCtRecordatorio;
    private FloatingActionButton mFabEditar;
    private DetalleRecordatorioFragmento mDetalleRecordatorioFragmento;
    private Recordatorios itemsRecordatorios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_recordatorio);

        mCtRecordatorio = (CollapsingToolbarLayout) findViewById(R.id.ct_recordatorio);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFabEditar = (FloatingActionButton) findViewById(R.id.fab_editar);
        mFabEditar.setOnClickListener(this);

        //Cierro la actividad si el dispositivo en orientación LANDSCAPE
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {

            Bundle datos = this.getIntent().getExtras();
            int agregarRecordatorioFragmento = datos.getInt(MainActivity.CARGAR_NUEVO_RECORDATORIO_FRAGMENTO);

            if(agregarRecordatorioFragmento == 1){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_contenedor_detalle, new AgregarRecordatorioFragmento())
                        .commit();

                //Oculto el botón editar
                mFabEditar.hide();
                mCtRecordatorio.setTitle(getString(R.string.agregar_recordatorio));


            }else{

                itemsRecordatorios = getIntent().getParcelableExtra(DetalleRecordatorioFragmento.ID_RECORDATORIO);

                mDetalleRecordatorioFragmento = DetalleRecordatorioFragmento.newInstance(itemsRecordatorios);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl_contenedor_detalle, mDetalleRecordatorioFragmento);
                ft.commit();
                mFabEditar.show();
            }


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_editar:
                Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show();
        }
    }
}
