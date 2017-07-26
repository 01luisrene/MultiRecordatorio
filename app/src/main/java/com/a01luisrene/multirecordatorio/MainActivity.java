package com.a01luisrene.multirecordatorio;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

import com.a01luisrene.multirecordatorio.fragments.AgregarRecordatorioFragment;
import com.a01luisrene.multirecordatorio.fragments.DetalleRecordatorioFragment;
import com.a01luisrene.multirecordatorio.fragments.ListaRecordatorioFragment;

import com.a01luisrene.multirecordatorio.modelos.Recordatorio;
import com.a01luisrene.multirecordatorio.ui.CategoriaRecordatorioActivity;
import com.a01luisrene.multirecordatorio.ui.DetalleRecordatorioActivity;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener,
        NavigationView.OnNavigationItemSelectedListener,
        ListaRecordatorioFragment.OnItemSelectedListener{

    private Toolbar mToolbar;
    public FloatingActionButton mFabAgregarRecordatorio;
    private boolean dosPaneles;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    Recordatorio recordatorio = new Recordatorio();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        //Condiciono para cargar el fragment solo si el bundle esta vacío
        if(savedInstanceState == null){
            //Cargar fragment (lista recordatorios) en el contenedor principal
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fl_contenedor_principal, ListaRecordatorioFragment.crear(), "LISTA_RECORDATORIOS");
            fragmentTransaction.commit();
        }

        // Verificación: ¿Existe el detalle en el layout?
        if (findViewById(R.id.fl_contenedor_lateral) != null) {
            // Si es asi, entonces confirmar modo Master-Detail
            dosPaneles = true;

            cargarFragmentoDetalle(recordatorio.getId(0));
        }



        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.nd_open, R.string.nd_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Botón flotante
        mFabAgregarRecordatorio = (FloatingActionButton) findViewById(R.id.fab_agregar_recordatorio);
        mFabAgregarRecordatorio.setOnClickListener(this);
    }

    private void cargarFragmentoDetalle(String id) {
        Bundle arguments = new Bundle();
        arguments.putString(DetalleRecordatorioFragment.ID_RECORDATORIO, id);
        DetalleRecordatorioFragment fragment = new DetalleRecordatorioFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_contenedor_lateral, fragment)
                .commit();
    }


    @Override
    public void alSeleccionarItem(String idArticulo) {
        if (dosPaneles) {
            cargarFragmentoDetalle(idArticulo);
        } else {
            Intent intent = new Intent(this, DetalleRecordatorioActivity.class);
            intent.putExtra(DetalleRecordatorioFragment.ID_RECORDATORIO, idArticulo);

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        //Para los fragments
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        boolean fragmentTrasaccion = false;
        Fragment fragment = null;
        String fragTag = null;
        Intent intent;

        switch (item.getItemId()) {
            case R.id.nav_lista_recordatorios:

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_contenedor_principal, ListaRecordatorioFragment.crear(), "LISTA_RECORDATORIOS");
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.addToBackStack("BACKSTACK_LISTA_RECORDATORIOS");
                fragmentTransaction.commit();

                getSupportActionBar().setTitle(getString(R.string.l_recordatorios));

                mFabAgregarRecordatorio.show();
                break;

            case R.id.nav_Lista_recordatorios_archivados:

                break;

            case R.id.nav_Lista_categoria_recordatorios:
                String listaCategoria = "lista_categoria";
                intent = new Intent(MainActivity.this, CategoriaRecordatorioActivity.class);
                //intent.putExtra(ABRIR_FRAGMENT, nuevoRecordatorio);
                startActivity(intent);
                break;

            case R.id.nav_configuraciones:

                break;

            case R.id.nav_salir:
                finish();
                break;
        }

        if (fragmentTrasaccion) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragmentByTag = fragmentManager.findFragmentByTag(fragTag);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            fragmentManager.beginTransaction();

            if(fragmentByTag == null) {
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fl_contenedor_principal, fragment, fragTag);
                transaction.addToBackStack(fragTag.getClass().getName());
                transaction.commit();
            }else{
                transaction.remove(fragmentByTag);
            }

            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {

        boolean fragmentTrasaccion = false;
        Fragment fragment = null;
        String fragTag = null;
        int toolbartitulo = 0;

        switch (v.getId()){
            case R.id.fab_agregar_recordatorio:

                AgregarRecordatorioFragment fragAgregarRecordatorios = new AgregarRecordatorioFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_contenedor_principal, fragAgregarRecordatorios, "NUEVO_RECORDATORIO");
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.addToBackStack("BACKSTACK_NUEVO_RECORDATORIO");
                fragmentTransaction.commit();

                getSupportActionBar().setTitle(getString(R.string.nd_nuevo_recordatorio));

                mFabAgregarRecordatorio.hide();

                break;
        }

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