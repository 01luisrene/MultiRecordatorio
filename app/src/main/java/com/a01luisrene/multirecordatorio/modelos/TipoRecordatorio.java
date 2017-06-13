package com.a01luisrene.multirecordatorio.modelos;

/**
 * Created by LUIS on 10/06/2017.
 */

public class TipoRecordatorio {
    private String id;
    private String tipoRecordatorio;
    private String proteccion;

    public TipoRecordatorio(){

    }

    public TipoRecordatorio(String id, String tipoRecordatorio, String proteccion) {
        this.id = id;
        this.tipoRecordatorio = tipoRecordatorio;
        this.proteccion = proteccion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
