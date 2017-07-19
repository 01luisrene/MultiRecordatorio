package com.a01luisrene.multirecordatorio.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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


public class ListaTipoRecordatorioFragment extends Fragment{
    private RecyclerView mTipoRecordatorioListRecyclerView;
    private TipoRecordatorioListAdapter mTipoRecordatorioListAdapter;
    private List<TipoRecordatorio> mListaItemsTipoRecordatorio;
    private DataBaseManagerRecordatorios mManager;

    private FragmentActivity mContext;


    public ListaTipoRecordatorioFragment() {
        // Required empty public constructor
    }

    public static ListaTipoRecordatorioFragment newInstance(String param1, String param2) {
        ListaTipoRecordatorioFragment fragment = new ListaTipoRecordatorioFragment();
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
        View v = inflater.inflate(R.layout.fragment_lista_tipo_recordatorio, container, false);

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
