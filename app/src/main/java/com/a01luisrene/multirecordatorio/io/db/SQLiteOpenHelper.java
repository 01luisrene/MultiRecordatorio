package com.a01luisrene.multirecordatorio.io.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;


class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static SQLiteOpenHelper sInstance;
    private static final String NOMBRE_BASE_DATOS = "multirecordatorio.sqlite";
    private static final int VERSION_ESQUEMA_BASE_DATOS = 1;

    //===[CADENA DE SENTECIA SQL PARA GENERAR LA TABLA RECORDATORIOS]===//
    private static final String
            CREAR_TABLA_RECORDATORIOS = String.format("CREATE TABLE %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%3$s TEXT NOT NULL, %4$s TEXT NULL, %5$s TEXT NOT NULL, %6$s INTEGER NOT NULL, %7$s INTEGER NOT NULL, " +
                    "%8$s INTEGER NOT NULL, %9$s TEXT NULL, %10$s TEXT NOT NULL, " +
                    "%11$s TEXT NOT NULL, %12$s TEXT NOT NULL, %13$s INTEGER NOT NULL, %14$s INTEGER NOT NULL, " +
                    "FOREIGN KEY(%15$s) REFERENCES %16$s(%17$s));",
            Constantes.EntradasRecordatorio.TABLA_RECORDATORIOS,            //[1]
            Constantes.EntradasRecordatorio.ID_RECORDATORIO,                //[2]
            Constantes.EntradasRecordatorio.TITULO_RECORDATORIO,            //{3}
            Constantes.EntradasRecordatorio.ENTIDAD_OTROS_RECORDATORIO,     //[4]
            Constantes.EntradasRecordatorio.CONTENIDO_MENSAJE_RECORDATOIO,  //[5]
            Constantes.EntradasRecordatorio.PUBLICAR_FACEBOOK_RECORDATORIO, //[6]
            Constantes.EntradasRecordatorio.PUBLICAR_TWITTER_RECORDATORIO,  //[7]
            Constantes.EntradasRecordatorio.ENVIO_MENSAJE_RECORDATORIO,     //[8]
            Constantes.EntradasRecordatorio.TELEFONO_RECORDATORIO,          //[9]
            Constantes.EntradasRecordatorio.FECHA_CREACION_RECORDATORIO,    //[10]
            Constantes.EntradasRecordatorio.FECHA_AVISO_RECORDATORIO,       //[11]
            Constantes.EntradasRecordatorio.HORA_AVISO_RECORDATORIO,        //[12]
            Constantes.EntradasRecordatorio.ESTADO_RECORDATORIO,            //[13]
            Constantes.EntradasRecordatorio.LLAVE_CATEGORIA_RECORDATORIO,   //[14]
            Constantes.EntradasRecordatorio.LLAVE_CATEGORIA_RECORDATORIO,   //[15]
            Constantes.EntradasCategoria.TABLA_CATEGORIAS_RECORDATORIOS, //[16]
            Constantes.EntradasCategoria.ID_CATEGORIA);                  //[17]

    //===[CADENA DE SENTECIA SQL PARA GENERAR LA TABLA CATEGORÍAS]===//
    private static final String
            CREAR_TABLA_CATEGORIAS = String.format("CREATE TABLE %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, %3$s TEXT NULL " +
                    ", %4$s TEXT NOT NULL, %5$s INTEGER NOT NULL, %6$s TEXT NOT NULL);",
            Constantes.EntradasCategoria.TABLA_CATEGORIAS_RECORDATORIOS,
            Constantes.EntradasCategoria.ID_CATEGORIA,
            Constantes.EntradasCategoria.RUTA_IMAGEN_CATEGORIA,
            Constantes.EntradasCategoria.TITULO_CATEGORIA,
            Constantes.EntradasCategoria.PROTECCION_CATEGORIA,
            Constantes.EntradasCategoria.FECHA_CREACION_CATEGORIA);

    //===[CADENA DE SENTECIA SQL PARA GENERAR LA TABLA]]===//
    private static final String
            CREAR_TABLA_USUARIOS = String.format("CREATE TABLE %1$s (%2$s INTEGER PRIMARY KEY AUTOINCREMENT, %3$s BLOB, " +
                    "%4$s TEXT NOT NULL, %5$s TEXT NOT NULL, %6$s TEXT NOT NULL, %7$s TEXT NOT NULL, %8$s TEXT NOT NULL, " +
                    "%9$s INTEGER NOT NULL);",
            Constantes.EntradasUsuario.TABLA_USUARIOS,
            Constantes.EntradasUsuario.ID_USUARIO,
            Constantes.EntradasUsuario.AVATAR_USUARIO,
            Constantes.EntradasUsuario.NOMBRE_USUARIO,
            Constantes.EntradasUsuario.PASSWORD_USUARIO,
            Constantes.EntradasUsuario.CONFIRMAR_PASSWORD_USUARIO,
            Constantes.EntradasUsuario.EMAIL_USUARIO,
            Constantes.EntradasUsuario.FECHA_CREACION_USUARIO,
            Constantes.EntradasUsuario.ESTADO_USUARIO);

    //[Singleton Pattern] Patrón Singleton
    static synchronized SQLiteOpenHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SQLiteOpenHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private SQLiteOpenHelper(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION_ESQUEMA_BASE_DATOS);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON;");
            }
        }
    }
    // Se llama cuando se está configurando la conexión a la base de datos.
    // Configura la base de datos para elementos como el
    // soporte de claves foráneas, el registro por adelantado, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREAR_TABLA_RECORDATORIOS);
        db.execSQL(CREAR_TABLA_CATEGORIAS);
        db.execSQL(CREAR_TABLA_USUARIOS);


        //Insertar datos iniciales
        try {

            for (int i = 0; i <= DatosIniciales.datosIniciales().length; i++){
                db.insert(Constantes.EntradasCategoria.TABLA_CATEGORIAS_RECORDATORIOS,
                        null, DatosIniciales.datosIniciales()[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {

            String tablaRecordatorioV2 = "DROP TABLE IF EXISTS "
                    + Constantes.EntradasRecordatorio.TABLA_RECORDATORIOS;
            String tablaTipoRecordatoriosV2 = "DROP TABLE IF EXISTS "
                    + Constantes.EntradasCategoria.TABLA_CATEGORIAS_RECORDATORIOS;
            String tablaUsuariosV2 = "DROP TABLE IF EXISTS "
                    + Constantes.EntradasUsuario.TABLA_USUARIOS;

            db.execSQL(tablaRecordatorioV2);
            db.execSQL(tablaTipoRecordatoriosV2);
            db.execSQL(tablaUsuariosV2);

            onCreate(db);
        }
    }

}
