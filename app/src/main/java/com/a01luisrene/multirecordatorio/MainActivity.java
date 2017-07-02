package com.a01luisrene.multirecordatorio;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener{

    private DataBaseManagerRecordatorios manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cargar fragment en el contenedor principal
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ListaRecordatorioFragment listaRecordatorio = new ListaRecordatorioFragment();
        fragmentTransaction.add(R.id.container_main, listaRecordatorio);
        fragmentTransaction.commit();

        /*Creación de la base de datos SQLite  */
        manager = new DataBaseManagerRecordatorios(this);

        manager.insertarTipoRecordatorio(null, "cumpleanios.png", "Cumpleaño", "1", "28-066-2017");
        manager.insertarTipoRecordatorio(null, "cita.png", "Cita", "1", "28-066-2017");

        manager.insertarRecoratorio(null, "Cumple de luis", "Luis rene","1", "Feliz cumpleaños", "1145454","1", "1", "1", "2017", "2017","1");
        manager.insertarRecoratorio(null, "Cumple de luis", "Luis rene","1", "Feliz cumpleaños", "1145454","1", "1", "1", "2017", "2017","1");
        manager.insertarRecoratorio(null, "Cumple de luis", "Luis rene","1", "Feliz cumpleaños", "1145454","1", "1", "1", "2017", "2017","1");

        manager.cerrar();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        //Listener para el EditText
        searchView.setOnQueryTextListener(this);

        //Listener para el cierre y apertura de widget
        MenuItem menuItem = MenuItemCompat.setOnActionExpandListener(searchItem, this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_add:
                Toast.makeText(this, "Agregar", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Eventos del buscador

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        Toast.makeText(this, "Buscador activado", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        Toast.makeText(this, "Buscador desactivado", Toast.LENGTH_SHORT).show();
        return true;
    }
    //Se ejecuta cuando presionamos enter
    @Override
    public boolean onQueryTextSubmit(String s) {
        Toast.makeText(this, "Buscando en la DB", Toast.LENGTH_SHORT).show();
        return false;
    }
    //Se ejecuta mientras escribimos en el campo search
    @Override
    public boolean onQueryTextChange(String s) {
        Toast.makeText(this, "Busscando...", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Toast.makeText(this,"Crear un nuevo recordatorio", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // @Override
    //public void onClick(View view) {
        //if(view.getId() == R.id.imageButton){

            //new buscarTask().execute();

        //}
   // }

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