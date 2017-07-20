package com.a01luisrene.multirecordatorio.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.adaptadores.TipoRecordatorioListAdapter;
import com.a01luisrene.multirecordatorio.modelos.TipoRecordatorio;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;

import java.util.List;


public class ListaCategoriaRecordatoriosFragment extends Fragment{

    public static final String FRAGMENT_CATEGORIA_RECORDATORIO = "fragment_lista_categoria_recordatorios";
    private RecyclerView mTipoRecordatorioListRecyclerView;
    private TipoRecordatorioListAdapter mTipoRecordatorioListAdapter;
    private List<TipoRecordatorio> mListaItemsTipoRecordatorio;
    private DataBaseManagerRecordatorios mManager;

    public ListaCategoriaRecordatoriosFragment() {
        // Required empty public constructor
    }

    public static ListaCategoriaRecordatoriosFragment newInstance(String param1, String param2) {
        ListaCategoriaRecordatoriosFragment fragment = new ListaCategoriaRecordatoriosFragment();
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
        View v = inflater.inflate(R.layout.fragment_lista_categoria_recordatorios, container, false);

        mTipoRecordatorioListRecyclerView = (RecyclerView) v.findViewById(R.id.rv_lista_tipo_recordatorio);

        mTipoRecordatorioListRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        mTipoRecordatorioListRecyclerView.setLayoutManager(linearLayoutManager);

        mManager = new DataBaseManagerRecordatorios(getActivity());

        mListaItemsTipoRecordatorio = mManager.getTipoRecordatoriosList();

        mTipoRecordatorioListAdapter =  new TipoRecordatorioListAdapter(mListaItemsTipoRecordatorio, getActivity());

        mTipoRecordatorioListRecyclerView.setAdapter(mTipoRecordatorioListAdapter);

        mTipoRecordatorioListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return v;
    }
}
