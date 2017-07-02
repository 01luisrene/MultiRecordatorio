package com.a01luisrene.multirecordatorio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


public class DetalleRecordatorioFragment extends Fragment {

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

        //Toolbar
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_detail);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(null);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

}
