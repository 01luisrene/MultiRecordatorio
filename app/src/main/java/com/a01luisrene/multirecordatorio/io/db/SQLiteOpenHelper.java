package com.a01luisrene.multirecordatorio.io.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static SQLiteOpenHelper sInstance;
    private static final String NOMBRE_BASE_DATOS = "multirecordatorio.sqlite";
    private static final int VERSION_ESQUEMA_BASE_DATOS = 1;

    //[Singleton Pattern] Patrón Singleton
    public static synchronized SQLiteOpenHelper getInstance(Context context) {
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
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

        db.execSQL(DataBaseManagerRecordatorios.CREAR_TABLA_RECORDATORIOS);
        db.execSQL(DataBaseManagerRecordatorios.CREAR_TABLA_CATEGORIAS);
        db.execSQL(DataBaseManagerRecordatorios.CREAR_TABLA_USUARIOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {

            String tablaRecordatorioV2 = "DROP TABLE IF EXISTS " + DataBaseManagerRecordatorios.CREAR_TABLA_RECORDATORIOS;
            String tablaTipoRecordatoriosV2 = "DROP TABLE IF EXISTS " + DataBaseManagerRecordatorios.CREAR_TABLA_CATEGORIAS;
            String tablaUsuariosV2 = "DROP TABLE IF EXISTS " + DataBaseManagerRecordatorios.CREAR_TABLA_USUARIOS;

            db.execSQL(tablaRecordatorioV2);
            db.execSQL(tablaTipoRecordatoriosV2);
            db.execSQL(tablaUsuariosV2);

            onCreate(db);
        }
    }
}
