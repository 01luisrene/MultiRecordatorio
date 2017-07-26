package com.a01luisrene.multirecordatorio.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recordatorio implements Parcelable{
    private String id;
    private String titulo;
    private String entidadOtros;
    private String imagen;
    private String contenidoMensaje;



    /**
     * PARCELABLE
     */

    protected Recordatorio(Parcel in) {
        id = in.readString();
        titulo = in.readString();
        entidadOtros = in.readString();
        imagen = in.readString();
        contenidoMensaje = in.readString();
    }

    public static final Creator<Recordatorio> CREATOR = new Creator<Recordatorio>() {
        @Override
        public Recordatorio createFromParcel(Parcel in) {
            return new Recordatorio(in);
        }

        @Override
        public Recordatorio[] newArray(int size) {
            return new Recordatorio[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(titulo);
        dest.writeString(entidadOtros);
        dest.writeString(imagen);
        dest.writeString(contenidoMensaje);
    }

    /**
     * CONSTRUCTOR

     */
    public Recordatorio() {

    }

    public Recordatorio(String id, String titulo, String entidadOtros, String imagen, String contenidoMensaje) {
        this.id = id;
        this.titulo = titulo;
        this.entidadOtros = entidadOtros;
        this.imagen = imagen;
        this.contenidoMensaje = contenidoMensaje;
    }

    /**
    *
    * GETTER & SETTER
    *
    *
     * @param i*/

    public String getId(int i) {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getentidadOtros() {
        return entidadOtros;
    }

    public void setentidadOtros(String entidadOtros) {
        this.entidadOtros = entidadOtros;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getContenidoMensaje() {
        return contenidoMensaje;
    }

    public void setContenidoMensaje(String contenidoMensaje) {
        this.contenidoMensaje = contenidoMensaje;
    }

}
