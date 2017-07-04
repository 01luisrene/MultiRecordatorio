package com.a01luisrene.multirecordatorio;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a01luisrene.multirecordatorio.adaptadores.RecordatorioListAdapter;
import com.a01luisrene.multirecordatorio.modelos.Recordatorio;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;

import java.util.List;

public class ListaRecordatorioFragment
        extends Fragment {

    public static final String LISTA_RECORDATORIO_FRAGMENT = "lista_recordatorio_fragment";

    private RecyclerView recordatorioListRecyclerView;
    private RecordatorioListAdapter recordatorioListAdapter;
    private List<Recordatorio> listaItemsRecordatorio;
    private DataBaseManagerRecordatorios manager;

    public ListaRecordatorioFragment() {
        // Required empty public constructor
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

        recordatorioListRecyclerView = (RecyclerView) v.findViewById(R.id.reminder_list_recyclerView);

        recordatorioListRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recordatorioListRecyclerView.setLayoutManager(linearLayoutManager);

        manager = new DataBaseManagerRecordatorios(getActivity());

        listaItemsRecordatorio = manager.getRecordatoriosList();

        recordatorioListAdapter =  new RecordatorioListAdapter(listaItemsRecordatorio, getActivity());

        recordatorioListRecyclerView.setAdapter(recordatorioListAdapter);

        recordatorioListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);

        fab.setOnClickListener((View.OnClickListener) getActivity());

        recordatorioListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 || dy < 0 && fab.isShown())
                    fab.hide();
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    fab.show();

                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        //Toolbar
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        //toolbar.setLogo(R.mipmap.ic_launcher);

        return v;
    }
}
