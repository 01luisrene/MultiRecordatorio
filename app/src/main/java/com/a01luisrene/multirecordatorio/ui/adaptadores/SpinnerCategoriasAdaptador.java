package com.a01luisrene.multirecordatorio.ui.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a01luisrene.multirecordatorio.R;
import com.a01luisrene.multirecordatorio.utilidades.Utilidades;

import java.util.List;


public class SpinnerCategoriasAdaptador extends BaseAdapter {
    private List<String> mListaCategorias;
    private Context mContext;

    public SpinnerCategoriasAdaptador(Context context, List<String> listaCategorias){
        this.mContext = context;
        this.mListaCategorias = listaCategorias;
    }
    @Override
    public int getCount() {
        return mListaCategorias.size();
    }

    @Override
    public Object getItem(int position) {
        return mListaCategorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Drawable drawable_titulo_main, drawable_titulos;

        drawable_titulo_main = ContextCompat.getDrawable(mContext, R.drawable.item_main_categoria_spinner);
        drawable_titulos = ContextCompat.getDrawable(mContext, R.drawable.item_titulos_categorias_spinner);

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        view = layoutInflater.inflate(android.R.layout.simple_spinner_item, null);

        TextView tituloCategoria =(TextView)view.findViewById(android.R.id.text1);

       String titulo =  mListaCategorias.get(position);

        String tituloFormateado;
        if(titulo.length() >= 40){
            tituloFormateado = titulo.substring(0, 40) + "...";
        }else{
            tituloFormateado = titulo;
        }

        if(position==0) { //Primer elemento

            tituloCategoria.setTextColor(Color.parseColor("#FFFFFF"));

            //Condicionar para smartphone & tablets
            if(Utilidades.smartphone){
                tituloCategoria.setTextSize(16);
            }else{
                tituloCategoria.setTextSize(20);
            }

            tituloCategoria.setBackground(drawable_titulo_main);

        }else {//N items

            tituloCategoria.setTextColor(Color.parseColor("#212121"));

            //Condicionar para smartphone & tablets
            if(Utilidades.smartphone){
                tituloCategoria.setTextSize(14);
            }else{
                tituloCategoria.setTextSize(18);
            }
            tituloCategoria.setBackground(drawable_titulos);

        }
        tituloCategoria.setText(tituloFormateado);

        return view;
    }
}
