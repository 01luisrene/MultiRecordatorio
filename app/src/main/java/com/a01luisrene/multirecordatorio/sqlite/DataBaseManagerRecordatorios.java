package com.a01luisrene.multirecordatorio.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.modelos.CategoriaRecordatorios;

import java.util.ArrayList;
import java.util.List;


public class DataBaseManagerRecordatorios extends DataBaseManager {

    public DataBaseManagerRecordatorios(Context context) {
        super(context);
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
        valores.put(Tablas.TablaRecordatorios.CN_ID, id);
        valores.put(Tablas.TablaRecordatorios.CN_TITLE, titulo);
        valores.put(Tablas.TablaRecordatorios.CN_NAME_OTHER, nombres_otros);
        valores.put(Tablas.TablaRecordatorios.CN_TYPE_REMINDER, tipoRecordatorio);
        valores.put(Tablas.TablaRecordatorios.CN_PHONE, telefono);
        valores.put(Tablas.TablaRecordatorios.CN_CONTENT_MESSAGE, contenidoMensaje);
        valores.put(Tablas.TablaRecordatorios.CN_SEND_MESSAGE, envioMensaje);
        valores.put(Tablas.TablaRecordatorios.CN_FACEBOOK, publicarFacebook);
        valores.put(Tablas.TablaRecordatorios.CN_TWITTER, publicarTwitter);
        valores.put(Tablas.TablaRecordatorios.CN_CREATION_DATE, fechaCreacion);
        valores.put(Tablas.TablaRecordatorios.CN_REMINDER_DATE, fechaRecordatorio);
        valores.put(Tablas.TablaRecordatorios.CN_STATE, estadoRecordatorio);

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
        super.getDb().insert(Tablas.TablaRecordatorios.TABLE_NAME, null, generarContentValues(id,
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
        super.getDb().update(Tablas.TablaRecordatorios.TABLE_NAME, generarContentValues(id,
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
                estadoRecordatorio), Tablas.TablaRecordatorios.CN_ID + "=?",args);
    }

    @Override
    public void eliminarRecordatorio(String id) {

        String[] args = new String[]{id};
        super.getDb().delete(Tablas.TablaRecordatorios.TABLE_NAME, Tablas.TablaRecordatorios.CN_ID + "=?", args);

    }

    @Override
    public void eliminarRecordatorios() {

        super.getDb().execSQL("DELETE FROM " + Tablas.TablaRecordatorios.TABLE_NAME + ";");

    }

    @Override
    public Cursor cargarCursorRecordatorios() {
        //Cadena que contiene la consulta de las tablas recordatorios y categoria_recoordatorios
        String sql =
                String.format("SELECT r.%1$s, r.%2$s, r.%3$s, cr.%4$s, cr.%5$s, r.%6$s, r.%7$s, r.%8$s, r.%9$s, " +
                                "r.%10$s, r.%11$s FROM %12$s r, %13$s cr WHERE r.%14$s = cr.%15$s " +
                                "AND r.%16$s = %17$d ORDER BY r.%18$s DESC",
                        Tablas.TablaRecordatorios.CN_ID, //[1]
                        Tablas.TablaRecordatorios.CN_TITLE, //[2]
                        Tablas.TablaRecordatorios.CN_NAME_OTHER, //[3]
                        Tablas.TablaTipoRecordatorio.CN_IMAGE_REMINDER, //[4]
                        Tablas.TablaTipoRecordatorio.CN_TYPE_REMINDER, //[5]
                        Tablas.TablaRecordatorios.CN_PHONE, //[6]
                        Tablas.TablaRecordatorios.CN_CONTENT_MESSAGE, //[7]
                        Tablas.TablaRecordatorios.CN_SEND_MESSAGE, //[8]
                        Tablas.TablaRecordatorios.CN_FACEBOOK, //[9]
                        Tablas.TablaRecordatorios.CN_TWITTER, //[10]
                        Tablas.TablaRecordatorios.CN_REMINDER_DATE, //[11]
                        Tablas.TablaRecordatorios.TABLE_NAME, //[12]
                        Tablas.TablaTipoRecordatorio.TABLE_NAME, //[13]
                        Tablas.TablaRecordatorios.CN_TYPE_REMINDER, //[14]
                        Tablas.TablaTipoRecordatorio.CN_ID, //[15]
                        Tablas.TablaRecordatorios.CN_STATE, //[16]
                        1, //[17]
                        Tablas.TablaRecordatorios.CN_ID); //[18]

        //rawQuery(Sentencia SQL, null)
        return super.getDb().rawQuery(sql, null);
    }

    @Override
    public Cursor buscarRecordatorio(String titulo) {
        String[] columnas = new String[]{Tablas.TablaRecordatorios.CN_ID,
                Tablas.TablaRecordatorios.CN_TITLE,
                Tablas.TablaRecordatorios.CN_NAME_OTHER,
                Tablas.TablaRecordatorios.CN_CONTENT_MESSAGE};
        String[] args = new String[]{titulo};
        //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        return super.getDb().query(Tablas.TablaRecordatorios.TABLE_NAME, columnas, Tablas.TablaRecordatorios.CN_TITLE + "=?", args, null, null, null);
    }

    //Comprobar si existe un registro por el valor de un dato
    @Override
    public Boolean compruebaRegistroRecordatorio(String id){
        boolean existe = true;
        Cursor resultSet = super.getDb().rawQuery("SELECT _id FROM " + Tablas.TablaRecordatorios.TABLE_NAME + " WHERE " + Tablas.TablaRecordatorios.CN_ID + "=" + id, null);

        if(resultSet.getCount() <= 0)
            existe = false;
        else
            existe = true;

        return existe;

    }

    public List<Recordatorios> getRecordatoriosList(){
        List<Recordatorios> list = new ArrayList<>();

        Cursor cursor = cargarCursorRecordatorios();

        while (cursor.moveToNext()){
            Recordatorios recordatorio = new Recordatorios();
            recordatorio.setId(cursor.getString(0));
            recordatorio.setTitulo(cursor.getString(1));
            recordatorio.setEntidadOtros(cursor.getString(2));
            recordatorio.setImagenRecordatorio(cursor.getString(3));
            recordatorio.setCategoriaRecordatorio(cursor.getString(4));
            recordatorio.setTelefono(cursor.getString(5));
            recordatorio.setContenidoMensaje(cursor.getString(6));
            recordatorio.setEnvioMensaje(cursor.getString(7));
            recordatorio.setPublicarFacebook(cursor.getString(8));
            recordatorio.setPublicarTwitter(cursor.getString(9));
            recordatorio.setFechaPublicacionRecordatorio(cursor.getString(10));

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
        valores.put(Tablas.TablaTipoRecordatorio.CN_ID, id);
        valores.put(Tablas.TablaTipoRecordatorio.CN_IMAGE_REMINDER, iconoRecordatorio);
        valores.put(Tablas.TablaTipoRecordatorio.CN_TYPE_REMINDER, tipoRecordatorio);
        valores.put(Tablas.TablaTipoRecordatorio.CN_PROTECTION, proteccion);
        valores.put(Tablas.TablaTipoRecordatorio.CN_CREATION_DATE, fechaCreacion);

        return valores;

    }
    @Override
    public void insertarTipoRecordatorio(String id,
                                         String iconoRecordatorio,
                                         String tipoRecordatorio,
                                         int proteccion,
                                         String fechaCreacion) {
        super.getDb().insert(Tablas.TablaTipoRecordatorio.TABLE_NAME,null, generarContentValuesTipoRecordatorio(
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

        String[] columnas = new String[]{Tablas.TablaTipoRecordatorio.CN_ID,
                Tablas.TablaTipoRecordatorio.CN_IMAGE_REMINDER,
                Tablas.TablaTipoRecordatorio.CN_TYPE_REMINDER};
        //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        return super.getDb().query(Tablas.TablaRecordatorios.TABLE_NAME, columnas, null, null, null, null, Tablas.TablaTipoRecordatorio.CN_ID + " DESC");
    }

    @Override
    public Cursor buscarTipoRecordatorio(String tipoRecordatorio) {
        return null;
    }

    @Override
    public Boolean compruebaRegistroTipoRecordatorio(String id) {
        return null;
    }

    public List<CategoriaRecordatorios> getTipoRecordatoriosList(){
        List<CategoriaRecordatorios> list = new ArrayList<>();

        Cursor cursor = cargarCursorRecordatorios();

        while (cursor.moveToNext()){
            CategoriaRecordatorios recordatorio = new CategoriaRecordatorios();
            recordatorio.setId(cursor.getString(0));
            recordatorio.setImagen(cursor.getString(1));
            recordatorio.setCategorioRecordatorio(cursor.getString(2));

            list.add(recordatorio);
        }

        return list;
    }
}
