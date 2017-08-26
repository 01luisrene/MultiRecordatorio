package com.a01luisrene.multirecordatorio;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
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

import com.a01luisrene.multirecordatorio.fragmentos.AgregarRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.fragmentos.DetalleRecordatorioFragmento;
import com.a01luisrene.multirecordatorio.fragmentos.ListaRecordatoriosFragmento;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.ui.DetalleRecordatorioActivity;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener,
        ListaRecordatoriosFragmento.OnItemSelectedListener{

    Toolbar mToolbar;
    FloatingActionButton mFabAgregarRecordatorio;

    ListaRecordatoriosFragmento mListaRecordatoriosFragmento;
    DetalleRecordatorioFragmento mDetalleRecordatorioFragmento;
    public static final String CARGAR_NUEVO_RECORDATORIO_FRAGMENTO = "cargar_nuevo_recordatorio_fragmento";

    private boolean dosPaneles = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);

            //Botón flotante
            mFabAgregarRecordatorio = (FloatingActionButton) findViewById(R.id.fab_agregar_recordatorio);
            mFabAgregarRecordatorio.setOnClickListener(this);

            //Función para determinar el tipo de layout
            determinePaneLayout();


            if (savedInstanceState == null){
                // Agregar fragmento de lista
                mListaRecordatoriosFragmento = new ListaRecordatoriosFragmento();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_contenedor, mListaRecordatoriosFragmento)
                        .commit();
            }

        }
    private void determinePaneLayout() {

        View detalleItemFragmento = findViewById(R.id.fl_contenedor_lateral);

        if (detalleItemFragmento != null) {
            dosPaneles = true;
            Utilidades.smartphone = false;

            //TODO: cargar el fragment detalle al momento de cargar con
            //Crear un fragment cover

        }else{
            Utilidades.smartphone = true;
            //Mantener en modo portrait la pantalla
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onItemSelected(Recordatorios recordatorios) {
        if (dosPaneles) { // Actividad única con lista y detalle

            // Reemplazar el diseño del marco con el fragmento de detalle correcto
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            mDetalleRecordatorioFragmento = DetalleRecordatorioFragmento.newInstance(recordatorios);
            ft.replace(R.id.fl_contenedor_lateral, mDetalleRecordatorioFragmento);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

            //Habilito el botón agregar
            mFabAgregarRecordatorio.setEnabled(true);

        } else { // Actividades separadas

            // Carga la actividad Detalle
            Intent i = new Intent(this, DetalleRecordatorioActivity.class);
            i.putExtra(DetalleRecordatorioFragmento.ID_RECORDATORIO, recordatorios);
            startActivity(i);
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
                    i.putExtra(CARGAR_NUEVO_RECORDATORIO_FRAGMENTO, 1);
                    startActivity(i);

                }else{
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fl_contenedor_lateral, new AgregarRecordatorioFragmento())
                            .commit();

                    //Desabilito el botón agregar
                    mFabAgregarRecordatorio.setEnabled(false);
                }
                break;
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


}