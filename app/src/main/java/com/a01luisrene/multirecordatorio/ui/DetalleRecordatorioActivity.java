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

import com.a01luisrene.multirecordatorio.MainActivity;
import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.fragmentos.ActualizarRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.fragmentos.AgregarRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.fragmentos.DetalleRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;


public class DetalleRecordatorioActivity extends AppCompatActivity implements View.OnClickListener {

    //Constantes
    public static int MILISEGUNDOS_ESPERA = 300;

    //Variables para ocultar el icono eliminar del menú
    public int estadoIconoEliminar;
    MenuItem eliminarItem;

    CollapsingToolbarLayout mCtCategoriaRecordatorio;
    FloatingActionButton mFabEditar;
    Recordatorios mItemsRecordatorios;
    DataBaseManagerRecordatorios mManagerRecordatorios;
    DetalleRecordatorioFragmento mDetalleRecordatorioFragmento;
    AgregarRecordatorioFragmento mAgregarRecordatorioFragmento;
    ActualizarRecordatorioFragmento mActualizarRecordatorioFragmento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_recordatorio);

        mManagerRecordatorios = new DataBaseManagerRecordatorios(this);

        mCtCategoriaRecordatorio = (CollapsingToolbarLayout) findViewById(R.id.ct_categoria_recordatorio);

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

        Bundle datos = this.getIntent().getExtras();
        int agregarRecordatorioFragmento = datos.getInt(MainActivity.CARGAR_NUEVO_RECORDATORIO_FRAGMENTO);


        mItemsRecordatorios = getIntent().getParcelableExtra(DetalleRecordatorioFragmento.ID_RECORDATORIO);


        if (savedInstanceState == null) {
            if(agregarRecordatorioFragmento == 1){

                estadoIconoEliminar = 0;

                mAgregarRecordatorioFragmento = new AgregarRecordatorioFragmento();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_contenedor_detalle, mAgregarRecordatorioFragmento)
                        .commit();

                //Oculto el botón editar
                mFabEditar.setVisibility(View.INVISIBLE);
                mCtCategoriaRecordatorio.setTitle(getString(R.string.agregar_recordatorio));


            }else{

                estadoIconoEliminar = 1;

                mDetalleRecordatorioFragmento = DetalleRecordatorioFragmento.newInstance(mItemsRecordatorios);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl_contenedor_detalle, mDetalleRecordatorioFragmento);
                ft.commit();

                mFabEditar.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_editar:

                //Oculto el icono de eliminar del menu
                eliminarItem.setVisible(false);

                mActualizarRecordatorioFragmento = ActualizarRecordatorioFragmento.newInstance(mItemsRecordatorios);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl_contenedor_detalle, mActualizarRecordatorioFragmento);
                ft.addToBackStack(null);
                ft.commit();

                //Oculto el botón editar
                mFabEditar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle, menu);

        eliminarItem = menu.findItem(R.id.accion_eliminar);
        if (estadoIconoEliminar == 1){
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
                String idRecordatorio = mItemsRecordatorios.getId();

                mManagerRecordatorios.eliminarRecordatorio(idRecordatorio);

                esperarYCerrar(MILISEGUNDOS_ESPERA);

            default:
                return super.onOptionsItemSelected(item);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Mostrar icono eliminar del menú
        if (estadoIconoEliminar == 1){
            eliminarItem.setVisible(true);
            mFabEditar.setVisibility(View.VISIBLE);
        }
    }
}
