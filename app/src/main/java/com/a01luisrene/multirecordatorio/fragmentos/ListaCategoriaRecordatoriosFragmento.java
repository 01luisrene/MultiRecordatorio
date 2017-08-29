package com.a01luisrene.multirecordatorio.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.adaptadores.ListaCategoriaRecordatoriosAdaptador;
import com.a01luisrene.multirecordatorio.modelos.CategoriaRecordatorios;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;

import java.util.List;


public class ListaCategoriaRecordatoriosFragmento extends Fragment{

    RecyclerView mTipoRecordatorioListRecyclerView;
    ListaCategoriaRecordatoriosAdaptador mListaCategoriaRecordatoriosAdaptador;
    List<CategoriaRecordatorios> mListaItemsCategoriaRecordatorios;
    DataBaseManagerRecordatorios mManager;

    public ListaCategoriaRecordatoriosFragmento() {
        // Required empty public constructor
    }

    public static ListaCategoriaRecordatoriosFragmento newInstance(String param1, String param2) {
        ListaCategoriaRecordatoriosFragmento fragment = new ListaCategoriaRecordatoriosFragmento();
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

        mListaItemsCategoriaRecordatorios = mManager.getListaCategoriaRecordatorios();

        mListaCategoriaRecordatoriosAdaptador =  new ListaCategoriaRecordatoriosAdaptador(mListaItemsCategoriaRecordatorios, getActivity());

        mTipoRecordatorioListRecyclerView.setAdapter(mListaCategoriaRecordatoriosAdaptador);

        mTipoRecordatorioListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return v;
    }
}
