package com.a01luisrene.multirecordatorio.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a01luisrene.multirecordatorio.fragmentos.ListaRecordatoriosFragmento;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaRecordatoriosAdaptador extends RecyclerView.Adapter<ListaRecordatoriosAdaptador.ViewHolder> {

    public static final String ELLIPSIS = "...";
    private List<Recordatorios> mListaItemsRecordatorios;
    private Context mContext;

    //Variable para la comunicación al fragment que contiene la lista
    ListaRecordatoriosFragmento.OnItemSelectedListener itemClickListener;


    //Proporcionar un constructor adecuado (depende del tipo de conjunto de datos)
    public ListaRecordatoriosAdaptador(List<Recordatorios> listaItemsRecordatorios,
                                       Context context,
                                       ListaRecordatoriosFragmento.OnItemSelectedListener itemClickListener) {

        this.mListaItemsRecordatorios = listaItemsRecordatorios;
        this.mContext = context;
        this.itemClickListener = itemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
            // Cada elemento de datos es sólo una cadena en este caso
            TextView id, titulo, entidadOtros, contenidoMensaje;
            ImageView ivImagenRecordatorio;
            CircleImageView civImagenRecordatorio;

        ViewHolder(View v) {
            super(v);
            v.setClickable(true);
            id = (TextView) v.findViewById(R.id.tv_id);
            titulo = (TextView) v.findViewById(R.id.tv_title_reminder);
            entidadOtros = (TextView) v.findViewById(R.id.tv_name_reminder);

            if(Utilidades.smartphone) {
                contenidoMensaje = (TextView) v.findViewById(R.id.tv_message_reminder);
                ivImagenRecordatorio = (ImageView) v.findViewById(R.id.civ_categoria_recordatorio);
            }else{
                civImagenRecordatorio = (CircleImageView) v.findViewById(R.id.civ_categoria_recordatorio);
            }


        }

        public void bind(final Context context, final Recordatorios items, final ListaRecordatoriosFragmento.OnItemSelectedListener itemClickListener) {

            id.setText(items.getId());

            //Almaceno los valores devueltos para realizar algunos cambios
            String valorTitulo = items.getTitulo();
            String valorEntidadOtros = items.getEntidadOtros();
            String valorImagen = items.getImagenRecordatorio();

            String tituloFormateado, entidadOtrosFormateado, mensajeFormateado;

            if(valorTitulo.length() >= 45){
                tituloFormateado = valorTitulo.substring(0, 45) + ELLIPSIS;
            }else{
                tituloFormateado = valorTitulo;
            }
            if(valorEntidadOtros.length() >= 35){
                entidadOtrosFormateado = valorEntidadOtros.substring(0, 35) + ELLIPSIS;
            }else{
                entidadOtrosFormateado = valorEntidadOtros;
            }

            titulo.setText(tituloFormateado);
            entidadOtros.setText(entidadOtrosFormateado);

            if(Utilidades.smartphone){ //Se aplica para resoluciones pequeñas como [smartphones]
                /**
                 * TEXTO DEL CONTENIDO MENSAJE FORMATEADO
                 */
                //Almaceno el valor de la cadena devuelta del contenido mensaje
                String valoraMensaje = items.getContenidoMensaje();

                //Condiciono para saber si la cadena tiene mas de 70 caracteres
                if(valoraMensaje.length() >= 80){
                    //Agrego ellipsis al texto si es mayor de 70
                    mensajeFormateado = valoraMensaje.substring(0, 60) + ELLIPSIS;
                }else{
                    //Muestro el texto original
                    mensajeFormateado = valoraMensaje;
                }

                //cargo los valores devueltos en el EditText
                contenidoMensaje.setText(mensajeFormateado);

                /**
                 * CARGO IMAGEN CON PICASSO
                 */
                //Condiciono para manejar si el valor devuelto es vacío
                if(valorImagen == null ||valorImagen.isEmpty()){
                    //[Si no hay imagen muestro una por defecto]
                    ivImagenRecordatorio.setImageResource(R.drawable.ic_image_150dp);


                }else{
                    //Cargo la imagen con la ayuda de la librería picasso
                    Picasso.with(context)
                            .load(new File(valorImagen))
                            .error(R.drawable.ic_image_150dp)
                            .into(ivImagenRecordatorio);
                }

            }else{ //Se aplica para resoluciones grandes como [tablets]
                /**
                 * CARGO IMAGEN CON PICASSO
                 */
                //Condiciono para manejar si el valor devuelto es vacío
                if(valorImagen == null ||valorImagen.isEmpty()){
                    //[Si no hay imagen muestro una por defecto]
                    civImagenRecordatorio.setImageResource(R.drawable.ic_image_150dp);

                }else{
                    //Cargo la imagen con la ayuda de la librería picasso
                    Picasso.with(context)
                            .load(new File(valorImagen))
                            .error(R.drawable.ic_image_150dp)
                            .into(civImagenRecordatorio);
                }
            }
            //Evento clic para el item seleccionado
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemSelected(items);
                }
            });
        }
    }


    //Crear nuevas vistas (invocadas por el gestor de diseño)
    @Override
    public ListaRecordatoriosAdaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //crear nueva vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recordatorio_list_item, parent, false);
        // Establecer el tamaño de la vista, los márgenes, los rellenos y los parámetros de diseño

        return new ViewHolder(v);
    }

    //Reemplazar el contenido de una vista (invocada por el gestor de diseño)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Recordatorios items = mListaItemsRecordatorios.get(position);
        holder.bind(mContext, items, itemClickListener);

    }

    public void agregarItemRecordatorio() {
        //mListaItemsRecordatorios.add(recordatorios);
        //notifyItemInserted(index);
        notifyDataSetChanged();
    }

    public void eliminarItemRecordatorio(int index) {
        mListaItemsRecordatorios.remove(index);
        notifyItemRemoved(index);
    }

    // Devuelve el tamaño de tu conjunto de datos (invocado por el administrador de diseño)
    @Override
    public int getItemCount() {

        if(mListaItemsRecordatorios != null){
            return mListaItemsRecordatorios.size() > 0 ? mListaItemsRecordatorios.size() : 0;
        }else{
            return 0;
        }

    }

}
