package com.a01luisrene.multirecordatorio.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a01luisrene.multirecordatorio.R;


public class DetalleRecordatorioFragment extends Fragment {

    public DetalleRecordatorioFragment() {
        // Required empty public constructor
    }

    public static DetalleRecordatorioFragment newInstance(int index) {
        DetalleRecordatorioFragment fragment = new DetalleRecordatorioFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View v = inflater.inflate(R.layout.fragment_detalle_recordatorio, container, false);

        return v;
    }

}
