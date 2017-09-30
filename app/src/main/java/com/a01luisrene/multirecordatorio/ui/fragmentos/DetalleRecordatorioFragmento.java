package com.a01luisrene.multirecordatorio.ui.fragmentos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.interfaces.InterfaceCrud;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.io.db.DataBaseManagerRecordatorios;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetalleRecordatorioFragmento extends Fragment
        implements View.OnClickListener {

    //Llave para usar con parcelable
    public static final String KEY_RECORDATORIO = "objeto.recordatorio";
    public static final String VALOR_ACTIVO = "1";
    public static final String RUTA_CARPETA_ASSETS = "file:///android_asset/";

    Recordatorios mItemRecordatorio;
    CollapsingToolbarLayout mCtRecordatorio;
    ImageView mIvImagenRecordatorio;
    CircleImageView mCivImagenCategoria;
    CheckBox mCbFacebook, mCbTwitter, mCbMensaje;
    String mValorFacebook, mValorTwitter, mValorMensaje, mValorRutaImagen;
    TextView mTvTelefono, mTvEntidadOtros;
    ImageButton mIbEliminarRecordatorio, mIbAcutualizarRecordatorio;
    int mValorProteccionImg;

    FloatingActionButton fabAddUpd;
    Activity activity;

    DataBaseManagerRecordatorios mManager;

    // Objeto de la interfaz remover, con este objeto llamaremos el
    // método de la interfaz
    private InterfaceCrud mCrud; //CRUD: crear, leer, actualizar, eliminar


    public DetalleRecordatorioFragmento() {
        // Required empty public constructor
    }

    public static DetalleRecordatorioFragmento detalleRecordatorio(Recordatorios recordatorios) {
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
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detalle_recordatorio, container, false);
        if(mItemRecordatorio != null) {
            mManager = new DataBaseManagerRecordatorios(getActivity().getApplicationContext());
            activity = this.getActivity();
            mValorRutaImagen = mItemRecordatorio.getRutaImagenRecordatorio();
            mValorProteccionImg = mItemRecordatorio.getProteccionImg();

            if (Utilidades.smartphone) {
                mCtRecordatorio = (CollapsingToolbarLayout) activity.findViewById(R.id.ct_categoria_recordatorio);
                mIvImagenRecordatorio = (ImageView) activity.findViewById(R.id.iv_cover);
                mCtRecordatorio.setTitle(mItemRecordatorio.getTitulocategoria());
                //Condiciona para cargar el ImageView [solo si devuelve un valor diferente a nulo]
                if(mValorRutaImagen != null){
                    if(mValorProteccionImg == 1){
                        Picasso.with(getContext())
                                .load(RUTA_CARPETA_ASSETS + mValorRutaImagen)
                                .error(R.drawable.ic_image_150dp)
                                .into(mIvImagenRecordatorio);
                    }else{
                        Picasso.with(getContext())
                                .load(new File(mValorRutaImagen))
                                .error(R.drawable.ic_image_150dp)
                                .into(mIvImagenRecordatorio);
                    }
                }

            }else{ //Compruebo si es tablet
                mCivImagenCategoria = (CircleImageView) activity.findViewById(R.id.civ_toolbar);
                mIbEliminarRecordatorio = (ImageButton) activity.findViewById(R.id.ib_eliminar_toolbar);
                mIbAcutualizarRecordatorio = (ImageButton) activity.findViewById(R.id.ib_editar_toolbar);

                if(mValorRutaImagen != null){
                    if(mValorProteccionImg == 1){
                        //Utilizo la librería Picasso
                        Picasso.with(getContext())
                                .load(RUTA_CARPETA_ASSETS + mValorRutaImagen)
                                .error(R.drawable.ic_image_150dp)
                                .into(mCivImagenCategoria);
                    }else{
                        //Utilizo la librería Picasso
                        Picasso.with(getContext())
                                .load(new File(mValorRutaImagen))
                                .error(R.drawable.ic_image_150dp)
                                .into(mCivImagenCategoria);
                    }


                }else{
                    mCivImagenCategoria.setImageResource(R.drawable.ic_image_150dp);
                }
                //Fab actualizar recordatorio
                fabAddUpd = (FloatingActionButton) activity.findViewById(R.id.fab_add_upd);
                //Oculto el botón de actualizar recordatorio
                fabAddUpd.setVisibility(View.INVISIBLE);

                mIbEliminarRecordatorio.setOnClickListener(this);
                mIbAcutualizarRecordatorio.setOnClickListener(this);
                mIbEliminarRecordatorio.setVisibility(View.VISIBLE);
                mIbAcutualizarRecordatorio.setVisibility(View.VISIBLE);

                //Titulo categoría
                ((TextView) activity.findViewById(R.id.tv_titulo_toolbar)).setText(mItemRecordatorio.getTitulo());

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
            ((TextView) v.findViewById(R.id.tv_mensaje)).setText(mItemRecordatorio.getContenidoMensaje());
            ((TextView) v.findViewById(R.id.tv_telefono)).setText(mItemRecordatorio.getTelefono());

            //Almaceno el valor de del campo Entidad Otros
            String entidadOtrosValor = mItemRecordatorio.getEntidadOtros();

            if(entidadOtrosValor.isEmpty()){
                mTvEntidadOtros.setWidth(0);
                mTvEntidadOtros.setHeight(0);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_eliminar_toolbar:
                eliminarRecordatorio();
                break;
            case R.id.ib_editar_toolbar:
                mCrud.cargarItem(mItemRecordatorio);
                break;
        }
    }

    public void eliminarRecordatorio(){
        new AlertDialog.Builder(getActivity())
            .setIcon(R.drawable.ic_info_24dp)
            .setTitle(getString(R.string.adb_titulo_eliminar))
            .setMessage(getString(R.string.adb_mensaje_eliminar))
            .setPositiveButton(getString(R.string.boton_aceptar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    //Eliminar registro
                    String idRecordatorio = mItemRecordatorio.getId();
                    mManager.eliminarRecordatorio(idRecordatorio); //Elimino el registro

                    //Interfaz que sirve para remover el item
                    mCrud.removerItem();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
            })
            .setNegativeButton(getString(R.string.boton_cancelar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Cancelar
                }
            })
            .show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Aquí nos aseguramos de que en la actividad se haya implementado la interfaz,
        // si el programador no la implementado se lanza el mensaje de error.
        try {
            mCrud = (InterfaceCrud) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Debe implementar las interfaces en su Activity");
        }
    }

    //Función llamada cuando el fragment es desasociada de una actividad
    @Override
    public void onDetach() {
        super.onDetach();
        mCrud = null;
    }

}
