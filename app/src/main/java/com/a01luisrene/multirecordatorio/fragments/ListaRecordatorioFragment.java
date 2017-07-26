package com.a01luisrene.multirecordatorio.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a01luisrene.multirecordatorio.MainActivity;
import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.adaptadores.RecordatorioListAdapter;
import com.a01luisrene.multirecordatorio.modelos.Recordatorio;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;
import com.a01luisrene.multirecordatorio.ui.DetalleRecordatorioActivity;

import java.util.List;

public class ListaRecordatorioFragment
        extends Fragment
        implements RecordatorioListAdapter.OnItemClickListener {

    private OnItemSelectedListener escucha;
    private RecyclerView mRecordatorioListRecyclerView;
    private List<Recordatorio> mListaItemsRecordatorio;
    private DataBaseManagerRecordatorios mManager;
    private RecordatorioListAdapter mRecordatorioListAdapter;
    private TextView mListaVacia;


    public ListaRecordatorioFragment() {
        // Required empty public constructor
    }

    public static ListaRecordatorioFragment crear() {
        return new ListaRecordatorioFragment();
    }

    public static ListaRecordatorioFragment newInstance() {
        ListaRecordatorioFragment fragment = new ListaRecordatorioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lista_recordatorio, container, false);

        //Almaceno MainActivity en un variable para reutilizarlo
        MainActivity activity = (MainActivity) getActivity();

        //Muestro el titulo de lista en el toolbar
        String stringTitulo = getString(R.string.l_recordatorios);
        String tituloListaRecordatorios  =  stringTitulo.substring(0, 1).toUpperCase() + stringTitulo.substring(1);
        activity.getSupportActionBar().setTitle(tituloListaRecordatorios);

        //Muestro el botón flotante
        activity.mFabAgregarRecordatorio.show();

        mListaVacia = (TextView) v.findViewById(R.id.lista_vacia);
        mRecordatorioListRecyclerView = (RecyclerView) v.findViewById(R.id.rv_lista_recordatorio);

        mRecordatorioListRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mRecordatorioListRecyclerView.setLayoutManager(linearLayoutManager);

        mManager = new DataBaseManagerRecordatorios(getActivity());

        mListaItemsRecordatorio = mManager.getRecordatoriosList();

        mRecordatorioListAdapter = new RecordatorioListAdapter(mListaItemsRecordatorio, getActivity());

        mRecordatorioListRecyclerView.setAdapter(mRecordatorioListAdapter);

        mRecordatorioListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (mListaItemsRecordatorio.isEmpty()) {
            Resources res = getResources();
            String text = String.format(res.getString(R.string.lista_vacia), getString(R.string.l_recordatorios));
            mListaVacia.setText(text);
            mListaVacia.setVisibility(View.VISIBLE);
            mRecordatorioListRecyclerView.setVisibility(View.GONE);
        } else {
            mListaVacia.setVisibility(View.GONE);
            mRecordatorioListRecyclerView.setVisibility(View.VISIBLE);
        }

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            escucha = (OnItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " debes implementar EscuchaFragmento");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        escucha = null;
    }

    public void cargarDetalle(String idArticulo) {
        if (escucha != null) {
            escucha.alSeleccionarItem(idArticulo);
        }
    }

    @Override
    public void onClick(RecordatorioListAdapter.ViewHolder viewHolder, String idArticulo) {
        cargarDetalle(idArticulo);
    }

    public interface OnItemSelectedListener {
        void alSeleccionarItem(String idArticulo);
    }
}
