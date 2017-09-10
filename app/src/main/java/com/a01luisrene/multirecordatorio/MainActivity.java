package com.a01luisrene.multirecordatorio;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.fragmentos.ActualizarRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.fragmentos.AgregarRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.fragmentos.Cover;
import com.a01luisrene.multirecordatorio.fragmentos.DetalleRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.fragmentos.ListaRecordatoriosFragmento;
import com.a01luisrene.multirecordatorio.interfaces.InterfaceCrud;
import com.a01luisrene.multirecordatorio.interfaces.InterfaceItemClic;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;
import com.a01luisrene.multirecordatorio.ui.DetalleRecordatorioActivity;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener,
        InterfaceCrud,
        InterfaceItemClic {

    public static final String CARGAR_NUEVO_RECORDATORIO_FRAGMENTO = "cargar_nuevo_recordatorio_fragmento";
    public static final int VALOR_ENVIADO_NUEVO_RECORDATORIO = 1;
    public static final int CODIGO_RESPUESTA_NUEVO_RECORDATORIO = 103;
    public static final int CODIGO_RESPUESTA_ELIMINAR_RECORDATORIO = 105;
    public static final String TAG_FRAGMENTO_ACTUALIZAR = "fragmento_actualizar";
    public static final String TAG_FRAGMENTO_DETALLE = "fragmento_detalle";

    ListaRecordatoriosFragmento mListaRecordatoriosFragmento;
    DetalleRecordatorioFragmento mDetalleRecordatorioFragmento;
    AgregarRecordatorioFragmento mAgregarRecordatorioFragmento;
    Cover cover;

    FloatingActionButton mFabAgregarRecordatorio;
    Toolbar mToolbar;

    private Animation fab_abrir,fab_cerrar;

    private DataBaseManagerRecordatorios mManager;

    ActualizarRecordatorioFragmento mActualizarRecordatorioFragmento;

    private boolean dosPaneles = false;
    private boolean esFabAbrir = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListaRecordatoriosFragmento = new ListaRecordatoriosFragmento();
        cover = new Cover();

        mManager = new DataBaseManagerRecordatorios(this);
    /*
        mManager.insertarCategoriaRecordatorio(null, "imagen.jpg", "Cumpleaño", 1, "28-066-2017");

        mManager.insertarRecoratorio(null, "Cumple de Luis", "Luis Rene Mas Mas","1", "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500", "1145454","1", "1", "1", "2017", "2017","12:20","1");
        mManager.insertarRecoratorio(null, "Cumple de Jose", "Juan Mesia Chicana","1", "Feliz cumpleaños", "1145454","1", "1", "1", "2017", "2017", "18:25","1");
        mManager.insertarRecoratorio(null, "Cumple de Wander", "Wander Rojas briceño","1", "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500", "1145454","1", "1", "1", "2017", "2017", "15:10","1");

 */

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);

        //Botón flotante
        mFabAgregarRecordatorio = (FloatingActionButton) findViewById(R.id.fab_agregar_recordatorio);
        fab_abrir = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_cerrar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        mFabAgregarRecordatorio.setOnClickListener(this);


        if (savedInstanceState == null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_contenedor, mListaRecordatoriosFragmento)
                    .commit();


        }

        //Función para determinar patrón Maestro - Detalle
        determinePaneLayout(savedInstanceState);

        //TODO: agregar una función para agregar los permisos en tiempo de ejecución que utilizara mi app

        }

    private void determinePaneLayout(Bundle savedInstanceState) {

        View detalleItemFragmento = findViewById(R.id.fl_contenedor_lateral);

        if (detalleItemFragmento != null) {
            dosPaneles = true;
            Utilidades.smartphone = false;
            //Agregar un fragmento cover
            if(savedInstanceState == null)
                getSupportFragmentManager().beginTransaction().add(R.id.fl_contenedor_lateral, cover).commit();

            //Mantener en modo portrait la landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }else{
            Utilidades.smartphone = true;
            //Mantener en modo portrait la pantalla
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }



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

                    mAgregarRecordatorioFragmento = new AgregarRecordatorioFragmento();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fl_contenedor_lateral, mAgregarRecordatorioFragmento);
                    ft.addToBackStack(null);
                    ft.commit();

                    //Oculto el botón FAB agregar
                    if(!esFabAbrir){
                        animateFAB(fab_cerrar, false);
                    }
                    esFabAbrir = true;
                }
                break;
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

    @Override
    public void onBackPressed() {
        //Muestro el botón FAB agregar
        if(esFabAbrir){
            animateFAB(fab_abrir, true);
        }
        esFabAbrir = false;

        FragmentManager fm = getSupportFragmentManager();

        ActualizarRecordatorioFragmento actualizarFrag =
                (ActualizarRecordatorioFragmento)
                        getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENTO_ACTUALIZAR);
        DetalleRecordatorioFragmento detalleFrag =
                (DetalleRecordatorioFragmento)
                        getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENTO_DETALLE);
        /*
        if(actualizarFrag != null){
            ft.attach(actualizarFrag);
            ft.commit();
            Log.i("log", "frag actualizar borrado");
        }
        if(detalleFrag != null){
            ft.attach(detalleFrag);
            ft.commit();
            Log.i("log", "frag detalle borrado");


        }
*/
        if(fm.getBackStackEntryCount() > 0){
            fm.popBackStack();
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            if(requestCode == CODIGO_RESPUESTA_NUEVO_RECORDATORIO ){
                boolean valorObtenido = data.getExtras()
                        .getBoolean(AgregarRecordatorioFragmento.LLAVE_RETORNO_RECORDATORIO);
                if(valorObtenido){
                    //Recargo el spinner siempre y cuando que el valor retornado sea `true`
                    if(mListaRecordatoriosFragmento != null){
                        //mListaRecordatoriosFragmento.inserterItemRecordatorio();
                    }

                }
            }

            if(requestCode == CODIGO_RESPUESTA_ELIMINAR_RECORDATORIO){
                boolean valorObtenido = data.getExtras()
                        .getBoolean(DetalleRecordatorioActivity.LLAVE_RETORNO_ELIMINAR_RECORDATORIO);
                if(valorObtenido){
                    //Recargo el spinner siempre y cuando que el valor retornado sea `true`
                    if(mListaRecordatoriosFragmento != null){
                        mListaRecordatoriosFragmento.removerItemRecordatorio();
                    }

                }
            }

        }

    }

    //===============================INTERFACES=========================================//

    @Override
    public void itemSeleccionado(Recordatorios recordatorios) {
        if (dosPaneles) { // Actividad única con lista y detalle

            // Reemplazar el diseño del marco con el fragmento de detalle correcto
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            mDetalleRecordatorioFragmento = DetalleRecordatorioFragmento.detalleRecordatorio(recordatorios);
            ft.replace(R.id.fl_contenedor_lateral, mDetalleRecordatorioFragmento, TAG_FRAGMENTO_DETALLE);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

            //Muestro el botón FAB agregar
            if(esFabAbrir){
                animateFAB(fab_abrir, true);
            }
            esFabAbrir = false;

        } else { // Actividades separadas

            // Carga la actividad Detalle
            Intent i = new Intent(this, DetalleRecordatorioActivity.class);
            i.putExtra(DetalleRecordatorioFragmento.KEY_RECORDATORIO, recordatorios);
            startActivityForResult(i, CODIGO_RESPUESTA_ELIMINAR_RECORDATORIO);

        }
    }

    @Override
    public void removerItem() {
        if(mListaRecordatoriosFragmento != null){
            mListaRecordatoriosFragmento.removerItemRecordatorio();
        }
    }

    @Override
    public void actualizarItem(Recordatorios recordatorios) {
        mActualizarRecordatorioFragmento = ActualizarRecordatorioFragmento.actualizarRecordatorio(recordatorios);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_contenedor_lateral, mActualizarRecordatorioFragmento, TAG_FRAGMENTO_ACTUALIZAR);
        ft.addToBackStack(null);
        ft.commit();
    }

}