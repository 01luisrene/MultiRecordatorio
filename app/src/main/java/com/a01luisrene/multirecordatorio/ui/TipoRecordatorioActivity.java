package com.a01luisrene.multirecordatorio.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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

import com.a01luisrene.multirecordatorio.MainActivity;
import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.fragments.ListaTipoRecordatorioFragment;


public class TipoRecordatorioActivity extends AppCompatActivity
        implements View.OnClickListener,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener,
        NavigationView.OnNavigationItemSelectedListener {
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_recordatorio);

        ListaTipoRecordatorioFragment fragment = new ListaTipoRecordatorioFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_contenedor_tipo_recordatorio, fragment)
                .commit();
        //Código para reemplazar un fragment
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main_type_reminder, AgregarTipoRecordatorioFragment.crear())
                .commit();
        */

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Asigno un titulo personalizado al toolbar
        getSupportActionBar().setTitle(getString(R.string.lista_tipo_recordatorios));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_lista_recordatorios) {

            FrameLayout listaRecordatorios = (FrameLayout) findViewById(R.id.fl_contenedor_principal);
            if(listaRecordatorios == null){
                mIntent = new Intent(this, MainActivity.class);
                startActivity(mIntent);
            }

        }else if (id == R.id.nav_Lista_tipo_recordatorios) {
            //mostrarFragment(new ListaTipoRecordatorioFragment(), R.id.container_main);
            FrameLayout listaRecordatorios = (FrameLayout) findViewById(R.id.fl_contenedor_tipo_recordatorio);
            if(listaRecordatorios == null) {
                mIntent = new Intent(this, TipoRecordatorioActivity.class);
                startActivity(mIntent);
            }
        }else if(id == R.id.nav_salir){
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }
}
