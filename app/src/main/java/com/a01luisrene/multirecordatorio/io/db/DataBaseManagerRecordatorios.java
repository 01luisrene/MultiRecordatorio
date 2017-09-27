package com.a01luisrene.multirecordatorio.io.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.modelos.Categorias;

import java.util.ArrayList;
import java.util.List;


public class DataBaseManagerRecordatorios extends DataBaseManager {

    //==================================CONSTANTES - NO DB ======================//
    private static final String VALOR_NULO = "nulo";

    public DataBaseManagerRecordatorios(Context context) {
        super(context);
    }


    @Override
    public void cerrar() {
        super.cerrar();
    }

    //
    //===========================================================================
    //                      CRUD: CREATE, READ, UPDATE, DELETE
    //===========================================================================
    //

    //Constructor usado para insertar registros
    private ContentValues cvRecordatorios(String id,
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
                                          String categoria){

        ContentValues valores = new ContentValues();
        valores.put(Constantes.EntradasRecordatorio.ID_RECORDATORIO, id);
        valores.put(Constantes.EntradasRecordatorio.TITULO_RECORDATORIO, titulo);
        valores.put(Constantes.EntradasRecordatorio.ENTIDAD_OTROS_RECORDATORIO, nombresOtros);
        valores.put(Constantes.EntradasRecordatorio.CONTENIDO_MENSAJE_RECORDATOIO, contenidoMensaje);
        valores.put(Constantes.EntradasRecordatorio.PUBLICAR_FACEBOOK_RECORDATORIO, publicarFacebook);
        valores.put(Constantes.EntradasRecordatorio.PUBLICAR_TWITTER_RECORDATORIO, publicarTwitter);
        valores.put(Constantes.EntradasRecordatorio.ENVIO_MENSAJE_RECORDATORIO, envioMensaje);
        valores.put(Constantes.EntradasRecordatorio.TELEFONO_RECORDATORIO, telefono);
        valores.put(Constantes.EntradasRecordatorio.FECHA_CREACION_RECORDATORIO, fechaCreacion);
        valores.put(Constantes.EntradasRecordatorio.FECHA_AVISO_RECORDATORIO, fechaRecordatorio);
        valores.put(Constantes.EntradasRecordatorio.HORA_AVISO_RECORDATORIO, horaRecordatorio);
        valores.put(Constantes.EntradasRecordatorio.ESTADO_RECORDATORIO, estadoRecordatorio);
        valores.put(Constantes.EntradasRecordatorio.LLAVE_CATEGORIA_RECORDATORIO, categoria);

        return valores;

    }

    @Override
    public void insertarRecoratorio(String id,
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
                                    String categoria) {

        getDb().beginTransaction();
        try{
            //db.insert(TABLA, NullColumnHack, ContentValues)
            super.getDb().insertOrThrow(Constantes.EntradasRecordatorio.TABLA_RECORDATORIOS, null, cvRecordatorios(id,
                    titulo,
                    nombresOtros,
                    contenidoMensaje,
                    publicarFacebook,
                    publicarTwitter,
                    envioMensaje,
                    telefono,
                    fechaCreacion,
                    fechaRecordatorio,
                    horaRecordatorio,
                    estadoRecordatorio,
                    categoria));

            getDb().setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            getDb().endTransaction();
        }

    }

    @Override
    public void actualizarRecoratorio(String id,
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
                                      String categoria) {

        String[] args = new String[]{id};
        getDb().beginTransaction();
        try {
            super.getDb().update(Constantes.EntradasRecordatorio.TABLA_RECORDATORIOS, cvRecordatorios(id,
                    titulo,
                    nombresOtros,
                    contenidoMensaje,
                    publicarFacebook,
                    publicarTwitter,
                    envioMensaje,
                    telefono,
                    fechaCreacion,
                    fechaRecordatorio,
                    horaRecordatorio,
                    estadoRecordatorio,
                    categoria), Constantes.EntradasRecordatorio.ID_RECORDATORIO + "=?",args);

            getDb().setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getDb().endTransaction();
        }
    }

    @Override
    public void eliminarRecordatorio(String id) {

        String[] args = new String[]{id};
        getDb().beginTransaction();
        try {
            super.getDb().delete(Constantes.EntradasRecordatorio.TABLA_RECORDATORIOS,
                    Constantes.EntradasRecordatorio.ID_RECORDATORIO + "=?", args);

            getDb().setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getDb().endTransaction();
        }

    }

    @Override
    public void eliminarRecordatorios() {

        super.getDb().execSQL("DELETE FROM " + Constantes.EntradasRecordatorio.TABLA_RECORDATORIOS + ";");

    }

    @Override
    public Cursor cargarCursorRecordatorios() {
        //Cadena que contiene la consulta de las tablas recordatorios y categoria_recoordatorios
        String sql =
                String.format("SELECT r.%1$s, r.%2$s, r.%3$s, r.%4$s, r.%5$s, r.%6$s, r.%7$s, r.%8$s, r.%9$s, r.%10$s, " +
                                "r.%11$s, cr.%12$s, cr.%13$s, cr.%14$s FROM %15$s r, %16$s cr WHERE r.%17$s = cr.%18$s " +
                                "AND r.%19$s = %20$d ORDER BY r.%21$s DESC",
                        Constantes.EntradasRecordatorio.ID_RECORDATORIO, //[1]
                        Constantes.EntradasRecordatorio.TITULO_RECORDATORIO, //[2]
                        Constantes.EntradasRecordatorio.ENTIDAD_OTROS_RECORDATORIO, //[3]
                        Constantes.EntradasRecordatorio.CONTENIDO_MENSAJE_RECORDATOIO, //[4]
                        Constantes.EntradasRecordatorio.PUBLICAR_FACEBOOK_RECORDATORIO, //[5]
                        Constantes.EntradasRecordatorio.PUBLICAR_TWITTER_RECORDATORIO, //[6]
                        Constantes.EntradasRecordatorio.ENVIO_MENSAJE_RECORDATORIO, //[7]
                        Constantes.EntradasRecordatorio.TELEFONO_RECORDATORIO, //[8]
                        Constantes.EntradasRecordatorio.FECHA_CREACION_RECORDATORIO, //[9]
                        Constantes.EntradasRecordatorio.FECHA_AVISO_RECORDATORIO, //[10]
                        Constantes.EntradasRecordatorio.HORA_AVISO_RECORDATORIO, //[11]
                        Constantes.EntradasCategoria.TITULO_CATEGORIA, //[12] Constantes de categoría
                        Constantes.EntradasCategoria.RUTA_IMAGEN_CATEGORIA, //[13] Constantes de categoría
                        Constantes.EntradasCategoria.PROTECCION_CATEGORIA, //[14] Constantes de categoría
                        Constantes.EntradasRecordatorio.TABLA_RECORDATORIOS, //[15]
                        Constantes.EntradasCategoria.TABLA_CATEGORIAS_RECORDATORIOS, //[16] Constantes de categoría
                        Constantes.EntradasRecordatorio.LLAVE_CATEGORIA_RECORDATORIO, //[17]
                        Constantes.EntradasCategoria.ID_CATEGORIA, //[18] Constantes de categoría
                        Constantes.EntradasRecordatorio.ESTADO_RECORDATORIO, //[19]
                        1, //[20]
                        Constantes.EntradasRecordatorio.ID_RECORDATORIO); //[21]

        //rawQuery(Sentencia SQL, null)
        return super.getDb().rawQuery(sql, null);
    }

    @Override
    public Cursor buscarRecordatorio(String titulo) {
        String[] columnas = new String[]{Constantes.EntradasRecordatorio.ID_RECORDATORIO,
                Constantes.EntradasRecordatorio.TITULO_RECORDATORIO,
                Constantes.EntradasRecordatorio.ENTIDAD_OTROS_RECORDATORIO,
                Constantes.EntradasRecordatorio.CONTENIDO_MENSAJE_RECORDATOIO};

        String[] args = new String[]{titulo};

        //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        return super.getDb().query(Constantes.EntradasRecordatorio.TABLA_RECORDATORIOS,
                columnas, Constantes.EntradasRecordatorio.TITULO_RECORDATORIO + "=?", args, null, null, null);
    }

    //Comprobar si el título que desea guardar  existe
    @Override
    public boolean compruebaTituloRecordatorio(String titulo){
        boolean existe = true;
        try {
            String mTitulo = tituloRecordatorioQueExiste(titulo);

            existe = mTitulo.equalsIgnoreCase(VALOR_NULO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return existe;
    }

    @Override
    public String tituloRecordatorioQueExiste(String titulo) {
        String mTitulo = VALOR_NULO;

        if (titulo.trim().length() > 0){
            Cursor cursor = super.getDb().rawQuery("SELECT "+Constantes.EntradasRecordatorio.TITULO_RECORDATORIO+" FROM "
                    +Constantes.EntradasRecordatorio.TABLA_RECORDATORIOS , null);

            if (cursor.moveToFirst()) {
                do {
                    if(cursor.getString(0).equalsIgnoreCase(titulo)){
                        mTitulo = cursor.getString(0);
                        break;
                    }

                } while(cursor.moveToNext());
            }
            cursor.close();
        }

        return mTitulo;
    }

    @Override
    public boolean compruebaTituloRecordatorioUp(String titulo, String tituloObtenido) {

        boolean existe = true;
        try {
            String mTitulo = tituloRecordatorioQueExisteUp(titulo);

            existe = mTitulo.equalsIgnoreCase(tituloObtenido) || mTitulo.equalsIgnoreCase(VALOR_NULO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return existe;
    }

    @Override
    public String tituloRecordatorioQueExisteUp(String titulo) {
        String mTitulo = VALOR_NULO;

        if (titulo.trim().length() > 0){
            Cursor cursor = super.getDb().rawQuery("SELECT "+Constantes.EntradasRecordatorio.TITULO_RECORDATORIO+" FROM "
                    +Constantes.EntradasRecordatorio.TABLA_RECORDATORIOS , null);

            if (cursor.moveToFirst()) {
                do {
                    if(cursor.getString(0).equalsIgnoreCase(titulo)){
                        mTitulo = cursor.getString(0);
                        break;
                    }

                } while(cursor.moveToNext());
            }
            cursor.close();
        }

        return mTitulo;
    }

    @Override
    public int idRecordatorioMax() {
        int max_id = 1;
        Cursor cursor = super.getDb().rawQuery("SELECT MAX("+Constantes.EntradasRecordatorio.ID_RECORDATORIO+") FROM "
                + Constantes.EntradasRecordatorio.TABLA_RECORDATORIOS, null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            max_id = cursor.getInt(0);
        }
        cursor.close();

        return max_id;
    }

    @Override
    public List<Recordatorios> getListaRecordatorios(){
        List<Recordatorios> lista = new ArrayList<>();

        Cursor cursor = cargarCursorRecordatorios();

        Recordatorios recordatorio;

        while (cursor.moveToNext()){
            recordatorio = new Recordatorios(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getInt(13)
            );

            lista.add(recordatorio);
        }
        cursor.close();

        return lista;
    }

    /**
     *
     * METODOS PARA LA TABLA CATEGORÍA RECORDATORIOS
     */
    private ContentValues cvCategoriaRecordatorio(String id,
                                          String rutaImagenRecordatorio,
                                          String categoriaRecordatorio,
                                          int proteccion,
                                          String fechaCreacion){

        ContentValues valores = new ContentValues();
        valores.put(Constantes.EntradasCategoria.ID_CATEGORIA, id);
        valores.put(Constantes.EntradasCategoria.RUTA_IMAGEN_CATEGORIA, rutaImagenRecordatorio);
        valores.put(Constantes.EntradasCategoria.TITULO_CATEGORIA, categoriaRecordatorio);
        valores.put(Constantes.EntradasCategoria.PROTECCION_CATEGORIA, proteccion);
        valores.put(Constantes.EntradasCategoria.FECHA_CREACION_CATEGORIA, fechaCreacion);

        return valores;

    }
    @Override
    public void insertarCategoriaRecordatorio(String id,
                                              String rutaImagenRecordatorio,
                                              String categoriaRecordatorio,
                                              int proteccion,
                                              String fechaCreacion) {
        getDb().beginTransaction();
        try {
            super.getDb().insert(Constantes.EntradasCategoria.TABLA_CATEGORIAS_RECORDATORIOS,null,
                    cvCategoriaRecordatorio(
                    id,
                    rutaImagenRecordatorio,
                    categoriaRecordatorio,
                    proteccion,
                    fechaCreacion
            ));
            getDb().setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getDb().endTransaction();
        }

    }

    @Override
    public void actualizarCategoriaRecordatorio(String id,
                                                String rutaImagenRecordatorio,
                                                String categoriaRecordatorio,
                                                String proteccion,
                                                String fechaCreacion) {

    }

    @Override
    public void eliminarCategoriaRecordatorio(String id) {

    }

    @Override
    public void eliminarCategoriaRecordatorios() {

    }

    @Override
    public Cursor cargarCursorCategorias() {
        String[] columnas = new String[]{Constantes.EntradasCategoria.ID_CATEGORIA,
                Constantes.EntradasCategoria.RUTA_IMAGEN_CATEGORIA,
                Constantes.EntradasCategoria.TITULO_CATEGORIA,
                Constantes.EntradasCategoria.PROTECCION_CATEGORIA};
        String order_by = Constantes.EntradasCategoria.ID_CATEGORIA + " DESC";
        return super.getDb().query(Constantes.EntradasCategoria.TABLA_CATEGORIAS_RECORDATORIOS,
                columnas, null, null, null, null, order_by);
    }

    @Override
    public Cursor buscarCategoriasRecordatorios(String categoriaRecordatorio) {
        return null;
    }

    @Override
    public boolean compruebaTituloCategoria(String titulo) {
        boolean existe = true;

        try {

            String mTitulo = tituloCategoriaQueExiste(titulo);

            existe = mTitulo.equalsIgnoreCase(VALOR_NULO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return existe;
    }

    @Override
    public String tituloCategoriaQueExiste(String tituloCategoria) {

        String mTituloCategoria = VALOR_NULO;
        if (tituloCategoria.trim().length()>0) {
            Cursor cursor = super.getDb().rawQuery("SELECT " + Constantes.EntradasCategoria.TITULO_CATEGORIA
                    + " FROM "
                    + Constantes.EntradasCategoria.TABLA_CATEGORIAS_RECORDATORIOS, null);

            if (cursor.moveToFirst()) {
                do {

                    if (cursor.getString(0).equalsIgnoreCase(tituloCategoria)) {
                        mTituloCategoria = cursor.getString(0);
                        break;
                    }

                } while (cursor.moveToNext());
            }
            cursor.close();

        }
        return mTituloCategoria;
    }

    @Override
    public boolean compruebaTituloCategoriaUp(String tituloCategoria, String tituloCategoriaObtenido) {
        boolean existe = true;
        try {
            String mTitulo = tituloCategoriaQueExisteUp(tituloCategoria);

            existe = mTitulo.equalsIgnoreCase(tituloCategoriaObtenido) || mTitulo.equalsIgnoreCase(VALOR_NULO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return existe;
    }

    @Override
    public String tituloCategoriaQueExisteUp(String tituloCategoria) {
        String mTitulo = VALOR_NULO;

        if (tituloCategoria.trim().length() > 0){
            Cursor cursor = super.getDb().rawQuery("SELECT "+Constantes.EntradasCategoria.TITULO_CATEGORIA
                    +" FROM "
                    +Constantes.EntradasCategoria.TABLA_CATEGORIAS_RECORDATORIOS , null);

            if (cursor.moveToFirst()) {
                do {
                    if(cursor.getString(0).equalsIgnoreCase(tituloCategoria)){
                        mTitulo = cursor.getString(0);
                        break;
                    }

                } while(cursor.moveToNext());
            }
            cursor.close();
        }

        return mTitulo;
    }

    @Override
    public List<Categorias> getListaCategorias(){
        List<Categorias> lista = new ArrayList<>();

        Cursor cursor = cargarCursorCategorias();

        while (cursor.moveToNext()){
            Categorias catRecordatorio = new Categorias(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3)
            );

            lista.add(catRecordatorio);
        }

        cursor.close();

        return lista;
    }
}
