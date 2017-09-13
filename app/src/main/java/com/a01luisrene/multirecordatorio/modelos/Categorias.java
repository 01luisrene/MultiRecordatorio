package com.a01luisrene.multirecordatorio.modelos;

public class Categorias {
    private String id;
    private String imagen;
    private String categorioRecordatorio;

    /**
     * CONSTRUCTOR
     */

    public Categorias() {

    }

    public Categorias(String id, String imagen, String categorioRecordatorio) {
        this.id = id;
        this.imagen = imagen;
        this.categorioRecordatorio = categorioRecordatorio;
    }

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

    //Spinner
    public String toString(){
        //Nombre del campo a mostrar en el combo
        return categorioRecordatorio;
    }

}
