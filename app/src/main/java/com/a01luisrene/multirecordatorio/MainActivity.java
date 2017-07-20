package com.a01luisrene.multirecordatorio;

import android.support.v4.app.FragmentManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.fragments.ListaRecordatorioFragment;
import com.a01luisrene.multirecordatorio.fragments.ListaCategoriaRecordatoriosFragment;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;

public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar mToolbar;
    private DataBaseManagerRecordatorios manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new DataBaseManagerRecordatorios(this);

        manager.insertarTipoRecordatorio(null, "cumpleanios.png", "Cumpleaño", 1, "28-066-2017");
        manager.insertarTipoRecordatorio(null, "cita.png", "Cita", 1, "28-066-2017");

        manager.insertarRecoratorio(null, "Cumple de luis", "Luis rene","1", "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500", "1145454","1", "1", "1", "2017", "2017","1");
        manager.insertarRecoratorio(null, "Cumple de luis", "Luis rene","1", "Feliz cumpleaños", "1145454","1", "1", "1", "2017", "2017","1");
        manager.insertarRecoratorio(null, "Cumple de luis", "Luis rene","1", "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500", "1145454","1", "1", "1", "2017", "2017","1");

        manager.cerrar();

        //Cargar fragment (lista recordatorios) en el contenedor principal
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_contenedor_principal, new ListaRecordatorioFragment(), ListaRecordatorioFragment.FRAGMENT_LISTA_RECORDATORIO)
                .commit();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.nd_open, R.string.nd_close);
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
        boolean fragmentTrasaccion = false;
        Fragment fragment = null;
        String fragTag = null;

        switch (item.getItemId()){
            case R.id.nav_lista_recordatorios:
                fragment = new ListaRecordatorioFragment();
                fragTag = ListaRecordatorioFragment.FRAGMENT_LISTA_RECORDATORIO;
                fragmentTrasaccion = true;
                break;
            case R.id.nav_Lista_categoria_recordatorios:
                fragment = new ListaCategoriaRecordatoriosFragment();
                fragTag = ListaCategoriaRecordatoriosFragment.FRAGMENT_CATEGORIA_RECORDATORIO;
                fragmentTrasaccion = true;
                break;
            case R.id.nav_salir:
                finish();
                break;
        }

        if(fragmentTrasaccion){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragmentByTag = fragmentManager.findFragmentByTag(fragTag);
            if (fragmentByTag == null){
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fl_contenedor_principal, fragment, fragTag);
                transaction.commit();
            }

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        /*boolean fragmentTrasaccion = false;
        Fragment fragment = null;
        String titulo = null;

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment1 = fragmentManager.findFragmentByTag("fragment_agregar_recordatorios");
        Fragment fragment2 = fragmentManager.findFragmentByTag("fragment_agregar_categoria_recordatorios");
        Fragment mFragment = getSupportFragmentManager().findFragmentById(R.id.frag_lista_recordatorio);

        switch (v.getId()) {
            case R.id.fab_agregar:
                if(mFragment != null) {
                    fragment = new AgregarCategotiaRecordatorioFragment();
                    fragmentTrasaccion = true;
                }
                else if(!mFragment.getClass().getName().equalsIgnoreCase(new AgregarRecordatorioFragment().getClass().getName()))  {
                    fragment = new AgregarRecordatorioFragment();
                    fragmentTrasaccion = true;
                }

                if(fragment1 == null){
                    fragment = new AgregarRecordatorioFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_contenedor_principal, fragment,"fragment_agregar_recordatorios")
                            .commit();
                    fab_agregar.hide();
                }else if(fragment2 == null){
                    fragment = new AgregarCategotiaRecordatorioFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_contenedor_principal, fragment,"fragment_agregar_categoria_recordatorios")
                            .commit();
                    fab_agregar.hide();
                }


                //fragment = new AgregarRecordatorioFragment();
                //fragmentTrasaccion = true;
                //titulo = "Nuevo recordatorio";


                //fab_agregar.hide();
                break;

        }

        if(fragmentTrasaccion){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.fl_contenedor_principal, fragment, fragment.getClass().getName())
                    .addToBackStack(null)
                    .commit();

            getSupportActionBar().setTitle(titulo);
        }*/
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