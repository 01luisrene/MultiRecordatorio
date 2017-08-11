package com.a01luisrene.multirecordatorio.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.squareup.picasso.Picasso;

import java.io.File;

public class DetalleRecordatorioFragmento extends Fragment {

    //Llave para usar con parcelable
    public static final String ID_RECORDATORIO = "objeto.recordatorio";

    private Recordatorios mItemRecordatorio;
    private CollapsingToolbarLayout mCtRecordatorio;
    private ImageView mIvImagenRecordatorio;
    private CheckBox mCbFacebook, mCbTwitter, mCbMensaje;
    private String mValorFacebook, mValorTwitter, mValorMensaje, mValorImagen;

    Activity activity;

    public DetalleRecordatorioFragmento() {
        // Required empty public constructor
    }

    public static DetalleRecordatorioFragmento newInstance(Recordatorios recordatorios) {
        DetalleRecordatorioFragmento fragmentDetalle = new DetalleRecordatorioFragmento();
        Bundle args = new Bundle();
        args.putParcelable(ID_RECORDATORIO, recordatorios);
        fragmentDetalle.setArguments(args);

        return fragmentDetalle;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ID_RECORDATORIO)) {

            mItemRecordatorio = getArguments().getParcelable(ID_RECORDATORIO);

            activity = this.getActivity();

            mCtRecordatorio = (CollapsingToolbarLayout) activity.findViewById(R.id.ct_recordatorio);
            mIvImagenRecordatorio = (ImageView) activity.findViewById(R.id.iv_cover);

            mValorImagen = mItemRecordatorio.getImagenRecordatorio();

            if (mCtRecordatorio != null && mIvImagenRecordatorio != null) {
                mCtRecordatorio.setTitle(mItemRecordatorio.getCategoriaRecordatorio());

                //Condiciona para cargar el ImageView [solo si devuelve un valor no nulo ni vacío]
                if(mValorImagen != null){

                    Picasso.with(getContext())
                            .load(new File(mValorImagen))
                            .into(mIvImagenRecordatorio);

                }else{

                    Picasso.with(getContext())
                            .load(R.drawable.ic_image_150dp)
                            .error(R.drawable.ic_image_150dp).into(mIvImagenRecordatorio);
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detalle_recordatorio, container, false);
        if(mItemRecordatorio != null) {
            ((TextView) v.findViewById(R.id.recordatorio_id)).setText(mItemRecordatorio.getId());
            ((TextView) v.findViewById(R.id.tv_titulo)).setText(mItemRecordatorio.getTitulo());
            ((TextView) v.findViewById(R.id.tv_entidad_otros)).setText(mItemRecordatorio.getEntidadOtros());
            ((TextView) v.findViewById(R.id.tv_telefono)).setText(mItemRecordatorio.getTelefono());
            ((TextView) v.findViewById(R.id.tv_mensaje)).setText(mItemRecordatorio.getContenidoMensaje());

            mCbFacebook = (CheckBox) v.findViewById(R.id.cb_facebook);
            mCbTwitter = (CheckBox) v.findViewById(R.id.cb_twtter);
            mCbMensaje = (CheckBox) v.findViewById(R.id.cb_mensaje);

            //Variables a verificar
            mValorFacebook = mItemRecordatorio.getPublicarFacebook();
            mValorTwitter = mItemRecordatorio.getPublicarTwitter();
            mValorMensaje = mItemRecordatorio.getEnvioMensaje();

            //Condición para el checkbox facebook
            if (mValorFacebook.equals("1"))
                mCbFacebook.setChecked(true);
            else
                mCbFacebook.setChecked(false);

            //Condición para el checkbox twitter
            if (mValorTwitter.equals("1"))
                mCbTwitter.setChecked(true);
            else
                mCbTwitter.setChecked(false);

            //Condición para el checkbox mensaje
            if (mValorMensaje.equals("1"))
                mCbMensaje.setChecked(true);
            else
                mCbMensaje.setChecked(false);

            //TODO: crear una función para dividir la fecha de publicaicón
            ((TextView) v.findViewById(R.id.tv_fecha_recordatorio)).setText(mItemRecordatorio.getFechaPublicacionRecordatorio());

        }


        return v;
    }

    public interface OnItemSelectedListener {
    }
}
