package com.a01luisrene.multirecordatorio.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Recordatorios implements Parcelable {
    private String id;
    private String titulo;
    private String entidadOtros;
    private String contenidoMensaje;
    private String publicarFacebook;
    private String publicarTwitter;
    private String envioMensaje;
    private String telefono;
    private String fechaCreacionRecordatorio;
    private String fechaPublicacionRecordatorio;
    private String horaPublicacionRecordatorio;
    private String titulocategoria;
    private String rutaImagenRecordatorio;
    private int proteccionImg;

    /**
     * CONSTRUCTOR
     */
    public Recordatorios(String id, String titulo, String entidadOtros, String contenidoMensaje, String publicarFacebook, String publicarTwitter, String envioMensaje, String telefono, String fechaCreacionRecordatorio, String fechaPublicacionRecordatorio, String horaPublicacionRecordatorio, String titulocategoria, String rutaImagenRecordatorio, int proteccionImg) {
        this.id = id;
        this.titulo = titulo;
        this.entidadOtros = entidadOtros;
        this.contenidoMensaje = contenidoMensaje;
        this.publicarFacebook = publicarFacebook;
        this.publicarTwitter = publicarTwitter;
        this.envioMensaje = envioMensaje;
        this.telefono = telefono;
        this.fechaCreacionRecordatorio = fechaCreacionRecordatorio;
        this.fechaPublicacionRecordatorio = fechaPublicacionRecordatorio;
        this.horaPublicacionRecordatorio = horaPublicacionRecordatorio;
        this.titulocategoria = titulocategoria;
        this.rutaImagenRecordatorio = rutaImagenRecordatorio;
        this.proteccionImg = proteccionImg;
    }
    /**
     * GETTERS & SETTERS
     */
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

    public String getContenidoMensaje() {
        return contenidoMensaje;
    }

    public void setContenidoMensaje(String contenidoMensaje) {
        this.contenidoMensaje = contenidoMensaje;
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

    public String getEnvioMensaje() {
        return envioMensaje;
    }

    public void setEnvioMensaje(String envioMensaje) {
        this.envioMensaje = envioMensaje;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaCreacionRecordatorio() {
        return fechaCreacionRecordatorio;
    }

    public void setFechaCreacionRecordatorio(String fechaCreacionRecordatorio) {
        this.fechaCreacionRecordatorio = fechaCreacionRecordatorio;
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

    public String getTitulocategoria() {
        return titulocategoria;
    }

    public void setTitulocategoria(String titulocategoria) {
        this.titulocategoria = titulocategoria;
    }

    public String getRutaImagenRecordatorio() {
        return rutaImagenRecordatorio;
    }

    public void setRutaImagenRecordatorio(String rutaImagenRecordatorio) {
        this.rutaImagenRecordatorio = rutaImagenRecordatorio;
    }

    public int getProteccionImg() {
        return proteccionImg;
    }

    public void setProteccionImg(int proteccionImg) {
        this.proteccionImg = proteccionImg;
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
        dest.writeString(this.contenidoMensaje);
        dest.writeString(this.publicarFacebook);
        dest.writeString(this.publicarTwitter);
        dest.writeString(this.envioMensaje);
        dest.writeString(this.telefono);
        dest.writeString(this.fechaCreacionRecordatorio);
        dest.writeString(this.fechaPublicacionRecordatorio);
        dest.writeString(this.horaPublicacionRecordatorio);
        dest.writeString(this.titulocategoria);
        dest.writeString(this.rutaImagenRecordatorio);
        dest.writeInt(this.proteccionImg);
    }

    protected Recordatorios(Parcel in) {
        this.id = in.readString();
        this.titulo = in.readString();
        this.entidadOtros = in.readString();
        this.contenidoMensaje = in.readString();
        this.publicarFacebook = in.readString();
        this.publicarTwitter = in.readString();
        this.envioMensaje = in.readString();
        this.telefono = in.readString();
        this.fechaCreacionRecordatorio = in.readString();
        this.fechaPublicacionRecordatorio = in.readString();
        this.horaPublicacionRecordatorio = in.readString();
        this.titulocategoria = in.readString();
        this.rutaImagenRecordatorio = in.readString();
        this.proteccionImg = in.readInt();
    }

    public static final Creator<Recordatorios> CREATOR = new Creator<Recordatorios>() {
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