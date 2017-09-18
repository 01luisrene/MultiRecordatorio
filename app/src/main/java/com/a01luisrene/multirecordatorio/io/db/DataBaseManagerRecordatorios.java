package com.a01luisrene.multirecordatorio.io.db;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.modelos.Categorias;

import java.util.ArrayList;
import java.util.List;


public class DataBaseManagerRecordatorios extends DataBaseManager {

    //==================================CONSTANTES - NO DB ======================//
    public static final String VALOR_NULO = "nulo";

    //
    //===========================================================================
    //  CONSTANTES PARA LA GENERACIÓN DE BASE DE DATOS MULTI RECORDATORIOS
    //===========================================================================
    //

    /*[NOMBRE DE LAS TABLAS]*/
    private static final String TABLA_RECORDATORIOS = "recordatorios";
    private static final String TABLA_CATEGORIAS_RECORDATORIOS = "categoria_recordatorios";
    private static final String TABLA_USUARIOS = "usuarios";

    /*[COLUMNAS PARA LA TABLA RECORDATORIO]*/
    private static final String ID_RECORDATORIO = "_id";
    private static final String TITULO_RECORDATORIO = "titulo";
    private static final String ENTIDAD_OTROS_RECORDATORIO = "entidad_otros";
    private static final String LLAVE_CATEGORIA_RECORDATORIO = "id_categoria";
    private static final String CONTENIDO_MENSAJE_RECORDATOIO = "contenido_mensaje";
    private static final String TELEFONO_RECORDATORIO = "numero_smartphone";
    private static final String ENVIO_MENSAJE_RECORDATORIO = "ennviar_mensaje";
    private static final String PUBLICAR_FACEBOOK_RECORDATORIO = "publicar_facebook";
    private static final String PUBLICAR_TWITTER_RECORDATORIO = "publicar_twitter";
    private static final String FECHA_CREACION_RECORDATORIO = "fecha_creacion";
    private static final String FECHA_AVISO_RECORDATORIO = "fecha_aviso_recordatorio";
    public static final String HORA_AVISO_RECORDATORIO = "hora_aviso_recordatorio";
    private static final String ESTADO_RECORDATORIO = "estado_recordatorio";

    /*[COLUMNAS PARA LA TABLA CATEGORIAS DE RECORDATORIOS]*/
    private static final String ID_CATEGORIA = "_id";
    private static final String RUTA_IMAGEN_CATEGORIA = "ruta_imagen_categoria";
    public static final String TITULO_CATEGORIA = "titulo_categoria";
    private static final String PROTECCION_CATEGORIA = "proteccion";
    private static final String FECHA_CREACION_CATEGORIA = "fecha_creacion";

    /*[COLUMNAS PARA LA TABLA USUARIO]*/
    private static final String ID_USUARIO = "_id";
    private static final String AVATAR_USUARIO = "avatar";
    private static final String NOMBRE_USUARIO = "user";
    private static final String PASSWORD_USUARIO = "password";
    private static final String CONFIRMAR_PASSWORD_USUARIO = "confirmar_password";
    private static final String EMAIL_USUARIO = "correo_electronico";
    private static final String FECHA_CREACION_USUARIO = "fecha_creacion";
    private static final String ESTADO_USUARIO = "estado";

    //===[CADENA DE SENTECIA SQL PARA GENERAR LA TABLA RECORDATORIOS]===//
    public static final String
            CREAR_TABLA_RECORDATORIOS = String.format("CREATE TABLE %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%3$s TEXT NOT NULL, %4$s TEXT NULL, %5$s TEXT NOT NULL, %6$s INTEGER NOT NULL, %7$s INTEGER NOT NULL, " +
                    "%8$s INTEGER NOT NULL, %9$s TEXT NULL, %10$s TEXT NOT NULL, " +
                    "%11$s TEXT NOT NULL, %12$s TEXT NOT NULL, %13$s INTEGER NOT NULL, %14$s INTEGER NOT NULL, " +
                    "FOREIGN KEY(%15$s) REFERENCES %16$s(%17$s));",
            TABLA_RECORDATORIOS,            //[1]
            ID_RECORDATORIO,                //[2]
            TITULO_RECORDATORIO,            //{3}
            ENTIDAD_OTROS_RECORDATORIO,     //[4]
            CONTENIDO_MENSAJE_RECORDATOIO,  //[5]
            PUBLICAR_FACEBOOK_RECORDATORIO, //[6]
            PUBLICAR_TWITTER_RECORDATORIO,  //[7]
            ENVIO_MENSAJE_RECORDATORIO,     //[8]
            TELEFONO_RECORDATORIO,          //[9]
            FECHA_CREACION_RECORDATORIO,    //[10]
            FECHA_AVISO_RECORDATORIO,       //[11]
            HORA_AVISO_RECORDATORIO,        //[12]
            ESTADO_RECORDATORIO,            //[13]
            LLAVE_CATEGORIA_RECORDATORIO,   //[14]
            LLAVE_CATEGORIA_RECORDATORIO,   //[15]
            TABLA_CATEGORIAS_RECORDATORIOS, //[16]
            ID_CATEGORIA);                  //[17]

    //===[CADENA DE SENTECIA SQL PARA GENERAR LA TABLA CATEGORÍAS]===//
    public static final String
            CREAR_TABLA_CATEGORIAS = String.format("CREATE TABLE %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, %3$s TEXT NULL " +
                    ", %4$s TEXT NOT NULL, %5$s INTEGER NOT NULL, %6$s TEXT NOT NULL);",
            TABLA_CATEGORIAS_RECORDATORIOS,
            ID_CATEGORIA,
            RUTA_IMAGEN_CATEGORIA,
            TITULO_CATEGORIA,
            PROTECCION_CATEGORIA,
            FECHA_CREACION_CATEGORIA);

    //===[CADENA DE SENTECIA SQL PARA GENERAR LA TABLA]]===//
    public static final String
            CREAR_TABLA_USUARIOS = String.format("CREATE TABLE %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, %3$s BLOB, " +
                    "%4$s TEXT NOT NULL, %5$s TEXT NOT NULL, %6$s TEXT NOT NULL, %7$s TEXT NOT NULL, %8$s TEXT NOT NULL, " +
                    "%9$s INTEGER NOT NULL);",
            TABLA_USUARIOS,
            ID_USUARIO,
            AVATAR_USUARIO,
            NOMBRE_USUARIO,
            PASSWORD_USUARIO,
            CONFIRMAR_PASSWORD_USUARIO,
            EMAIL_USUARIO,
            FECHA_CREACION_USUARIO,
            ESTADO_USUARIO);

    //
    //===========================================================================
    //                         CONSTRUCTOR DATA BASE SQLITE
    //===========================================================================
    //

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

    //===SPINNER===//


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
        valores.put(ID_RECORDATORIO, id);
        valores.put(TITULO_RECORDATORIO, titulo);
        valores.put(ENTIDAD_OTROS_RECORDATORIO, nombresOtros);
        valores.put(CONTENIDO_MENSAJE_RECORDATOIO, contenidoMensaje);
        valores.put(PUBLICAR_FACEBOOK_RECORDATORIO, publicarFacebook);
        valores.put(PUBLICAR_TWITTER_RECORDATORIO, publicarTwitter);
        valores.put(ENVIO_MENSAJE_RECORDATORIO, envioMensaje);
        valores.put(TELEFONO_RECORDATORIO, telefono);
        valores.put(FECHA_CREACION_RECORDATORIO, fechaCreacion);
        valores.put(FECHA_AVISO_RECORDATORIO, fechaRecordatorio);
        valores.put(HORA_AVISO_RECORDATORIO, horaRecordatorio);
        valores.put(ESTADO_RECORDATORIO, estadoRecordatorio);
        valores.put(LLAVE_CATEGORIA_RECORDATORIO, categoria);

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
            super.getDb().insertOrThrow(TABLA_RECORDATORIOS, null, cvRecordatorios(id,
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
        super.getDb().update(TABLA_RECORDATORIOS, cvRecordatorios(id,
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
                categoria), ID_RECORDATORIO + "=?",args);
    }

    @Override
    public void eliminarRecordatorio(String id) {

        String[] args = new String[]{id};
        super.getDb().delete(TABLA_RECORDATORIOS, ID_RECORDATORIO + "=?", args);

    }

    @Override
    public void eliminarRecordatorios() {

        super.getDb().execSQL("DELETE FROM " + TABLA_RECORDATORIOS + ";");

    }

    @Override
    public Cursor cargarCursorRecordatorios() {
        //Cadena que contiene la consulta de las tablas recordatorios y categoria_recoordatorios
        String sql =
                String.format("SELECT r.%1$s, r.%2$s, r.%3$s, r.%4$s, r.%5$s, r.%6$s, r.%7$s, r.%8$s, r.%9$s, r.%10$s, " +
                                "r.%11$s, cr.%12$s, cr.%13$s FROM %14$s r, %15$s cr WHERE r.%16$s = cr.%17$s " +
                                "AND r.%18$s = %19$d ORDER BY r.%20$s DESC",
                        ID_RECORDATORIO, //[1]
                        TITULO_RECORDATORIO, //[2]
                        ENTIDAD_OTROS_RECORDATORIO, //[3]
                        CONTENIDO_MENSAJE_RECORDATOIO, //[4]
                        PUBLICAR_FACEBOOK_RECORDATORIO, //[5]
                        PUBLICAR_TWITTER_RECORDATORIO, //[6]
                        ENVIO_MENSAJE_RECORDATORIO, //[7]
                        TELEFONO_RECORDATORIO, //[8]
                        FECHA_CREACION_RECORDATORIO, //[9]
                        FECHA_AVISO_RECORDATORIO, //[10]
                        HORA_AVISO_RECORDATORIO, //[11]
                        TITULO_CATEGORIA, //[12] Constantes de categoría
                        RUTA_IMAGEN_CATEGORIA, //[13] Constantes de categoría
                        TABLA_RECORDATORIOS, //[14]
                        TABLA_CATEGORIAS_RECORDATORIOS, //[15] Constantes de categoría
                        LLAVE_CATEGORIA_RECORDATORIO, //[16]
                        ID_CATEGORIA, //[17] Constantes de categoría
                        ESTADO_RECORDATORIO, //[18]
                        1, //[19]
                        ID_RECORDATORIO); //[20]

        //rawQuery(Sentencia SQL, null)
        return super.getDb().rawQuery(sql, null);
    }

    @Override
    public Cursor buscarRecordatorio(String titulo) {
        String[] columnas = new String[]{ID_RECORDATORIO,
                TITULO_RECORDATORIO,
                ENTIDAD_OTROS_RECORDATORIO,
                CONTENIDO_MENSAJE_RECORDATOIO};

        String[] args = new String[]{titulo};

        //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        return super.getDb().query(TABLA_RECORDATORIOS, columnas, TITULO_RECORDATORIO + "=?", args, null, null, null);
    }

    //Comprobar si el título que desea guardar  existe
    @Override
    public boolean compruebaTituloRecordatorio(String titulo){
        boolean existe = true;
        try {
            String mTitulo = tituloRecordatorioQueExiste(titulo);

            existe = mTitulo.equals(VALOR_NULO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return existe;
    }

    @Override
    public String tituloRecordatorioQueExiste(String titulo) {
        String mTitulo = VALOR_NULO;

        if (titulo.trim().length() > 0){
            Cursor cursor = super.getDb().rawQuery("SELECT "+TITULO_RECORDATORIO+" FROM "
                    +TABLA_RECORDATORIOS , null);

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

            existe = mTitulo.equalsIgnoreCase(tituloObtenido) || mTitulo.equals(VALOR_NULO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return existe;
    }

    @Override
    public String tituloRecordatorioQueExisteUp(String titulo) {
        String mTitulo = VALOR_NULO;

        if (titulo.trim().length() > 0){
            Cursor cursor = super.getDb().rawQuery("SELECT "+TITULO_RECORDATORIO+" FROM "
                    +TABLA_RECORDATORIOS , null);

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
        Cursor cursor = super.getDb().rawQuery("SELECT MAX("+ID_RECORDATORIO+") FROM " + TABLA_RECORDATORIOS, null);

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
                    cursor.getString(12)

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
        valores.put(ID_CATEGORIA, id);
        valores.put(RUTA_IMAGEN_CATEGORIA, rutaImagenRecordatorio);
        valores.put(TITULO_CATEGORIA, categoriaRecordatorio);
        valores.put(PROTECCION_CATEGORIA, proteccion);
        valores.put(FECHA_CREACION_CATEGORIA, fechaCreacion);

        return valores;

    }
    @Override
    public void insertarCategoriaRecordatorio(String id,
                                              String rutaImagenRecordatorio,
                                              String categoriaRecordatorio,
                                              int proteccion,
                                              String fechaCreacion) {
        super.getDb().insert(TABLA_CATEGORIAS_RECORDATORIOS,null, cvCategoriaRecordatorio(
                id,
                rutaImagenRecordatorio,
                categoriaRecordatorio,
                proteccion,
                fechaCreacion
        ));

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
        String[] columnas = new String[]{ID_CATEGORIA,
                RUTA_IMAGEN_CATEGORIA,
                TITULO_CATEGORIA};
        String order_by = ID_CATEGORIA + " DESC";
        return super.getDb().query(TABLA_CATEGORIAS_RECORDATORIOS, columnas, null, null, null, null, order_by);
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

            existe = mTitulo.equals(VALOR_NULO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return existe;
    }

    @Override
    public String tituloCategoriaQueExiste(String tituloCategoria) {

        String mTituloCategoria = VALOR_NULO;
        if (tituloCategoria.trim().length()>0) {
            Cursor cursor = super.getDb().rawQuery("SELECT " + TITULO_CATEGORIA + " FROM "
                    + TABLA_CATEGORIAS_RECORDATORIOS, null);

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

            existe = mTitulo.equalsIgnoreCase(tituloCategoriaObtenido) || mTitulo.equals(VALOR_NULO);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return existe;
    }

    @Override
    public String tituloCategoriaQueExisteUp(String tituloCategoria) {
        String mTitulo = VALOR_NULO;

        if (tituloCategoria.trim().length() > 0){
            Cursor cursor = super.getDb().rawQuery("SELECT "+TITULO_CATEGORIA+" FROM "
                    +TABLA_CATEGORIAS_RECORDATORIOS , null);

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
            Categorias catRecordatorio = new Categorias();

            catRecordatorio.setId(cursor.getString(0));
            catRecordatorio.setImagen(cursor.getString(1));
            catRecordatorio.setCategorioRecordatorio(cursor.getString(2));

            lista.add(catRecordatorio);
        }

        cursor.close();

        return lista;
    }
}
