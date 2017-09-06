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
    public static final String KEY_RECORDATORIO = "objeto.recordatorio";
    public static final String VALOR_ACTIVO = "1";

    Recordatorios mItemRecordatorio;
    CollapsingToolbarLayout mCtRecordatorio;
    ImageView mIvImagenRecordatorio;
    CircleImageView mCivImagenCategoria;
    CheckBox mCbFacebook, mCbTwitter, mCbMensaje;
    String mValorFacebook, mValorTwitter, mValorMensaje, mValorRutaImagen, mValorRutaImagenRecuperado;
    TextView mTvTelefono, mTvEntidadOtros;

    Activity activity;

    public DetalleRecordatorioFragmento() {
        // Required empty public constructor
    }

    public static DetalleRecordatorioFragmento newInstance(Recordatorios recordatorios) {
        DetalleRecordatorioFragmento fragmentoDetalle = new DetalleRecordatorioFragmento();

        Bundle args = new Bundle();
        args.putParcelable(KEY_RECORDATORIO, recordatorios);
        fragmentoDetalle.setArguments(args);

        return fragmentoDetalle;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(KEY_RECORDATORIO)) {

            mItemRecordatorio = getArguments().getParcelable(KEY_RECORDATORIO);

            activity = this.getActivity();

            mCtRecordatorio = (CollapsingToolbarLayout) activity.findViewById(R.id.ct_categoria_recordatorio);
            mIvImagenRecordatorio = (ImageView) activity.findViewById(R.id.iv_cover);
            mValorRutaImagen = mItemRecordatorio.getRutaImagenRecordatorio();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detalle_recordatorio, container, false);

        if(mItemRecordatorio != null) {

            if (mCtRecordatorio != null && mIvImagenRecordatorio != null) {
                mCtRecordatorio.setTitle(mItemRecordatorio.getCategoriaRecordatorio());

                //Condiciona para cargar el ImageView [solo si devuelve un valor diferente a nulo]
                if(mValorRutaImagen != null){

                    Picasso.with(getContext())
                            .load(new File(mValorRutaImagen))
                            .into(mIvImagenRecordatorio);

                }
            }

            //TextView
            mTvEntidadOtros = (TextView) v.findViewById(R.id.tv_entidad_otros);
            mTvTelefono = (TextView) v.findViewById(R.id.tv_telefono);
            //CheckBox
            mCbFacebook = (CheckBox) v.findViewById(R.id.cb_facebook);
            mCbTwitter = (CheckBox) v.findViewById(R.id.cb_twtter);
            mCbMensaje = (CheckBox) v.findViewById(R.id.cb_mensaje);

            ((TextView) v.findViewById(R.id.tv_titulo)).setText(mItemRecordatorio.getTitulo());
            ((TextView) v.findViewById(R.id.tv_entidad_otros)).setText(mItemRecordatorio.getEntidadOtros());
            ((TextView) v.findViewById(R.id.tv_telefono)).setText(mItemRecordatorio.getTelefono());
            ((TextView) v.findViewById(R.id.tv_mensaje)).setText(mItemRecordatorio.getContenidoMensaje());

            //Almaceno el valor de del campo Entidad Otros
            String entidadOtrosValor = mItemRecordatorio.getEntidadOtros();

            if(entidadOtrosValor.isEmpty()){
                mTvEntidadOtros.setWidth(0);
                mTvEntidadOtros.setHeight(0);
            }


            if(!Utilidades.smartphone){ //Compruebo si es tablet
                mCivImagenCategoria = (CircleImageView) v.findViewById(R.id.civ_detalle_categoria_recordatorio);
                if(mValorRutaImagen != null){
                    //Utilizo la librería Picasso
                    Picasso.with(getContext())
                            .load(new File(mValorRutaImagen))
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
                mTvTelefono.setVisibility(View.INVISIBLE);
            }

            ((TextView) v.findViewById(R.id.tv_fecha_recordatorio)).setText(mItemRecordatorio.getFechaPublicacionRecordatorio());
            ((TextView) v.findViewById(R.id.tv_hora_recordatorio)).setText(mItemRecordatorio.getHoraPublicacionRecordatorio());

        }

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.getString("ruta.img", mValorRutaImagen);
    }
}
