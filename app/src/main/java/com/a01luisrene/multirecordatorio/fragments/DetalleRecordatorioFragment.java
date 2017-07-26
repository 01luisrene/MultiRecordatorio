package com.a01luisrene.multirecordatorio.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.modelos.Recordatorio;
import com.a01luisrene.multirecordatorio.modelos.TipoRecordatorio;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;

import java.util.ArrayList;
import java.util.List;


public class DetalleRecordatorioFragment extends Fragment {
    // EXTRA
    public static final String ID_RECORDATORIO = "extra.idArticulo";

    Recordatorio mListaItemsTipoRecordatorio = new Recordatorio();
    DataBaseManagerRecordatorios mManager;
    TextView titulo;

    public DetalleRecordatorioFragment() {
        // Required empty public constructor
    }

    public static DetalleRecordatorioFragment newInstance(int index) {
        DetalleRecordatorioFragment fragment = new DetalleRecordatorioFragment();
        Bundle args = new Bundle();
        args.putInt(ID_RECORDATORIO, index);
        fragment.setArguments(args);
        return fragment;
    }

    public int getShownIndex() {
        return getArguments().getInt(ID_RECORDATORIO, 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ID_RECORDATORIO)) {
            mListaItemsTipoRecordatorio = mManager.getRecordatoriosList().get(getShownIndex());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View v = inflater.inflate(R.layout.fragment_detalle_recordatorio, container, false);

        titulo = (TextView) v.findViewById(R.id.tv_titulo);

        titulo.setText(mListaItemsTipoRecordatorio.getTitulo());

        return v;
    }


}
