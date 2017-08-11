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
        if (sInstance == null) {
            sInstance = new RecordatorioDataBaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private RecordatorioDataBaseHelper(Context context) {
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

        db.execSQL(Tablas.TablaRecordatorios.CREATE_TABLE);
        db.execSQL(Tablas.TablaTipoRecordatorio.CREATE_TABLE);
        db.execSQL(Tablas.TablaUsuario.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {

            String tablaRecordatorioV2 = "DROP TABLE IF EXISTS " + Tablas.TablaRecordatorios.CREATE_TABLE;
            String tablaTipoRecordatoriosV2 = "DROP TABLE IF EXISTS " + Tablas.TablaTipoRecordatorio.CREATE_TABLE;
            String tablaUsuariosV2 = "DROP TABLE IF EXISTS " + Tablas.TablaUsuario.CREATE_TABLE;

            db.execSQL(tablaRecordatorioV2);
            db.execSQL(tablaTipoRecordatoriosV2);
            db.execSQL(tablaUsuariosV2);

            onCreate(db);
        }
    }

}
