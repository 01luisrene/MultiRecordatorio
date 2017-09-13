package com.a01luisrene.multirecordatorio.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DataBaseManager {

    private SQLiteOpenHelper mRecordatoriosDbHelper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {

        mRecordatoriosDbHelper = SQLiteOpenHelper.getInstance(context);
        db = mRecordatoriosDbHelper.getWritableDatabase();

    }

    public void cerrar(){
        db.close();
    }
    /**
     *
     * METODOSS PARA LA TABLA RECORDATORIOS
     *
     **/
    //Insertar un registro
    abstract public void insertarRecoratorio(String id,
                                             String titulo,
                                             String nombresOtros,
                                             String contenidoMensaje,
                                             String publicarFacebook,
                                             String publicarTwitter,
                                             String envioMensaje,
                                             String telefono,
                                             String fechaCreacion,
                                             String fechaRecordatorio,
                                             String horaRecordatorio,
                                             String estadoRecordatorio,
                                             String categoria);
    //Actualizar un registro
    abstract public void actualizarRecoratorio(String id,
                                               String titulo,
                                               String nombresOtros,
                                               String contenidoMensaje,
                                               String publicarFacebook,
                                               String publicarTwitter,
                                               String envioMensaje,
                                               String telefono,
                                               String fechaCreacion,
                                               String fechaRecordatorio,
                                               String horaRecordatorio,
                                               String estadoRecordatorio,
                                               String categoria);

    //Eliminar el recordatorio seleccionado
    abstract public void eliminarRecordatorio(String id);

    //Eliminar todos los recordatorios
    abstract public void eliminarRecordatorios();

    abstract public Cursor cargarCursorRecordatorios();

    abstract public  Cursor buscarRecordatorio(String titulo);

    abstract public Boolean compruebaRegistroRecordatorio(String id);

    abstract public int idRecordatorioMax();

    /**
     *
     * METODOS PARA LA TABLA CATEGORÍA RECORDATORIOS
     */

    abstract public void insertarCategoriaRecordatorio(String id,
                                                       String rutaImagenRecordatorio,
                                                       String categoriaRecordatorio,
                                                       int proteccion,
                                                       String fechaCreacion);

    abstract public void actualizarCategoriaRecordatorio(String id,
                                                         String rutaImagenRecordatorio,
                                                         String categoriaRecordatorio,
                                                         String proteccion,
                                                         String fechaCreacion);

    abstract public void eliminarCategoriaRecordatorio(String id);

    abstract public void eliminarCategoriaRecordatorios();

    abstract public Cursor cargarCursorCategorias();

    abstract public Cursor buscarCategoriasRecordatorios(String categoriaRecordatorio);

    abstract public Boolean compruebaRegistroCategoriaRecordatorio(String id);


    /**
     *
     * GETTER & SETTER
     **/

    public SQLiteOpenHelper getRecordatoriosDbHelper() {
        return mRecordatoriosDbHelper;
    }

    public void setRecordatoriosDbHelper(SQLiteOpenHelper recordatoriosDbHelper) {
        mRecordatoriosDbHelper = recordatoriosDbHelper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }
}
