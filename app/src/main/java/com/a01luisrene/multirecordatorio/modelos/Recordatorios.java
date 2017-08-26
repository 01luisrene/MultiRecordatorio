package com.a01luisrene.multirecordatorio.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Recordatorios implements Parcelable {
    private String id;
    private String titulo;
    private String entidadOtros;
    private String imagenRecordatorio;
    private String categoriaRecordatorio;
    private String telefono;
    private String contenidoMensaje;
    private String envioMensaje;
    private String publicarFacebook;
    private String publicarTwitter;
    private String fechaPublicacionRecordatorio;
    private String horaPublicacionRecordatorio;


    /**
     * CONSTRUCTOR
     */

    public Recordatorios(){

        //Vacío [Requerido]

    }

    public String getId() {
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

    public String getEntidadOtros() {
        return entidadOtros;
    }

    public void setEntidadOtros(String entidadOtros) {
        this.entidadOtros = entidadOtros;
    }

    public String getImagenRecordatorio() {
        return imagenRecordatorio;
    }

    public void setImagenRecordatorio(String imagenRecordatorio) {
        this.imagenRecordatorio = imagenRecordatorio;
    }

    public String getCategoriaRecordatorio() {
        return categoriaRecordatorio;
    }

    public void setCategoriaRecordatorio(String categoriaRecordatorio) {
        this.categoriaRecordatorio = categoriaRecordatorio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContenidoMensaje() {
        return contenidoMensaje;
    }

    public void setContenidoMensaje(String contenidoMensaje) {
        this.contenidoMensaje = contenidoMensaje;
    }

    public String getEnvioMensaje() {
        return envioMensaje;
    }

    public void setEnvioMensaje(String envioMensaje) {
        this.envioMensaje = envioMensaje;
    }

    public String getPublicarFacebook() {
        return publicarFacebook;
    }

    public void setPublicarFacebook(String publicarFacebook) {
        this.publicarFacebook = publicarFacebook;
    }

    public String getPublicarTwitter() {
        return publicarTwitter;
    }

    public void setPublicarTwitter(String publicarTwitter) {
        this.publicarTwitter = publicarTwitter;
    }

    public String getFechaPublicacionRecordatorio() {
        return fechaPublicacionRecordatorio;
    }

    public void setFechaPublicacionRecordatorio(String fechaPublicacionRecordatorio) {
        this.fechaPublicacionRecordatorio = fechaPublicacionRecordatorio;
    }

    public String getHoraPublicacionRecordatorio() {
        return horaPublicacionRecordatorio;
    }

    public void setHoraPublicacionRecordatorio(String horaPublicacionRecordatorio) {
        this.horaPublicacionRecordatorio = horaPublicacionRecordatorio;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.titulo);
        dest.writeString(this.entidadOtros);
        dest.writeString(this.imagenRecordatorio);
        dest.writeString(this.categoriaRecordatorio);
        dest.writeString(this.telefono);
        dest.writeString(this.contenidoMensaje);
        dest.writeString(this.envioMensaje);
        dest.writeString(this.publicarFacebook);
        dest.writeString(this.publicarTwitter);
        dest.writeString(this.fechaPublicacionRecordatorio);
        dest.writeString(this.horaPublicacionRecordatorio);
    }

    protected Recordatorios(Parcel in) {
        this.id = in.readString();
        this.titulo = in.readString();
        this.entidadOtros = in.readString();
        this.imagenRecordatorio = in.readString();
        this.categoriaRecordatorio = in.readString();
        this.telefono = in.readString();
        this.contenidoMensaje = in.readString();
        this.envioMensaje = in.readString();
        this.publicarFacebook = in.readString();
        this.publicarTwitter = in.readString();
        this.fechaPublicacionRecordatorio = in.readString();
        this.horaPublicacionRecordatorio = in.readString();
    }

    public static final Parcelable.Creator<Recordatorios> CREATOR = new Parcelable.Creator<Recordatorios>() {
        @Override
        public Recordatorios createFromParcel(Parcel source) {
            return new Recordatorios(source);
        }

        @Override
        public Recordatorios[] newArray(int size) {
            return new Recordatorios[size];
        }
    };
}
