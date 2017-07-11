package com.a01luisrene.multirecordatorio.modelos;

public class TipoRecordatorio {
    private String id;
    private String imagen;
    private String tipoRecordatorio;
    private String proteccion;
    private String fecha;


    /**
     * GETTER & SETTER
     */

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

    public String getTipoRecordatorio() {
        return tipoRecordatorio;
    }

    public void setTipoRecordatorio(String tipoRecordatorio) {
        this.tipoRecordatorio = tipoRecordatorio;
    }

    public String getProteccion() {
        return proteccion;
    }

    public void setProteccion(String proteccion) {
        this.proteccion = proteccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
