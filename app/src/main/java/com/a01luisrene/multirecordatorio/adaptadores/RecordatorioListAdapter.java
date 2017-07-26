package com.a01luisrene.multirecordatorio.adaptadores;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a01luisrene.multirecordatorio.modelos.Recordatorio;
import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.sqlite.DataBaseManagerRecordatorios;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecordatorioListAdapter extends RecyclerView.Adapter<RecordatorioListAdapter.ViewHolder> {

    List<Recordatorio> mListaItemsRecordatorio;
    DataBaseManagerRecordatorios mManager;
    Context mMainContext;
    Recordatorio recordatorio;
    OnItemClickListener escuchaClicksExterna;

    //Proporcionar un constructor adecuado (depende del tipo de conjunto de datos)
    public RecordatorioListAdapter(List<Recordatorio> listaItemsRecordatorio,
                                   Context context) {

        this.mListaItemsRecordatorio = listaItemsRecordatorio;
        this.mMainContext = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            // Cada elemento de datos es sólo una cadena en este caso
            private final TextView id;
            private final TextView titulo;
            private final TextView nombres;
            private final TextView contenidoMensaje;
            private final CircleImageView civImagenRecordatorio;

        ViewHolder(View v) {
                super(v);
                v.setClickable(true);
                id = (TextView) v.findViewById(R.id.tv_id);
                titulo = (TextView) v.findViewById(R.id.tv_title_reminder);
                nombres = (TextView) v.findViewById(R.id.tv_name_reminder);
                contenidoMensaje = (TextView) v.findViewById(R.id.tv_message_reminder);
                civImagenRecordatorio = (CircleImageView) v.findViewById(R.id.civ_imagen);

                v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            escuchaClicksExterna.onClick(this, obtenerIdArticulo(getAdapterPosition()));
        }

    }

    private String obtenerIdArticulo(int posicion) {
        if (posicion != RecyclerView.NO_POSITION) {
            return recordatorio.getId(posicion);
        } else {
            return null;
        }
    }

    // Crear nuevas vistas (invocadas por el gestor de diseño)
    @Override
    public RecordatorioListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //crear nueva vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recordatorio_list_item, parent, false);
        // Establecer el tamaño de la vista, los márgenes, los rellenos y los parámetros de diseño

        return new ViewHolder(v);
    }

    //Reemplazar el contenido de una vista (invocada por el gestor de diseño)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.id.setText(String.valueOf(recordatorio.getId(0)));
        holder.titulo.setText(recordatorio.getTitulo());
        holder.nombres.setText(recordatorio.getentidadOtros());

        /**
         * TEXTO DEL CONTENIDO MENSAJE FORMATEADO
         */

        //Almaceno el valor de la cadena devuelta del contenido mensaje
        String cadenaMensaje = recordatorio.getContenidoMensaje();
        //creo la variable que almacenara el texto formateado
        String cadenaFormateada = "";
        //Condiciono para saber si la cadena tiene mas de 70 caracteres
        if(cadenaMensaje.length() > 70){
            //Agrego ellipsis al texto si es mayor de 70
            cadenaFormateada = cadenaMensaje.substring(0, 70) + "...";
        }else{
            //Muestro el texto original
            cadenaFormateada = cadenaMensaje;
        }
        //cargo los valores devueltos en el EditText
        holder.contenidoMensaje.setText(cadenaFormateada);

        /**
         * CARGO IMAGEN CON PICASSO
         */

        //Almaceno el valor devuelto la la ruta de imagen en string
        String valorImagen = recordatorio.getImagen();

        //Condiciono para manejar si el valor devuelto es null
        if(valorImagen == null ||  valorImagen.isEmpty()){
            //Cargo la imagen con la ayuda de la librería picasso
            Picasso.with(mMainContext)
                    .load(R.drawable.ic_image_150dp)
                    .resize(350, 350)
                    .into(holder.civImagenRecordatorio);
        }else{
            //Cargo la imagen con la ayuda de la librería picasso
            Picasso.with(mMainContext)
                    .load(new File(valorImagen))
                    .resize(350, 350)
                    .error(R.drawable.ic_image_150dp)
                    .into(holder.civImagenRecordatorio);
        }

    }

    // Devuelve el tamaño de tu conjunto de datos (invocado por el administrador de diseño)
    @Override
    public int getItemCount() {
        if (mListaItemsRecordatorio != null) {
            return mListaItemsRecordatorio.size() > 0 ? mListaItemsRecordatorio.size() : 0;
        } else {
            return 0;
        }
    }

    public interface OnItemClickListener {
         void onClick(ViewHolder viewHolder, String idArticulo);
    }
}
