package com.a01luisrene.multirecordatorio.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;

public class ActualizarRecordatorioFragmento extends Fragment {

    Recordatorios mItemRecordatorio;

    EditText mEtTituloRecordatorio;

    public ActualizarRecordatorioFragmento() {
        // Required empty public constructor
    }


    public static ActualizarRecordatorioFragmento newInstance(Recordatorios recordatorios) {
        ActualizarRecordatorioFragmento fragmentoActualizar = new ActualizarRecordatorioFragmento();

        Bundle args = new Bundle();
        args.putParcelable(DetalleRecordatorioFragmento.ID_RECORDATORIO, recordatorios);
        fragmentoActualizar.setArguments(args);

        return fragmentoActualizar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(DetalleRecordatorioFragmento.ID_RECORDATORIO)) {
            mItemRecordatorio = getArguments().getParcelable(DetalleRecordatorioFragmento.ID_RECORDATORIO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_actualizar_recordatorio, container, false);

        mEtTituloRecordatorio = (EditText) v.findViewById(R.id.et_actualizar_titulo);

        mEtTituloRecordatorio.setText(mItemRecordatorio.getTitulo());

        return v;
    }
}
