package com.a01luisrene.multirecordatorio.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.a01luisrene.multirecordatorio.MainActivity;
import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.fragmentos.ActualizarRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.fragmentos.AgregarRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.fragmentos.DetalleRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.interfaces.InterfaceCrud;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;


public class DetalleRecordatorioActivity extends AppCompatActivity
        implements View.OnClickListener,
        InterfaceCrud {

    //Constantes
    public static final int MILISEGUNDOS_ESPERA = 300;
    public static final String LLAVE_RETORNO_ELIMINAR_RECORDATORIO = "llave.retorno.eliminar.recordatorio";

    //Variables para ocultar el icono eliminar del menú
    public int estadoIconoEliminar;
    private MenuItem eliminarItem;

    private boolean respuestaRetornoEliminarRecordatorio = false;

    private Animation fab_abrir,fab_cerrar;
    private Handler handler;

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
        //Cierro la actividad si el dispositivo en orientación LANDSCAPE
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        mManagerRecordatorios = new DataBaseManagerRecordatorios(this);
        mCtCategoriaRecordatorio = (CollapsingToolbarLayout) findViewById(R.id.ct_categoria_recordatorio);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFabEditar = (FloatingActionButton) findViewById(R.id.fab_editar);
        fab_abrir = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_cerrar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        mFabEditar.setOnClickListener(this);

        Bundle datos = this.getIntent().getExtras();
        int agregarRecordatorioFragmento = datos.getInt(MainActivity.CARGAR_NUEVO_RECORDATORIO_FRAGMENTO);

        mItemsRecordatorios = getIntent().getParcelableExtra(DetalleRecordatorioFragmento.KEY_RECORDATORIO);

        if (savedInstanceState == null) {
            if(agregarRecordatorioFragmento == MainActivity.VALOR_ENVIADO_NUEVO_RECORDATORIO){

                estadoIconoEliminar = 0;
                //Oculto el FAB editar
                mFabEditar.setVisibility(View.INVISIBLE);

                mAgregarRecordatorioFragmento = new AgregarRecordatorioFragmento();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_contenedor_detalle, mAgregarRecordatorioFragmento)
                        .commit();

                mCtCategoriaRecordatorio.setTitle(getString(R.string.agregar_recordatorio));

            }else{

                estadoIconoEliminar = 1;

                mDetalleRecordatorioFragmento = DetalleRecordatorioFragmento.detalleRecordatorio(mItemsRecordatorios);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl_contenedor_detalle, mDetalleRecordatorioFragmento);
                ft.commit();

            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_editar:
                mActualizarRecordatorioFragmento = ActualizarRecordatorioFragmento.actualizarRecordatorio(mItemsRecordatorios);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl_contenedor_detalle, mActualizarRecordatorioFragmento);
                ft.addToBackStack(null);
                ft.commit();

                //Oculto el botón FAB editar
                animateFAB(fab_cerrar, false);
                tamanioFabCero(500); //Obliga que el fab scale a 0

                //Oculto el icono de eliminar del menu
                eliminarItem.setVisible(false);
                break;
        }
    }

    public void animateFAB(Animation animation, boolean clic){
        mFabEditar.startAnimation(animation);
        if(clic){
            mFabEditar.setClickable(true);
        }else{
            mFabEditar.setClickable(false);
        }
    }
    public void tamanioFabCero(int milisegundos){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
            // acciones que se ejecutan tras los milisegundos
            mFabEditar.setScaleX(0);
            mFabEditar.setScaleY(0);
            }
        }, milisegundos);
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
                eliminarRecordatorio();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void eliminarRecordatorio(){
        new AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_info_24dp)
            .setTitle(getString(R.string.adb_titulo_eliminar))
            .setMessage(getString(R.string.adb_mensaje_eliminar))
            .setPositiveButton(getString(R.string.boton_aceptar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    //Id recordatorio
                    String idRecordatorio = mItemsRecordatorios.getId();

                    mManagerRecordatorios.eliminarRecordatorio(idRecordatorio);

                    respuestaRetornoEliminarRecordatorio = true;//Respuesta
                    Intent intent = new Intent();
                    intent.putExtra(LLAVE_RETORNO_ELIMINAR_RECORDATORIO, respuestaRetornoEliminarRecordatorio);
                    setResult(RESULT_OK, intent);

                    esperarYCerrar(MILISEGUNDOS_ESPERA);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                }
            })
            .setNegativeButton(getString(R.string.boton_cancelar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Desplegar mensaje de lamentación

                }
            })
            .show();
    }

    //Esperar unos segundos antes de cerrar la activity
    public void esperarYCerrar(int milisegundos) {
        handler = new Handler();
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
        }
        //Muestro el botón FAB agregar
        animateFAB(fab_abrir, true);
        mFabEditar.setScaleX(1);
        mFabEditar.setScaleY(1);
    }


    @Override
    public void removerItem() {
        //Vacío
    }

    @Override
    public void actualizarItem(Recordatorios recordatorios) {
        //Vacío
    }
}
