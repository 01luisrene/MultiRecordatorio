package com.a01luisrene.multirecordatorio.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.adaptadores.RecordatorioListAdapter;
import com.a01luisrene.multirecordatorio.modelos.Recordatorio;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;
import com.a01luisrene.multirecordatorio.ui.DetalleRecordatorioActivity;

import java.util.List;

public class ListaRecordatorioFragment extends Fragment {

    private RecyclerView recordatorioListRecyclerView;
    private RecordatorioListAdapter recordatorioListAdapter;
    private List<Recordatorio> listaItemsRecordatorio;
    private DataBaseManagerRecordatorios manager;

    boolean mDualPane;
    int mCurCheckPosition = 0;

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View detailsReminderFrame = getActivity().findViewById(R.id.container_lateral);

        mDualPane = detailsReminderFrame != null && detailsReminderFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restaurar el último estado para la posición seleccionada.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            // In dual-pane mode, La vista de lista resalta el elemento seleccionado.
            showDetails(mCurCheckPosition);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }
    //TODO: ver esta linea de código
    //@Override
    public void onListItemClick(List<Recordatorio> l, View v, int position, long id) {
        showDetails(position);
    }

    void showDetails(int index) {
        mCurCheckPosition = index;

        if (mDualPane) {
            //TODO: verificar esta liena de código
            //getActivity().setItemChecked(index, true);

            // Compruebe qué fragmento se muestra actualmente, reemplácelo si es necesario.
            DetalleRecordatorioFragment details = (DetalleRecordatorioFragment)
                    getFragmentManager().findFragmentById(R.id.container_lateral);
            if (details == null || details.getShownIndex() != index) {
                // Make new fragment to show this selection.
                details = DetalleRecordatorioFragment.newInstance(index);

                // Ejecutar una transacción, reemplazando cualquier fragmento existente
                // con éste dentro del marco.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (index == 0) {
                    ft.replace(R.id.container_lateral, details);
                } else {
                    //TODO: chekear
                    Toast.makeText(getActivity(), "Ver por que fragment reemplazar", Toast.LENGTH_SHORT).show();
                }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            // De lo contrario, debemos lanzar una nueva actividad para mostrar
            // el fragmento de diálogo con el texto seleccionado.
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetalleRecordatorioActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }
}
