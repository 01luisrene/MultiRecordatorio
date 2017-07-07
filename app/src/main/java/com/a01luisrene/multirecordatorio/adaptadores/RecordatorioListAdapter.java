package com.a01luisrene.multirecordatorio.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a01luisrene.multirecordatorio.modelos.Recordatorio;
import com.a01luisrene.multirecordatorio.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecordatorioListAdapter extends RecyclerView.Adapter<RecordatorioListAdapter.ViewHolder> {
    private List<Recordatorio> mDataset;
    private Context mainContext;

    // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            TextView id;
            TextView titulo;
            TextView nombres;
            TextView contenidoMensaje;
            CircleImageView civ_avatar_reminder;

            ViewHolder(View v) {
                super(v);
                id = (TextView) v.findViewById(R.id.tv_id);
                titulo = (TextView) v.findViewById(R.id.tv_title_reminder);
                nombres = (TextView) v.findViewById(R.id.tv_name_reminder);
                contenidoMensaje = (TextView) v.findViewById(R.id.tv_message_reminder);
                civ_avatar_reminder = (CircleImageView) v.findViewById(R.id.civ_avatar_reminder);

                //v.setOnClickListener(this);
            }
        }

        // Proporcionar un constructor adecuado (depende del tipo de conjunto de datos)
        public RecordatorioListAdapter(List<Recordatorio> mDataset, Context mainContext) {
            this.mDataset = mDataset;
            this.mainContext = mainContext;
        }

        // Crear nuevas vistas (invocadas por el gestor de diseño)
        @Override
        public RecordatorioListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recordatorio_list_item, parent, false);
            // Establecer el tamaño de la vista, los márgenes, los rellenos y los parámetros de diseño

            return new ViewHolder(v);
        }


    //Reemplazar el contenido de una vista (invocada por el gestor de diseño)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - Obtener elemento de su conjunto de datos en esta posición
            // - Reemplazar el contenido de la vista con ese elemento
            holder.id.setText(mDataset.get(position).getId());
            holder.titulo.setText(mDataset.get(position).getTitle());
            holder.nombres.setText(mDataset.get(position).getName());
            holder.contenidoMensaje.setText(mDataset.get(position).getContentMessage());

        }

        // Devuelve el tamaño de tu conjunto de datos (invocado por el administrador de diseño)
        @Override
        public int getItemCount() {
            if (mDataset != null) {
                return mDataset.size() > 0 ? mDataset.size() : 0;
            } else {
                return 0;
            }
        }
    }
