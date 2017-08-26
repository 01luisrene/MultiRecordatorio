package com.a01luisrene.multirecordatorio.ui;

import android.content.res.Configuration;
import android.os.Handler;
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
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;


public class DetalleRecordatorioActivity extends AppCompatActivity implements View.OnClickListener {

    //Constantes
    public static int MILISEGUNDOS_ESPERA = 300;

    CollapsingToolbarLayout ctRecordatorio;
    FloatingActionButton fabEditar;
    Recordatorios itemsRecordatorios;
    DataBaseManagerRecordatorios managerRecordatorios;
    DetalleRecordatorioFragmento detalleRecordatorioFragmento;
    AgregarRecordatorioFragmento mAgregarRecordatorioFragmento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_recordatorio);

        managerRecordatorios = new DataBaseManagerRecordatorios(this);

        ctRecordatorio = (CollapsingToolbarLayout) findViewById(R.id.ct_recordatorio);


        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabEditar = (FloatingActionButton) findViewById(R.id.fab_editar);
        fabEditar.setOnClickListener(this);

        //Cierro la actividad si el dispositivo en orientación LANDSCAPE
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        Bundle datos = this.getIntent().getExtras();
        int agregarRecordatorioFragmento = datos.getInt(MainActivity.CARGAR_NUEVO_RECORDATORIO_FRAGMENTO);

        if (savedInstanceState == null) {
            if(agregarRecordatorioFragmento == 1){

                mAgregarRecordatorioFragmento = new AgregarRecordatorioFragmento();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_contenedor_detalle, mAgregarRecordatorioFragmento)
                        .commit();

                //Oculto el botón editar
                fabEditar.hide();
                ctRecordatorio.setTitle(getString(R.string.agregar_recordatorio));


            }else{

                itemsRecordatorios = getIntent().getParcelableExtra(DetalleRecordatorioFragmento.ID_RECORDATORIO);

                detalleRecordatorioFragmento = DetalleRecordatorioFragmento.newInstance(itemsRecordatorios);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl_contenedor_detalle, detalleRecordatorioFragmento);
                ft.commit();
                fabEditar.show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle, menu);

        MenuItem eliminarItem = menu.findItem(R.id.accion_eliminar);

        //Muestro el icono de eliminar solo si carga el formulario detalle
        if(detalleRecordatorioFragmento != null){
            eliminarItem.setVisible(true);
        }else{
            eliminarItem.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.accion_eliminar:
                String idRecordatorio = itemsRecordatorios.getId();

                managerRecordatorios.eliminarRecordatorio(idRecordatorio);

                esperarYCerrar(MILISEGUNDOS_ESPERA);

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

    //Esperar unos segundos antes de cerrar la activity
    public void esperarYCerrar(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                // [cerrar activity]
                finish();

            }
        }, milisegundos);
    }
}
