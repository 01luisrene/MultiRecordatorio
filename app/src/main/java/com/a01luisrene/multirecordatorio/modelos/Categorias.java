package com.a01luisrene.multirecordatorio.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Categorias implements Parcelable {
    private String id;
    private String imagen;
    private String tituloCategoria;
    private int proteccion;

    /**
     * CONSTRUCTOR
     */

    public Categorias(String id, String imagen, String tituloCategoria, int proteccion) {
        this.id = id;
        this.imagen = imagen;
        this.tituloCategoria = tituloCategoria;
        this.proteccion = proteccion;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTituloCategoria() {
        return tituloCategoria;
    }

    public void setTituloCategoria(String tituloCategoria) {
        this.tituloCategoria = tituloCategoria;
    }

    public int getProteccion() {
        return proteccion;
    }

    public void setProteccion(int proteccion) {
        this.proteccion = proteccion;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.imagen);
        dest.writeString(this.tituloCategoria);
        dest.writeInt(this.proteccion);
    }

    protected Categorias(Parcel in) {
        this.id = in.readString();
        this.imagen = in.readString();
        this.tituloCategoria = in.readString();
        this.proteccion = in.readInt();
    }

    public static final Parcelable.Creator<Categorias> CREATOR = new Parcelable.Creator<Categorias>() {
        @Override
        public Categorias createFromParcel(Parcel source) {
            return new Categorias(source);
        }

        @Override
        public Categorias[] newArray(int size) {
            return new Categorias[size];
        }
    };
}
