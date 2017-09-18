package com.a01luisrene.multirecordatorio.io.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.a01luisrene.multirecordatorio.modelos.Categorias;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;

import java.util.List;

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

    abstract public boolean compruebaTituloRecordatorio(String titulo);

    abstract public String tituloRecordatorioQueExiste(String titulo);

    abstract public boolean compruebaTituloRecordatorioUp(String titulo, String tituloObtenido);

    abstract public String tituloRecordatorioQueExisteUp(String titulo);

    abstract public int idRecordatorioMax();

    abstract public List<Recordatorios> getListaRecordatorios();

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

    abstract public boolean compruebaTituloCategoria(String tituloCategoria);

    abstract public String tituloCategoriaQueExiste(String tituloCategoria);

    abstract public boolean compruebaTituloCategoriaUp(String tituloCategoria, String tituloCategoriaObtenido);

    abstract public String tituloCategoriaQueExisteUp(String tituloCategoria);

    abstract public List<Categorias> getListaCategorias();


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
