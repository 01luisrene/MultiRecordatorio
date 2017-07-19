package com.a01luisrene.multirecordatorio;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.fragments.ListaRecordatorioFragment;
import com.a01luisrene.multirecordatorio.fragments.ListaTipoRecordatorioFragment;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;
import com.a01luisrene.multirecordatorio.ui.TipoRecordatorioActivity;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener, NavigationView.OnNavigationItemSelectedListener {

    private DataBaseManagerRecordatorios mManager;
    private Intent mIntent;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManager = new DataBaseManagerRecordatorios(this);

        mManager.insertarTipoRecordatorio(null, "", "Cumpleaños", 0, "2017");
        mManager.insertarRecoratorio(null, "hola", "Inpe", "1", "Holas mundo","9585222", "1", "1", "1", "2017", "2018", "1");
        mManager.cerrar();

        //Cargar fragment (lista recordatorios) en el contenedor principal
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_contenedor_principal, ListaRecordatorioFragment.crear())
                .commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Toast.makeText(this,"Crear un nuevo recordatorio", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //int id = item.getItemId();

        boolean fragmentTrasaccion = false;
        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.nav_lista_recordatorios:
                /*FrameLayout listaRecordatorios = (FrameLayout) findViewById(R.id.fl_contenedor_principal);
                if(listaRecordatorios == null){
                    mIntent = new Intent(this, MainActivity.class);
                    startActivity(mIntent);
                }*/
                fragment = new ListaRecordatorioFragment();
                fragmentTrasaccion = true;
                break;
            case R.id.nav_Lista_tipo_recordatorios:
                //mIntent = new Intent(this, TipoRecordatorioActivity.class);
                //startActivity(mIntent);
                fragment = new ListaTipoRecordatorioFragment();
                fragmentTrasaccion = true;
                break;
            case R.id.nav_salir:
                finish();
                break;
        }

            //ejemplo de uso
            //fragment = new ListaRecordatorioFragment();
            //fragmentTrasaccion = true;

        if(fragmentTrasaccion){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.fl_contenedor_principal, fragment)
                    .commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void mostrarFragment(final Fragment fragment, final int id){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(id, fragment)
                .commit();
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