package com.a01luisrene.multirecordatorio.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.a01luisrene.multirecordatorio.modelos.Recordatorio;
import com.a01luisrene.multirecordatorio.modelos.TipoRecordatorio;

import java.util.ArrayList;
import java.util.List;


public class DataBaseManagerRecordatorios extends DataBaseManager {

    public DataBaseManagerRecordatorios(Context context) {
        super(context);
    }
    @Override
    public void cerrar(){
        super.getDb().close();
    }

    //Constructor usado para insertar registros
    private ContentValues generarContentValues(String id,
                                               String titulo,
                                               String nombres_otros,
                                               String tipoRecordatorio,
                                               String contenidoMensaje,
                                               String telefono,
                                               String envioMensaje,
                                               String publicarFacebook,
                                               String publicarTwitter,
                                               String fechaCreacion,
                                               String fechaRecordatorio,
                                               String estadoRecordatorio){

        ContentValues valores = new ContentValues();
        valores.put(TablasDb.TablaRecordatorios.CN_ID, id);
        valores.put(TablasDb.TablaRecordatorios.CN_TITLE, titulo);
        valores.put(TablasDb.TablaRecordatorios.CN_NAME_OTHER, nombres_otros);
        valores.put(TablasDb.TablaRecordatorios.CN_REMINDER_TYPE, tipoRecordatorio);
        valores.put(TablasDb.TablaRecordatorios.CN_PHONE, telefono);
        valores.put(TablasDb.TablaRecordatorios.CN_CONTENT_MESSAGE, contenidoMensaje);
        valores.put(TablasDb.TablaRecordatorios.CN_SEND_MESSAGE, envioMensaje);
        valores.put(TablasDb.TablaRecordatorios.CN_FACEBOOK, publicarFacebook);
        valores.put(TablasDb.TablaRecordatorios.CN_TWITTER, publicarTwitter);
        valores.put(TablasDb.TablaRecordatorios.CN_CREATION_DATE, fechaCreacion);
        valores.put(TablasDb.TablaRecordatorios.CN_REMINDER_DATE, fechaRecordatorio);
        valores.put(TablasDb.TablaRecordatorios.CN_STATE, estadoRecordatorio);

        return valores;

    }

    @Override
    public void insertarRecoratorio(String id,
                                    String titulo,
                                    String nombresOtros,
                                    String tipoRecordatorio,
                                    String contenidoMensaje,
                                    String telefono,
                                    String envioMensaje,
                                    String publicarFacebook,
                                    String publicarTwitter,
                                    String fechaCreacion,
                                    String fechaRecordatorio,
                                    String estadoRecordatorio) {
        //db.insert(TABLA, NullColumnHack, ContentValues)
        super.getDb().insert(TablasDb.TablaRecordatorios.TABLE_NAME, null, generarContentValues(id,
                titulo,
                nombresOtros,
                tipoRecordatorio,
                contenidoMensaje,
                telefono,
                envioMensaje,
                publicarFacebook,
                publicarTwitter,
                fechaCreacion,
                fechaRecordatorio,
                estadoRecordatorio));
    }

    @Override
    public void actualizarRecoratorio(String id,
                                      String titulo,
                                      String nombresOtros,
                                      String tipoRecordatorio,
                                      String contenidoMensaje,
                                      String telefono,
                                      String envioMensaje,
                                      String publicarFacebook,
                                      String publicarTwitter,
                                      String fechaCreacion,
                                      String fechaRecordatorio,
                                      String estadoRecordatorio) {

        String[] args = new String[]{id};
        super.getDb().update(TablasDb.TablaRecordatorios.TABLE_NAME, generarContentValues(id,
                titulo,
                nombresOtros,
                tipoRecordatorio,
                contenidoMensaje,
                telefono,
                envioMensaje,
                publicarFacebook,
                publicarTwitter,
                fechaCreacion,
                fechaRecordatorio,
                estadoRecordatorio), TablasDb.TablaRecordatorios.CN_ID + "=?",args);
    }

    @Override
    public void eliminarRecordatorio(String id) {

        String[] args = new String[]{id};
        super.getDb().delete(TablasDb.TablaRecordatorios.TABLE_NAME, TablasDb.TablaRecordatorios.CN_ID + "=?", args);

    }

    @Override
    public void eliminarRecordatorios() {

        super.getDb().execSQL("DELETE FROM " + TablasDb.TablaRecordatorios.TABLE_NAME + ";");

    }

    @Override
    public Cursor cargarCursorRecordatorios() {
        String[] columnas = new String[]{TablasDb.TablaRecordatorios.CN_ID,
                TablasDb.TablaRecordatorios.CN_TITLE,
                TablasDb.TablaRecordatorios.CN_NAME_OTHER,
                TablasDb.TablaRecordatorios.CN_CONTENT_MESSAGE};
        //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        return super.getDb().query(TablasDb.TablaRecordatorios.TABLE_NAME, columnas, null, null, null, null, TablasDb.TablaRecordatorios.CN_ID + " DESC");
    }

    @Override
    public Cursor buscarRecordatorio(String titulo) {
        String[] columnas = new String[]{TablasDb.TablaRecordatorios.CN_ID,
                TablasDb.TablaRecordatorios.CN_TITLE,
                TablasDb.TablaRecordatorios.CN_NAME_OTHER,
                TablasDb.TablaRecordatorios.CN_CONTENT_MESSAGE};
        String[] args = new String[]{titulo};

        return super.getDb().query(TablasDb.TablaRecordatorios.TABLE_NAME, columnas, TablasDb.TablaRecordatorios.CN_TITLE + "=?", args, null, null, null);
    }

    //Comprobar si existe un registro por el valor de un dato
    @Override
    public Boolean compruebaRegistroRecordatorio(String id){
        boolean existe = true;
        Cursor resultSet = super.getDb().rawQuery("SELECT * FROM " + TablasDb.TablaRecordatorios.TABLE_NAME + " WHERE " + TablasDb.TablaRecordatorios.CN_ID + "=" + id, null);

        if(resultSet.getCount() <= 0)
            existe = false;
        else
            existe = true;

        return existe;

    }

    public List<Recordatorio> getRecordatoriosList(){
        List<Recordatorio> list = new ArrayList<>();

        Cursor cursor = cargarCursorRecordatorios();

        while (cursor.moveToNext()){
            Recordatorio recordatorio = new Recordatorio();
            recordatorio.setId(cursor.getString(0));
            recordatorio.setTitle(cursor.getString(1));
            recordatorio.setName(cursor.getString(2));
            recordatorio.setContentMessage(cursor.getString(3));

            list.add(recordatorio);
        }

        return list;
    }

    /**
     *
     * METODOS PARA LA TABLA TIPO RECORDATORIOS
     */
    private ContentValues generarContentValuesTipoRecordatorio(String id,
                                                               String iconoRecordatorio,
                                                               String tipoRecordatorio,
                                                               int proteccion,
                                                               String fechaCreacion){

        ContentValues valores = new ContentValues();
        valores.put(TablasDb.TablaTipoRecordatorio.CN_ID, id);
        valores.put(TablasDb.TablaTipoRecordatorio.CN_ICON_REMINDER, iconoRecordatorio);
        valores.put(TablasDb.TablaTipoRecordatorio.CN_REMINDER_TYPE, tipoRecordatorio);
        valores.put(TablasDb.TablaTipoRecordatorio.CN_PROTECTION, proteccion);
        valores.put(TablasDb.TablaTipoRecordatorio.CN_CREATION_DATE, fechaCreacion);

        return valores;

    }
    @Override
    public void insertarTipoRecordatorio(String id,
                                         String iconoRecordatorio,
                                         String tipoRecordatorio,
                                         int proteccion,
                                         String fechaCreacion) {
        super.getDb().insert(TablasDb.TablaTipoRecordatorio.TABLE_NAME,null, generarContentValuesTipoRecordatorio(
                id,
                iconoRecordatorio,
                tipoRecordatorio,
                proteccion,
                fechaCreacion
        ));

    }

    @Override
    public void actualizarTipoRecordatorio(String id,
                                           String iconoRecordatorio,
                                           String tipoRecordatorio,
                                           String proteccion,
                                           String fechaCreacion) {

    }

    @Override
    public void eliminarTipoRecordatorio(String id) {

    }

    @Override
    public void eliminarTipoRecordatorios() {

    }

    @Override
    public Cursor cargarCursorTipoRecordatorios() {

        String[] columnas = new String[]{TablasDb.TablaTipoRecordatorio.CN_ID,
                TablasDb.TablaTipoRecordatorio.CN_ICON_REMINDER,
                TablasDb.TablaTipoRecordatorio.CN_REMINDER_TYPE};
        //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        return super.getDb().query(TablasDb.TablaRecordatorios.TABLE_NAME, columnas, null, null, null, null, TablasDb.TablaTipoRecordatorio.CN_ID + " DESC");
    }

    @Override
    public Cursor buscarTipoRecordatorio(String tipoRecordatorio) {
        return null;
    }

    @Override
    public Boolean compruebaRegistroTipoRecordatorio(String id) {
        return null;
    }

    public List<TipoRecordatorio> getTipoRecordatoriosList(){
        List<TipoRecordatorio> list = new ArrayList<>();

        Cursor cursor = cargarCursorRecordatorios();

        while (cursor.moveToNext()){
            TipoRecordatorio recordatorio = new TipoRecordatorio();
            recordatorio.setId(cursor.getString(0));
            recordatorio.setImagen(cursor.getString(1));
            recordatorio.setTipoRecordatorio(cursor.getString(2));

            list.add(recordatorio);
        }

        return list;
    }
}
