package com.a01luisrene.multirecordatorio.modelos;

public class TipoRecordatorio {
    private String id;
    private String imagen;
    private String categorioRecordatorio;


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

    public String getCategorioRecordatorio() {
        return categorioRecordatorio;
    }

    public void setCategorioRecordatorio(String categorioRecordatorio) {
        this.categorioRecordatorio = categorioRecordatorio;
    }

}
