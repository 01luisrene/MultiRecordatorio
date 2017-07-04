package com.a01luisrene.multirecordatorio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DetalleRecordatorioFragment extends Fragment {
    public static final String DETALLE_RECORDATORIO_FRAGMENT = "lista_recordatorio_fragment";

    public DetalleRecordatorioFragment() {
        // Required empty public constructor
    }

    public static DetalleRecordatorioFragment newInstance() {
        DetalleRecordatorioFragment fragment = new DetalleRecordatorioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detalle_recordatorio, container, false);

        return v;
    }

}
