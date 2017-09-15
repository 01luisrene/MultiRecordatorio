package com.a01luisrene.multirecordatorio.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.ui.fragmentos.ActualizarRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.ui.fragmentos.AgregarRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.ui.fragmentos.Cover;
import com.a01luisrene.multirecordatorio.ui.fragmentos.DetalleRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.ui.fragmentos.ListaRecordatoriosFragmento;
import com.a01luisrene.multirecordatorio.interfaces.InterfaceCrud;
import com.a01luisrene.multirecordatorio.interfaces.InterfaceItemClic;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.io.db.DataBaseManagerRecordatorios;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener,
        InterfaceCrud,
        InterfaceItemClic{

    public static final String CARGAR_NUEVO_RECORDATORIO_FRAGMENTO = "cargar_nuevo_recordatorio_fragmento";
    public static final int VALOR_ENVIADO_NUEVO_RECORDATORIO = 1;
    public static final int CODIGO_RESPUESTA_NUEVO_RECORDATORIO = 103;
    public static final int CODIGO_RESPUESTA_ACTUALIZAR_ELIMINAR_RECORDATORIO = 1986;

    private static final int READ_WRITE_EXTERNAL_STORAGE_PERMISSION = 2;
    private static final int REQUEST_CODE_READ_WRITE_EXTERNAL_STORAGE = 3;
    private static final int READ_CONTACTS_PERMISSION = 6;
    private static final int REQUEST_CODE_READ_CONTACTS = 7;
    private static final int SEND_SMS_PERMISSION = 8;
    private static final int REQUEST_CODE_SEND_SMS = 9;

    FragmentManager fm;
    ListaRecordatoriosFragmento mListaRecordatoriosFragmento;
    DetalleRecordatorioFragmento mDetalleRecordatorioFragmento;
    AgregarRecordatorioFragmento mAgregarRecordatorioFragmento;
    ActualizarRecordatorioFragmento mActualizarRecordatorioFragmento;

    Cover cover;
    FloatingActionButton mFabAgregarRecordatorio;

    Toolbar mToolbar;

    private Animation fab_abrir,fab_cerrar;

    private DataBaseManagerRecordatorios mManager;

    private boolean dosPaneles = false;
    private boolean esFabAbrir = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        mListaRecordatoriosFragmento = new ListaRecordatoriosFragmento();
        mAgregarRecordatorioFragmento = new AgregarRecordatorioFragmento();
        cover = new Cover();

        mManager = new DataBaseManagerRecordatorios(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Botón flotante
        mFabAgregarRecordatorio = (FloatingActionButton) findViewById(R.id.fab_agregar_recordatorio);
        fab_abrir = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_cerrar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        mFabAgregarRecordatorio.setOnClickListener(this);


        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_contenedor, mListaRecordatoriosFragmento)
                    .commit();

        //Función para determinar patrón Maestro - Detalle
        determinePaneLayout(savedInstanceState);

        //TODO: agregar una función para agregar los permisos en tiempo de ejecución que utilizara mi app
        //SMS, CONTACTOS, READ_EXTERNAL_STORAGE_PERMISSION

        }

    //=====================================@Override===============================================//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //Listener para el EditText
        searchView.setOnQueryTextListener(this);
        //Listener para el cierre y apertura de widget
        MenuItem menuItem = MenuItemCompat.setOnActionExpandListener(searchItem, this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);

    }

    //Eventos del buscador
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        //Toast.makeText(this, "Buscador activado", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        //Toast.makeText(this, "Buscador desactivado", Toast.LENGTH_SHORT).show();
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        //Se ejecuta cuando presionamos enter
        Toast.makeText(this, "Buscando en la DB", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Se ejecuta mientras escribimos en el campo search
        Toast.makeText(this, "Busscando...", Toast.LENGTH_SHORT).show();
        return false;
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fab_agregar_recordatorio:
                if(Utilidades.smartphone){

                    Intent i = new Intent(this, DetalleRecordatorioActivity.class);
                    i.putExtra(CARGAR_NUEVO_RECORDATORIO_FRAGMENTO, VALOR_ENVIADO_NUEVO_RECORDATORIO);
                    startActivityForResult(i, CODIGO_RESPUESTA_NUEVO_RECORDATORIO);

                }else{
                    //Reemplazar fragmento por AgregarRecordatorioFragmento
                    abrirFragmento(mAgregarRecordatorioFragmento);
                    //Oculto el botón FAB agregar
                    if(!esFabAbrir){
                        animateFAB(fab_cerrar, false);
                    }
                    esFabAbrir = true;
                }
                break;
        }

    }

    @Override
    public void onBackPressed() {
        //Muestro el botón FAB agregar
        if(esFabAbrir){
            animateFAB(fab_abrir, true);
        }
        esFabAbrir = false;

        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            if(requestCode == CODIGO_RESPUESTA_NUEVO_RECORDATORIO ) {

                Recordatorios recordatorio = data.getExtras()
                        .getParcelable(AgregarRecordatorioFragmento.LLAVE_RETORNO_AGREGAR_RECORDATORIO);

                if (mListaRecordatoriosFragmento != null) {
                    mListaRecordatoriosFragmento.agregarItemRecordatorio(recordatorio);
                }

            }
            if(requestCode == CODIGO_RESPUESTA_ACTUALIZAR_ELIMINAR_RECORDATORIO){
                boolean valorObtenido = data.getExtras()
                        .getBoolean(DetalleRecordatorioActivity.LLAVE_RETORNO_ELIMINAR_RECORDATORIO);
                if(valorObtenido){
                    mListaRecordatoriosFragmento.removerItemRecordatorio();
                }

                Recordatorios recordatorio = data.getExtras()
                        .getParcelable(ActualizarRecordatorioFragmento.LLAVE_RETORNO_ACTUALIZAR_RECORDATORIO);
                if(recordatorio != null){
                    mListaRecordatoriosFragmento.actualizarItemRecordatorio(recordatorio);
                }
            }

        }

    }

    //=====================================Tareas Asincronas===============================================//
    private class buscarTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            //Toast.makeText(MainActivity.this, "Buscando...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //cursor = manager.buscarRecordatorio(textView.getText().toString());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //adapter.changeCursor(cursor);
            // Toast.makeText(MainActivity.this, "Busqueda finalizada...", Toast.LENGTH_SHORT).show();
        }
    }

    //===============================FUNCIONES PROPIAS=================================//
    private void determinePaneLayout(Bundle savedInstanceState) {

        View contenedorLateral = findViewById(R.id.fl_contenedor_lateral);

        if (contenedorLateral != null) {
            dosPaneles = true;
            Utilidades.smartphone = false;
            //Agregar un fragmento cover
            if(savedInstanceState == null)
                abrirFragmento(cover);

            //Mantener en modo portrait la landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }else{
            Utilidades.smartphone = true;
            //Mantener en modo portrait la pantalla
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void animateFAB(Animation animation, boolean clic){
        mFabAgregarRecordatorio.startAnimation(animation);
        if(clic){
            mFabAgregarRecordatorio.setClickable(true);
        }else{
            mFabAgregarRecordatorio.setClickable(false);
        }

    }
    private void abrirFragmento(Fragment nuevoFragmento) {
        Fragment contenedorFragmento = getSupportFragmentManager().findFragmentById(R.id.fl_contenedor_lateral);

        if (contenedorFragmento == null){
            agregarFragmento(nuevoFragmento);
        } else{
            if (!contenedorFragmento.getClass().getName().equalsIgnoreCase(nuevoFragmento.getClass().getName())) {
                reemplazarFragmento(nuevoFragmento);
            }
        }
    }
    private void agregarFragmento(Fragment nuevoFragmento) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fl_contenedor_lateral, nuevoFragmento);
        ft.commit();

    }
    private void reemplazarFragmento(Fragment nuevoFragmento) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_contenedor_lateral, nuevoFragmento);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

    //=========================================INTERFACES=========================================//

    @Override
    public void itemSeleccionado(Recordatorios recordatorios) {
        if (dosPaneles) { // Actividad única con lista y detalle

            //Reemplazar el diseño del marco con el fragmento de detalle correcto
            mDetalleRecordatorioFragmento =
                    DetalleRecordatorioFragmento
                    .detalleRecordatorio(recordatorios);
            reemplazarFragmento(mDetalleRecordatorioFragmento);

            //Muestro el botón FAB agregar
            if(esFabAbrir){
                animateFAB(fab_abrir, true);
            }
            esFabAbrir = false;

        } else { // Actividades separadas

            // Carga la actividad Detalle
            Intent i = new Intent(this, DetalleRecordatorioActivity.class);
            i.putExtra(DetalleRecordatorioFragmento.KEY_RECORDATORIO, recordatorios);
            startActivityForResult(i, CODIGO_RESPUESTA_ACTUALIZAR_ELIMINAR_RECORDATORIO);
        }
    }

    @Override
    public void agregarItem(Recordatorios recordatorios) {

        if (mListaRecordatoriosFragmento != null) {
            mListaRecordatoriosFragmento.agregarItemRecordatorio(recordatorios);
        }

        //Reemplazo por el cover
        abrirFragmento(cover);

        //Muestro el botón FAB agregar
        if(esFabAbrir){
            animateFAB(fab_abrir, true);
        }
        esFabAbrir = false;
    }

    @Override
    public void actualizarItem(Recordatorios recordatorios) {
        if(mListaRecordatoriosFragmento != null){
            mListaRecordatoriosFragmento.actualizarItemRecordatorio(recordatorios);
        }
        //Reemplazo por el cover
        abrirFragmento(cover);
    }

    @Override
    public void removerItem() {
        if(mListaRecordatoriosFragmento != null){
            mListaRecordatoriosFragmento.removerItemRecordatorio();
        }
        //Reemplazo por el cover
        abrirFragmento(cover);
    }

    @Override
    public void cargarItem(Recordatorios recordatorios) {
        mActualizarRecordatorioFragmento =
                ActualizarRecordatorioFragmento
                        .actualizarRecordatorio(recordatorios);
        //Reemplazar fragmento por ActualizarRecordatorioFragmento
        reemplazarFragmento(mActualizarRecordatorioFragmento);
    }

}