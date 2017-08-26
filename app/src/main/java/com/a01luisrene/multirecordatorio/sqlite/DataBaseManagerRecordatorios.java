package com.a01luisrene.multirecordatorio.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.a01luisrene.multirecordatorio.modelos.Recordatorios;
import com.a01luisrene.multirecordatorio.modelos.CategoriaRecordatorios;

import java.util.ArrayList;
import java.util.List;


public class DataBaseManagerRecordatorios extends DataBaseManager {

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
    public static final String HORA_AVIDO_RECORDATORIO = "hora_aviso_recordatorio";
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
                    "%3$s TEXT NOT NULL, %4$s TEXT NULL, %5$s TEXT NOT NULL, %6$s TEXT NULL, %7$s TEXT NOT NULL, " +
                    "%8$s INTEGER NOT NULL, %9$s INTEGER NOT NULL, %10$s INTEGER NOT NULL, " +
                    "%11$s TEXT NOT NULL, %12$s TEXT NOT NULL, %13$s TEXT NOT NULL, %14$s INTEGER NOT NULL, " +
                    "FOREIGN KEY(%15$s) REFERENCES %16$s(%17$s));",
            TABLA_RECORDATORIOS,            //[1]
            ID_RECORDATORIO,                //[2]
            TITULO_RECORDATORIO,            //{3}
            ENTIDAD_OTROS_RECORDATORIO,     //[4]
            LLAVE_CATEGORIA_RECORDATORIO,   //[5]
            TELEFONO_RECORDATORIO,          //[6]
            CONTENIDO_MENSAJE_RECORDATOIO,  //[7]
            ENVIO_MENSAJE_RECORDATORIO,     //[8]
            PUBLICAR_FACEBOOK_RECORDATORIO, //[9]
            PUBLICAR_TWITTER_RECORDATORIO,  //[10]
            FECHA_CREACION_RECORDATORIO,    //[11]
            FECHA_AVISO_RECORDATORIO,       //[12]
            HORA_AVIDO_RECORDATORIO,        //[13]
            ESTADO_RECORDATORIO,            //[14]
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
                                          String nombres_otros,
                                          String categoriaRecordatorio,
                                          String contenidoMensaje,
                                          String telefono,
                                          String envioMensaje,
                                          String publicarFacebook,
                                          String publicarTwitter,
                                          String fechaCreacion,
                                          String fechaRecordatorio,
                                          String horaRecordatorio,
                                          String estadoRecordatorio){

        ContentValues valores = new ContentValues();
        valores.put(ID_RECORDATORIO, id);
        valores.put(TITULO_RECORDATORIO, titulo);
        valores.put(ENTIDAD_OTROS_RECORDATORIO, nombres_otros);
        valores.put(LLAVE_CATEGORIA_RECORDATORIO, categoriaRecordatorio);
        valores.put(TELEFONO_RECORDATORIO, telefono);
        valores.put(CONTENIDO_MENSAJE_RECORDATOIO, contenidoMensaje);
        valores.put(ENVIO_MENSAJE_RECORDATORIO, envioMensaje);
        valores.put(PUBLICAR_FACEBOOK_RECORDATORIO, publicarFacebook);
        valores.put(PUBLICAR_TWITTER_RECORDATORIO, publicarTwitter);
        valores.put(FECHA_CREACION_RECORDATORIO, fechaCreacion);
        valores.put(FECHA_AVISO_RECORDATORIO, fechaRecordatorio);
        valores.put(HORA_AVIDO_RECORDATORIO, horaRecordatorio);
        valores.put(ESTADO_RECORDATORIO, estadoRecordatorio);

        return valores;

    }

    @Override
    public void insertarRecoratorio(String id,
                                    String titulo,
                                    String nombresOtros,
                                    String categoriaRecordatorio,
                                    String contenidoMensaje,
                                    String telefono,
                                    String envioMensaje,
                                    String publicarFacebook,
                                    String publicarTwitter,
                                    String fechaCreacion,
                                    String fechaRecordatorio,
                                    String horaRecordatorio,
                                    String estadoRecordatorio) {

        getDb().beginTransaction();
        try{
            //db.insert(TABLA, NullColumnHack, ContentValues)
            super.getDb().insertOrThrow(TABLA_RECORDATORIOS, null, cvRecordatorios(id,
                    titulo,
                    nombresOtros,
                    categoriaRecordatorio,
                    contenidoMensaje,
                    telefono,
                    envioMensaje,
                    publicarFacebook,
                    publicarTwitter,
                    fechaCreacion,
                    fechaRecordatorio,
                    horaRecordatorio,
                    estadoRecordatorio));

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
                                      String categoriaRecordatorio,
                                      String contenidoMensaje,
                                      String telefono,
                                      String envioMensaje,
                                      String publicarFacebook,
                                      String publicarTwitter,
                                      String fechaCreacion,
                                      String fechaRecordatorio,
                                      String horaRecordatorio,
                                      String estadoRecordatorio) {

        String[] args = new String[]{id};
        super.getDb().update(TABLA_RECORDATORIOS, cvRecordatorios(id,
                titulo,
                nombresOtros,
                categoriaRecordatorio,
                contenidoMensaje,
                telefono,
                envioMensaje,
                publicarFacebook,
                publicarTwitter,
                fechaCreacion,
                fechaRecordatorio,
                horaRecordatorio,
                estadoRecordatorio), ID_RECORDATORIO + "=?",args);
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
                String.format("SELECT r.%1$s, r.%2$s, r.%3$s, cr.%4$s, cr.%5$s, r.%6$s, r.%7$s, r.%8$s, r.%9$s, " +
                                "r.%10$s, r.%11$s, r.%12$s FROM %13$s r, %14$s cr WHERE r.%15$s = cr.%16$s " +
                                "AND r.%17$s = %18$d ORDER BY r.%19$s DESC",
                        ID_RECORDATORIO, //[1]
                        TITULO_RECORDATORIO, //[2]
                        ENTIDAD_OTROS_RECORDATORIO, //[3]
                        RUTA_IMAGEN_CATEGORIA, //[4] Constantes de categoría
                        TITULO_CATEGORIA, //[5] Constantes de categoría
                        TELEFONO_RECORDATORIO, //[6]
                        CONTENIDO_MENSAJE_RECORDATOIO, //[7]
                        ENVIO_MENSAJE_RECORDATORIO, //[8]
                        PUBLICAR_FACEBOOK_RECORDATORIO, //[9]
                        PUBLICAR_TWITTER_RECORDATORIO, //[10]
                        FECHA_AVISO_RECORDATORIO, //[11]
                        HORA_AVIDO_RECORDATORIO, //[12]
                        TABLA_RECORDATORIOS, //[13]
                        TABLA_CATEGORIAS_RECORDATORIOS, //[14] Constantes de categoría
                        LLAVE_CATEGORIA_RECORDATORIO, //[15]
                        ID_CATEGORIA, //[16] Constantes de categoría
                        ESTADO_RECORDATORIO, //[17]
                        1, //[18]
                        ID_RECORDATORIO); //[19]

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

    //Comprobar si existe un registro por el valor de un dato
    @Override
    public Boolean compruebaRegistroRecordatorio(String id){
        boolean existe = true;
        Cursor resultSet = super.getDb().rawQuery("SELECT _id FROM " + TABLA_RECORDATORIOS + " WHERE " + ID_RECORDATORIO + "=" + id, null);

        if(resultSet.getCount() <= 0)
            existe = false;
        else
            existe = true;

        return existe;

    }

    public List<Recordatorios> getListaRecordatorios(){
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
            recordatorio.setHoraPublicacionRecordatorio(cursor.getString(11));
            list.add(recordatorio);
        }

        return list;
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
    public Cursor cargarCategoriaSpinner() {
        String[] columnas = new String[]{ID_CATEGORIA,
                RUTA_IMAGEN_CATEGORIA,
                TITULO_CATEGORIA};
        String order_by = ID_CATEGORIA + " DESC";
        return super.getDb().query(TABLA_CATEGORIAS_RECORDATORIOS, columnas, null, null, null, null, order_by);
    }

    @Override
    public Cursor cargarCursorCategoriaRecordatorios() {

        String[] columnas = new String[]{ID_CATEGORIA,
                RUTA_IMAGEN_CATEGORIA,
                TITULO_CATEGORIA};
        String order_by = ID_CATEGORIA + " DESC";
        //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        return super.getDb().query(TABLA_CATEGORIAS_RECORDATORIOS, columnas, null, null, null, null, order_by);
    }

    @Override
    public Cursor buscarCategoriasRecordatorios(String categoriaRecordatorio) {
        return null;
    }

    @Override
    public Boolean compruebaRegistroCategoriaRecordatorio(String id) {
        return null;
    }

    public List<CategoriaRecordatorios> getListaCategoriasSpinner(){
        List<CategoriaRecordatorios> list = new ArrayList<>();

        Cursor cursor = cargarCategoriaSpinner();

        while (cursor.moveToNext()){
            CategoriaRecordatorios catRecordatorio = new CategoriaRecordatorios();

            catRecordatorio.setId(cursor.getString(0));
            catRecordatorio.setImagen(cursor.getString(1));
            catRecordatorio.setCategorioRecordatorio(cursor.getString(2));

            list.add(catRecordatorio);
        }

        return list;
    }

    public List<CategoriaRecordatorios> getListaCategoriaRecordatorios(){
        List<CategoriaRecordatorios> list = new ArrayList<>();

        Cursor cursor = cargarCursorCategoriaRecordatorios();

        while (cursor.moveToNext()){
            CategoriaRecordatorios catRecordatorio = new CategoriaRecordatorios();

            catRecordatorio.setId(cursor.getString(0));
            catRecordatorio.setImagen(cursor.getString(1));
            catRecordatorio.setCategorioRecordatorio(cursor.getString(2));

            list.add(catRecordatorio);
        }

        return list;
    }
}
