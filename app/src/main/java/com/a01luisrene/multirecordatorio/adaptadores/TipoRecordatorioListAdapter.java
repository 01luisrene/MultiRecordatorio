package com.a01luisrene.multirecordatorio.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.modelos.TipoRecordatorio;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TipoRecordatorioListAdapter extends RecyclerView.Adapter<TipoRecordatorioListAdapter.ViewHolder> {
    private List<TipoRecordatorio> mDataset;
    private Context mMainContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Cada elemento de datos es sólo una cadena en este caso
        TextView id;
        TextView titulo;
        CircleImageView civAvatarTipoRecordatorio;
        ViewHolder(View v) {
            super(v);
            id = (TextView) v.findViewById(R.id.tv_ver_id_tipo_recordatorio);
            titulo = (TextView) v.findViewById(R.id.tv_ver_titulo_tipo_recordatorio);
            civAvatarTipoRecordatorio = (CircleImageView) v.findViewById(R.id.civ_ver_tipo_recordatorio);
        }
    }

    // Proporcionar un constructor adecuado (depende del tipo de conjunto de datos)
    public TipoRecordatorioListAdapter(List<TipoRecordatorio> mDataset, Context mainContext) {
        this.mDataset = mDataset;
        this.mMainContext = mainContext;
    }

    // Crear nuevas vistas (invocadas por el gestor de diseño)
    @Override
    public TipoRecordatorioListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recordatorio_categoria_lista_item, parent, false);
        // Establecer el tamaño de la vista, los márgenes, los rellenos y los parámetros de diseño

        return new ViewHolder(v);
    }


    //Reemplazar el contenido de una vista (invocada por el gestor de diseño)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.id.setText(mDataset.get(position).getId());
        holder.titulo.setText(mDataset.get(position).getCategorioRecordatorio());

        //Almaceno el valor devuelto la la ruta de imagen en string
        String valorImagen = mDataset.get(position).getImagen();

        //Condiciono para manejar si el valor devuelto es null
        if(valorImagen == null ||  valorImagen.isEmpty()){
            //Cargo la imagen con la ayuda de la librería picasso
            Picasso.with(mMainContext)
                    .load(R.drawable.ic_image_150dp)
                    .resize(350, 350)
                    .into(holder.civAvatarTipoRecordatorio);
        }else{
            //Cargo la imagen con la ayuda de la librería picasso
            Picasso.with(mMainContext)
                    .load(new File(mDataset.get(position).getImagen()))
                    .resize(350, 350)
                    .error(R.drawable.ic_image_150dp)
                    .into(holder.civAvatarTipoRecordatorio);
        }

    }
    //Devuelve el tamaño de tu conjunto de datos (invocado por el administrador de diseño)
    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size() > 0 ? mDataset.size() : 0;
        } else {
            return 0;
        }
    }
}
