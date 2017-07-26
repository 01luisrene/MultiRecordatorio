package com.a01luisrene.multirecordatorio.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class RecordatorioDataBaseHelper extends SQLiteOpenHelper {

    private static RecordatorioDataBaseHelper sInstance;
    private static final String DB_NAME = "multirecordatorio.db";
    private static final int DB_SCHEME_VERSION = 1;

    public static synchronized RecordatorioDataBaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new RecordatorioDataBaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    RecordatorioDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
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
    // Configura la configuración de la base de datos para elementos como el
    // soporte de claves foráneas, el registro por adelantado, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TablaRecordatorios.CREATE_TABLE);
        db.execSQL(TablaTipoRecordatorio.CREATE_TABLE);
        db.execSQL(TablaUsuario.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {

            String tablaRecordatorioV2 = "DROP TABLE IF EXISTS " + TablaRecordatorios.CREATE_TABLE;
            String tablaTipoRecordatoriosV2 = "DROP TABLE IF EXISTS " + TablaTipoRecordatorio.CREATE_TABLE;
            String tablaUsuariosV2 = "DROP TABLE IF EXISTS " + TablaUsuario.CREATE_TABLE;

            db.execSQL(tablaRecordatorioV2);
            db.execSQL(tablaTipoRecordatoriosV2);
            db.execSQL(tablaUsuariosV2);

            onCreate(db);
        }
    }

    /**
     * TABLAS
     */

    public static class TablaRecordatorios {
        /*[NOMBRE DE LA TABLA]*/
        public static final String TABLE_NAME = "recordatorios";

        /*[COLUMNAS PARA LA TABLA RECORDATORIO]*/
        public static final String CN_ID = "_id";
        public static final String CN_TITLE = "Titulo";
        public static final String CN_NAME_OTHER = "entidad_otros";
        public static final String CN_TYPE_REMINDER = "tipo_recordatorio";
        public static final String CN_CONTENT_MESSAGE = "contenido_mensaje";
        public static final String CN_PHONE = "telefono";
        public static final String CN_SEND_MESSAGE = "ennviar_mensaje";
        public static final String CN_FACEBOOK = "publicar_facebook";
        public static final String CN_TWITTER = "publicar_twitter";
        public static final String CN_CREATION_DATE = "fecha_creacion";
        public static final String CN_REMINDER_DATE = "fecha_recordatorio";
        public static final String CN_STATE = "estado_recordatorio";

        /*[CÓDIGO PARA GENERAR LA TABLA]*/
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + CN_ID + " integer PRIMARY KEY AUTOINCREMENT,"
                + CN_TITLE + " text NOT NULL,"
                + CN_NAME_OTHER + " tex NOT NULL,"
                + CN_TYPE_REMINDER + " text NOT NULL,"
                + CN_PHONE + " text NULL,"
                + CN_CONTENT_MESSAGE + " text NULL,"
                + CN_SEND_MESSAGE + " integer NOT NULL,"
                + CN_FACEBOOK + " integer NOT NULL,"
                + CN_TWITTER + " integer NOT NULL,"
                + CN_CREATION_DATE + " text NOT NULL,"
                + CN_REMINDER_DATE + " text NOT NULL,"
                + CN_STATE + " integer NOT NULL,"
                + "FOREIGN KEY("+TablaRecordatorios.CN_TYPE_REMINDER +") REFERENCES "+ TablaTipoRecordatorio.TABLE_NAME+"("+ TablaTipoRecordatorio.CN_ID+")"
                + ");";
    }

    public static class TablaTipoRecordatorio{
        /*[NOMBRE DE LA TABLA]*/
        public static final String TABLE_NAME = "categoria_recordatorios";

        /*[COLUMNAS PARA LA TABLA TIPO DE RECORDATORIO]*/
        public static final String CN_ID = "_id";
        public static final String CN_IMAGE_REMINDER = "imagen_recordatorio";
        public static final String CN_TYPE_REMINDER = "categoria_texto";
        public static final String CN_PROTECTION = "proteccion";
        public static final String CN_CREATION_DATE = "fecha_creacion";

        /*[CÓDIGO PARA GENERAR LA TABLA]*/
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + CN_ID + " integer PRIMARY KEY AUTOINCREMENT,"
                + CN_IMAGE_REMINDER + " text NULL,"
                + CN_TYPE_REMINDER + " text NOT NULL,"
                + CN_PROTECTION + " integer NOT NULL,"
                + CN_CREATION_DATE + " text NOT NULL);";
    }

    public static class TablaUsuario{
        /*[NOMBRE DE LA TABLA]*/
        public static final String TABLE_NAME = "usuarios";

        /*[COLUMNAS PARA LA TABLA USUARIO]*/
        public static final String CN_ID = "_id";
        public static final String CN_AVATAR = "avatar";
        public static final String CN_USER = "user";
        public static final String CN_PASSWORD = "password";
        public static final String CN_CONFIRM_PASSWORD = "confirmar_password";
        public static final String CN_EMAIL = "correo_electronico";
        public static final String CN_CREATION_DATE = "fecha_creacion";
        public static final String CN_STATE = "estado";

        /*[CÓDIGO PARA GENERAR LA TABLA]*/
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + CN_ID + " integer PRIMARY KEY AUTOINCREMENT,"
                + CN_AVATAR + "blob,"
                + CN_USER + " text NOT NULL,"
                + CN_PASSWORD + " text NOT NULL,"
                + CN_CONFIRM_PASSWORD + " text NOT NULL,"
                + CN_EMAIL + " text NOT NULL,"
                + CN_CREATION_DATE + " text NOT NULL,"
                + CN_STATE + " integer NOT NULL);";
    }
}
