package com.a01luisrene.multirecordatorio.ui;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.fragments.DetalleRecordatorioFragment;

public class DetalleRecordatorioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_recordatorio);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            //Durante la configuración inicial, conecte el fragmento de detalles.
            DetalleRecordatorioFragment details = new DetalleRecordatorioFragment();
            details.setArguments(getIntent().getExtras());
            //getFragmentManager().beginTransaction().add(R.id.container_detail_reminder, details).commit();
        }
    }
}
