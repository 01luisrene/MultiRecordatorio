package com.a01luisrene.multirecordatorio.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DataBaseManager {

    private RecordatorioHelper mRecordatoriosDbHelper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {

        mRecordatoriosDbHelper = RecordatorioHelper.getInstance(context);
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
                                             String tipoMensaje,
                                             String contenidoMensaje,
                                             String telefono,
                                             String envioMensaje,
                                             String publicarFacebook,
                                             String publicarTwitter,
                                             String fechaCreacion,
                                             String fechaRecordatorio,
                                             String estadoRecordatorio);
    //Actualizar un registro
    abstract public void actualizarRecoratorio(String id,
                                               String titulo,
                                               String nombresOtros,
                                               String tipoMensaje,
                                               String contenidoMensaje,
                                               String telefono,
                                               String envioMensaje,
                                               String publicarFacebook,
                                               String publicarTwitter,
                                               String fechaCreacion,
                                               String fechaRecordatorio,
                                               String estadoRecordatorio);

    //Eliminar el recordatorio seleccionado
    abstract public void eliminarRecordatorio(String id);

    //Eliminar todos los recordatorios
    abstract public void eliminarRecordatorios();

    abstract public Cursor cargarCursorRecordatorios();

    abstract public  Cursor buscarRecordatorio(String titulo);

    abstract public Boolean compruebaRegistroRecordatorio(String id);

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

    abstract public Cursor cargarCursorCategoriaRecordatorios();

    abstract public Cursor buscarCategoriasRecordatorios(String categoriaRecordatorio);

    abstract public Boolean compruebaRegistroCategoriaRecordatorio(String id);


    /**
     *
     * GETTER & SETTER
     **/

    public RecordatorioHelper getRecordatoriosDbHelper() {
        return mRecordatoriosDbHelper;
    }

    public void setRecordatoriosDbHelper(RecordatorioHelper recordatoriosDbHelper) {
        mRecordatoriosDbHelper = recordatoriosDbHelper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }
}
