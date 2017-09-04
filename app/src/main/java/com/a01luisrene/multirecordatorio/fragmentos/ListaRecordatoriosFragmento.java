package com.a01luisrene.multirecordatorio.fragmentos;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a01luisrene.multirecordatorio.MainActivity;
import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.adaptadores.ListaRecordatoriosAdaptador;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;

import java.util.List;

public class ListaRecordatoriosFragmento extends Fragment{

    //Poblar lista recycclerView
    RecyclerView mRecordatoriosRecyclerView;
    List<Recordatorios> mListaItemsRecordatorios;
    DataBaseManagerRecordatorios mManagerRecordatorios;
    ListaRecordatoriosAdaptador mListaRecordatoriosAdaptador;

    //Variable que mostrara un mensaje cuando la lista este vacía
    TextView mListaVacia;

    //Variables para la comunicación entre fragment y Activity
    private OnItemSelectedListener listener;
    Activity activity;

    //Interface asociada a acitividad padre
    public interface OnItemSelectedListener {
        void onItemSelected(Recordatorios recordatorios);
    }

    public ListaRecordatoriosFragmento() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lista_recordatorio, container, false);
        //Almaceno RecordatoriosActivity en un variable para reutilizarlo
        MainActivity activity = (MainActivity) getActivity();

        //Muestro el titulo de lista en el toolbar
        String stringTitulo = getString(R.string.l_recordatorios);
        String tituloListaRecordatorios  =  stringTitulo.substring(0, 1).toUpperCase() + stringTitulo.substring(1);
        if(activity.getSupportActionBar() != null)
            activity.getSupportActionBar().setTitle(tituloListaRecordatorios);

        mListaVacia = (TextView) v.findViewById(R.id.lista_vacia);
        mRecordatoriosRecyclerView = (RecyclerView) v.findViewById(R.id.rv_lista_recordatorio);

        mRecordatoriosRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mRecordatoriosRecyclerView.setLayoutManager(linearLayoutManager);

        mManagerRecordatorios = new DataBaseManagerRecordatorios(getActivity().getApplicationContext());
        //Función que tiene la lógica para la carga de la lista
        listaRecordatorios();

        return v;
    }

    public void listaRecordatorios(){
        mListaItemsRecordatorios = mManagerRecordatorios.getListaRecordatorios();

        mListaRecordatoriosAdaptador = new ListaRecordatoriosAdaptador(mListaItemsRecordatorios,
                getActivity().getApplicationContext(), new OnItemSelectedListener() {
            @Override
            public void onItemSelected(Recordatorios recordatorios) {
                listener.onItemSelected(recordatorios);
            }
        });

        mRecordatoriosRecyclerView.setAdapter(mListaRecordatoriosAdaptador);

        mRecordatoriosRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Verifico si la lista sigue vacía
        if (mListaItemsRecordatorios.isEmpty()) {

            Resources res = getResources();
            String text = String.format(res.getString(R.string.lista_vacia), getString(R.string.l_recordatorios));

            //Formato personalizado del texto lista vacía
            Spannable spannable = Utilidades.setSpanCustomText(getActivity(),
                    text,
                    6,
                    20,
                    Color.BLACK,
                    1.2f,
                    Typeface.BOLD);

            mListaVacia.setText(spannable);
            mListaVacia.setVisibility(View.VISIBLE);
            mRecordatoriosRecyclerView.setVisibility(View.GONE);
        } else {
            mListaVacia.setVisibility(View.GONE);
            mRecordatoriosRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void actualizarListaRecordatorios(){
        //[Recargo la lista]
        //mListaRecordatoriosAdaptador.eliminarItemRecordatorio(1);
        Log.v("log", "Se recargo la lista");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //[Establecer comunicación entre nuestra lista y nuestro detalle]
        if (context instanceof Activity) {
            this.activity = (Activity) context;
            listener = (OnItemSelectedListener) this.activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement ItemsListFragment.OnItemSelectedListener");
        }

    }

    //Función llamada cuando el fragment es desasociada de una actividad
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}

