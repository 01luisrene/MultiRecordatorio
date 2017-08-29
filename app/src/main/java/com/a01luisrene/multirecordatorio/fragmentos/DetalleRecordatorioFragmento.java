package com.a01luisrene.multirecordatorio.fragmentos;

import android.app.Activity;
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
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetalleRecordatorioFragmento extends Fragment {

    //Llave para usar con parcelable
    public static final String ID_RECORDATORIO = "objeto.recordatorio";
    public static final String VALOR_ACTIVO = "1";

    Recordatorios mItemRecordatorio;
    CollapsingToolbarLayout mCtRecordatorio;
    ImageView mIvImagenRecordatorio;
    CircleImageView mCivImagenCategoria;
    CheckBox mCbFacebook, mCbTwitter, mCbMensaje;
    String mValorFacebook, mValorTwitter, mValorMensaje, mValorImagen;

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

                //Condiciona para cargar el ImageView [solo si devuelve un valor diferente a nulo]
                if(mValorImagen != null){

                    Picasso.with(getContext())
                            .load(new File(mValorImagen))
                            .into(mIvImagenRecordatorio);

                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detalle_recordatorio, container, false);

        if(mItemRecordatorio != null) {
            ((TextView) v.findViewById(R.id.tv_titulo)).setText(mItemRecordatorio.getTitulo());
            ((TextView) v.findViewById(R.id.tv_entidad_otros)).setText(mItemRecordatorio.getEntidadOtros());
            ((TextView) v.findViewById(R.id.tv_telefono)).setText(mItemRecordatorio.getTelefono());
            ((TextView) v.findViewById(R.id.tv_mensaje)).setText(mItemRecordatorio.getContenidoMensaje());

            mCbFacebook = (CheckBox) v.findViewById(R.id.cb_facebook);
            mCbTwitter = (CheckBox) v.findViewById(R.id.cb_twtter);
            mCbMensaje = (CheckBox) v.findViewById(R.id.cb_mensaje);

            if(!Utilidades.smartphone){ //Compruebo si es tablet
                mCivImagenCategoria = (CircleImageView) v.findViewById(R.id.civ_detalle_categoria_recordatorio);
                if(mValorImagen != null){
                    //Utilizo la librería Picasso
                    Picasso.with(getContext())
                            .load(new File(mValorImagen))
                            .into(mCivImagenCategoria);

                }else{
                    mCivImagenCategoria.setImageResource(R.drawable.ic_image_150dp);
                }

                ((TextView) v.findViewById(R.id.tv_detalle_titulo_categoria_recordatorio))
                        .setText(mItemRecordatorio.getCategoriaRecordatorio());


            }

            //Variables a verificar
            mValorFacebook = mItemRecordatorio.getPublicarFacebook();
            mValorTwitter = mItemRecordatorio.getPublicarTwitter();
            mValorMensaje = mItemRecordatorio.getEnvioMensaje();

            //Condición para el checkbox facebook
            if (mValorFacebook.equals(VALOR_ACTIVO)){
                mCbFacebook.setChecked(true);
                mCbFacebook.setClickable(false);
            } else {
                mCbFacebook.setChecked(false);
                mCbFacebook.setEnabled(false);
            }

            //Condición para el checkbox twitter
            if (mValorTwitter.equals(VALOR_ACTIVO)) {
                mCbTwitter.setChecked(true);
                mCbTwitter.setClickable(false);
            }else {
                mCbTwitter.setChecked(false);
                mCbTwitter.setEnabled(false);
            }

            //Condición para el checkbox mensaje
            if (mValorMensaje.equals(VALOR_ACTIVO)) {
                mCbMensaje.setChecked(true);
                mCbMensaje.setClickable(false);
            }else {
                mCbMensaje.setChecked(false);
                mCbMensaje.setEnabled(false);
            }

            ((TextView) v.findViewById(R.id.tv_fecha_recordatorio)).setText(mItemRecordatorio.getFechaPublicacionRecordatorio());
            ((TextView) v.findViewById(R.id.tv_hora_recordatorio)).setText(mItemRecordatorio.getHoraPublicacionRecordatorio());

        }


        return v;
    }

    public interface OnItemSelectedListener {
    }
}
