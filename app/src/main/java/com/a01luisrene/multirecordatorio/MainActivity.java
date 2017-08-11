package com.a01luisrene.multirecordatorio;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.fragments.DetalleRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.fragments.ListaRecordatoriosFragmento;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;
import com.a01luisrene.multirecordatorio.ui.CategoriaRecordatorioActivity;
import com.a01luisrene.multirecordatorio.ui.DetalleRecordatorioActivity;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener,
        ListaRecordatoriosFragmento.OnItemSelectedListener,
        DetalleRecordatorioFragmento.OnItemSelectedListener{

    private Toolbar mToolbar;
    private FloatingActionButton mFabAgregarRecordatorio;
    private boolean dosPaneles = false;
    ListaRecordatoriosFragmento listaRecordatoriosFragmento;
    DetalleRecordatorioFragmento mDetalleRecordatorioFragmento;

    private DataBaseManagerRecordatorios manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new DataBaseManagerRecordatorios(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        //Botón flotante
        mFabAgregarRecordatorio = (FloatingActionButton) findViewById(R.id.fab_agregar_recordatorio);
        mFabAgregarRecordatorio.setOnClickListener(this);

        //Función para determinar el tipo de layout
        determinePaneLayout();


        if (savedInstanceState == null){
            // Agregar fragmento de lista
            listaRecordatoriosFragmento = new ListaRecordatoriosFragmento();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_contenedor, listaRecordatoriosFragmento)
                    .commit();
        }

    }

    private void determinePaneLayout() {

        View fragmentItemDetail = findViewById(R.id.fl_detalle);

        if (fragmentItemDetail != null) {
            dosPaneles = true;
            Utilidades.smartphone = false;
            Toast.makeText(this, "Maestro-Detalle", Toast.LENGTH_SHORT).show();

            //TODO: cargar el fragment detalle al momento de cargar con
            //Crear un fragment cover

        }else{
            Utilidades.smartphone = true;

        }
    }

    @Override
    public void onItemSelected(Recordatorios recordatorios) {
        if (dosPaneles) { // Actividad única con lista y detalle

            // Reemplazar el diseño del marco con el fragmento de detalle correcto
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            DetalleRecordatorioFragmento fragmentItem = DetalleRecordatorioFragmento.newInstance(recordatorios);
            ft.replace(R.id.fl_detalle, fragmentItem);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();


        } else { // Actividades separadas

            // Carga la actividad Detalle
            Intent intent = new Intent(this, DetalleRecordatorioActivity.class);
            intent.putExtra(DetalleRecordatorioFragmento.ID_RECORDATORIO, recordatorios);
            startActivity(intent);
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

        /**
         *  boolean fragmentTrasaccion = false;
         Fragment fragment = null;
         String fragTag = null;
         int toolbartitulo = 0;
         */

        switch (v.getId()){
            case R.id.fab_agregar_recordatorio:
                Intent i = new Intent(this, CategoriaRecordatorioActivity.class);
                startActivity(i);
                break;
        }

        /*
        if(fragmentTrasaccion) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragmentByTag = fragmentManager.findFragmentByTag(fragTag);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            fragmentManager.beginTransaction();
            if (fragmentByTag == null){
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fl_contenedor_principal, fragment, fragTag);
                transaction.commit();
            }

            getSupportActionBar().setTitle(toolbartitulo);
        }
        */

    }



    private class buscarTask extends AsyncTask<Void, Void, Void>{
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

}