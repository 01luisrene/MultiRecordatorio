package com.a01luisrene.multirecordatorio.ui.adaptadores;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a01luisrene.multirecordatorio.interfaces.InterfaceItemClicAdapter;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaRecordatoriosAdaptador 
        extends RecyclerView.Adapter<ListaRecordatoriosAdaptador.ViewHolder> {

    private static final String ELLIPSIS = "...";
    private List<Recordatorios> mListaRecordatorios;
    private Context mContext;

    //Inteface de comunicación del adaptador a lista fragment
    private InterfaceItemClicAdapter mItemClicAdapter;

    //Proporcionar un constructor adecuado (depende del tipo de conjunto de datos)
    public ListaRecordatoriosAdaptador(Context context,
                                       List<Recordatorios> listaRecordatorios,
                                       InterfaceItemClicAdapter itemClicAdapter) {

        this.mContext = context;
        this.mListaRecordatorios = listaRecordatorios;
        this.mItemClicAdapter = itemClicAdapter;
    }

    @Override
    public ListaRecordatoriosAdaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recordatorio_list_item, parent, false);

        return new ViewHolder(v);
    }

    //Reemplazar el contenido de una vista (invocada por el gestor de diseño)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Recordatorios items = mListaRecordatorios.get(position);
        holder.bind(mContext, items);

    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private static final String CERO = "0";
        // Cada elemento de datos es sólo una cadena en este caso
        TextView titulo, contenidoMensaje, fecha, hora;
        ImageView ivImagenRecordatorio;
        CircleImageView civImagenRecordatorio;
        ImageView ivFacebook, ivTwitter, ivSms;
        Drawable drawable_deshabilitado, drawable_facebook, drawable_twitter, drawable_sms;
        String valorTitulo, valorImagen, valorContenidoMensaje, tituloFormateado,
                contenidoMensajeFormateado, valorFacebook, valorTwitter, valorSms;
        int valorProteccionImg;

        ViewHolder(View v) {
            super(v);
            v.setClickable(true);
            titulo = (TextView) v.findViewById(R.id.tv_titulo_item);
            fecha = (TextView) v.findViewById(R.id.tv_fecha_item);
            hora = (TextView) v.findViewById(R.id.tv_hora_item);
            ivFacebook = (ImageView) v.findViewById(R.id.iv_facebook_item);
            ivTwitter = (ImageView) v.findViewById(R.id.iv_twitter_item);
            ivSms = (ImageView) v.findViewById(R.id.iv_sms_item);
            drawable_deshabilitado = ContextCompat.getDrawable(mContext, R.drawable.bt_deshabilitado_item);
            drawable_facebook = ContextCompat.getDrawable(mContext, R.drawable.bt_facebook_item);
            drawable_twitter = ContextCompat.getDrawable(mContext, R.drawable.bt_twitter_item);
            drawable_sms = ContextCompat.getDrawable(mContext, R.drawable.bt_sms_item);

            if(Utilidades.smartphone) {
                contenidoMensaje = (TextView) v.findViewById(R.id.tv_mensaje_item);
                ivImagenRecordatorio = (ImageView) v.findViewById(R.id.iv_categoria_item);
            }else{
                civImagenRecordatorio = (CircleImageView) v.findViewById(R.id.civ_categoria_item);
            }

        }

        void bind(final Context context,
                         final Recordatorios items) {

             //Almaceno los valores devueltos para realizar algunos cambios
            valorTitulo = items.getTitulo();
            valorContenidoMensaje = items.getContenidoMensaje();
            fecha.setText(items.getFechaPublicacionRecordatorio());
            hora.setText(items.getHoraPublicacionRecordatorio());
            valorFacebook = items.getPublicarFacebook();
            valorTwitter = items.getPublicarTwitter();
            valorSms = items.getEnvioMensaje();
            valorImagen = items.getRutaImagenRecordatorio();
            valorProteccionImg = items.getProteccionImg();

            //Título formateado
            if(valorTitulo.length() >= 45){
                tituloFormateado = valorTitulo.substring(0,45) + ELLIPSIS;
            }else{
                tituloFormateado = valorTitulo;
            }

            titulo.setText(tituloFormateado);

            if(Utilidades.smartphone){ //Se aplica para resoluciones pequeñas como [smartphones]
                //TEXTO DEL CONTENIDO MENSAJE FORMATEADO

                //Condiciono para saber si la cadena tiene mas de 70 caracteres
                if(valorContenidoMensaje.length() >= 65){
                    //Agrego ellipsis al texto si es mayor de 50
                    contenidoMensajeFormateado = valorContenidoMensaje.substring(0, 65) + ELLIPSIS;
                }else{
                    //Muestro el texto original
                    contenidoMensajeFormateado = valorContenidoMensaje;
                }

                //cargo los valores devueltos en el EditText
                contenidoMensaje.setText(contenidoMensajeFormateado);

                //Condiciono para manejar si el valor devuelto es vacío
                if(valorImagen == null ||valorImagen.isEmpty()){
                    //[Si no hay imagen muestro una por defecto]
                    ivImagenRecordatorio.setImageResource(R.drawable.ic_image_150dp);

                }else{
                    if(valorProteccionImg==1){
                        //Cargo la imagen con la ayuda de la librería picasso
                        Picasso.with(context)
                                .load("file:///android_asset/" + valorImagen)
                                .error(R.drawable.ic_image_150dp)
                                .into(ivImagenRecordatorio);
                    }else{
                        //Cargo la imagen con la ayuda de la librería picasso
                        Picasso.with(context)
                                .load(new File(valorImagen))
                                .error(R.drawable.ic_image_150dp)
                                .into(ivImagenRecordatorio);
                    }
                }

            }else{ //Se aplica para resoluciones grandes como [tablets]

                //Condiciono para manejar si el valor devuelto es vacío
                if(valorImagen == null ||valorImagen.isEmpty()){
                    //[Si no hay imagen muestro una por defecto]
                    civImagenRecordatorio.setImageResource(R.drawable.ic_image_150dp);

                }else{
                    if(valorProteccionImg == 1){
                        //Cargo la imagen con la ayuda de la librería picasso
                        Picasso.with(context)
                                .load("file:///android_asset/" + valorImagen)
                                .error(R.drawable.ic_image_150dp)
                                .into(civImagenRecordatorio);
                    }else{
                        //Cargo la imagen con la ayuda de la librería picasso
                        Picasso.with(context)
                                .load(new File(valorImagen))
                                .error(R.drawable.ic_image_150dp)
                                .into(civImagenRecordatorio);
                    }
                }
            }

            //PREFERENCIAS DE ENVIO MENSAJE, PUBLICACIÓN O TWITTEO
            if(valorFacebook.equals(CERO)){
                ivFacebook.setBackground(drawable_deshabilitado);
            }else{
                ivFacebook.setBackground(drawable_facebook);
            }
            if(valorTwitter.equals(CERO)){
                ivTwitter.setBackground(drawable_deshabilitado);
            }else{
                ivTwitter.setBackground(drawable_twitter);
            }
            if (valorSms.equals(CERO)){
                ivSms.setBackground(drawable_deshabilitado);
            }else{
                ivSms.setBackground(drawable_sms);
            }
            //Evento clic para el item seleccionado
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicion  = ViewHolder.super.getAdapterPosition();
                    mItemClicAdapter.itemClicAdapter(items, posicion);
                }
            });
        }
    }

    public void removerItem(int posicion) {
        mListaRecordatorios.remove(posicion);
        notifyItemRemoved(posicion);
        notifyItemRangeChanged(posicion, mListaRecordatorios.size());
    }

    public void agregarItem(Recordatorios recordatorios) {
        mListaRecordatorios.add(0, recordatorios);
        notifyItemInserted(0);
        notifyItemRangeChanged(0, mListaRecordatorios.size());

    }
    public void actualizarItem(int posicion, Recordatorios recordatorios){
        mListaRecordatorios.set(posicion, recordatorios);
       notifyItemChanged(posicion);
    }

    // Devuelve el tamaño del data
    @Override
    public int getItemCount() {
        return mListaRecordatorios.size();
    }

}
