package com.a01luisrene.multirecordatorio.ui;

import android.content.res.Configuration;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.fragments.DetalleRecordatorioFragment;
import com.a01luisrene.multirecordatorio.fragments.ListaRecordatorioFragment;

public class DetalleRecordatorioActivity extends AppCompatActivity implements View.OnClickListener {
    private CollapsingToolbarLayout mCollapsingToolbarLayout = null;
    private FloatingActionButton mFabEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_recordatorio);
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
/*
        if (savedInstanceState == null) {
            //Durante la configuración inicial, conecte el fragmento de detalles.
           // DetalleRecordatorioFragment details = new DetalleRecordatorioFragment();
           // details.setArguments(getIntent().getExtras());
            //getFragmentManager().beginTransaction().add(R.id.container_detail_reminder, details).commit();
        }
*/
        DetalleRecordatorioFragment details = new DetalleRecordatorioFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_detail, details)
                .commit();

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapser);
        mCollapsingToolbarLayout.setTitle("Cumpleaños");

        mFabEditar = (FloatingActionButton) findViewById(R.id.fab_editar);
        mFabEditar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_eliminar:
                Toast.makeText(this, "Eliminar", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                Toast.makeText(this, "Atras", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_editar:
                Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show();
        }
    }
}
