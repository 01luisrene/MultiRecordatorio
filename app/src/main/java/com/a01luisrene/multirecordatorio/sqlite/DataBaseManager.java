package com.a01luisrene.multirecordatorio.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by LUIS on 01/06/2017.
 */

public abstract class DataBaseManager {

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    public DataBaseManager(Context context) {

        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();

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
     * METODOS PARA LA TABLA TIPO RECORDATORIOS
     */

    abstract public void insertarTipoRecordatorio(String id,
                                                  String iconoRecordatorio,
                                                  String tipoRecordatorio,
                                                  String proteccion,
                                                  String fechaCreacion);

    abstract public void actualizarTipoRecordatorio(String id,
                                                    String iconoRecordatorio,
                                                    String tipoRecordatorio,
                                                    String proteccion,
                                                    String fechaCreacion);

    abstract public void eliminarTipoRecordatorio(String id);

    abstract public void eliminarTipoRecordatorios();

    abstract public Cursor cargarCursorTipoRecordatorios();

    abstract public Cursor buscarTipoRecordatorio(String tipoRecordatorio);

    abstract public Boolean compruebaRegistroTipoRecordatorio(String id);


    /**
    *
    * GETTER & SETTER
    **/
    public DbHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

}
