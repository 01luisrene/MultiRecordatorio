package com.a01luisrene.multirecordatorio.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by LUIS on 01/06/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "multirecordatorio.db";
    private static final int DB_SCHEME_VERSION = 1;
    private final Context context;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
        this.context = context;
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

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TablasDb.TablaRecordatorios.CREATE_TABLE);
        db.execSQL(TablasDb.TablaTipoRecordatorio.CREATE_TABLE);
        db.execSQL(TablasDb.TablaUsuario.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String tablaRecordatorioV2 = "DROP TABLE IF EXISTS " + TablasDb.TablaRecordatorios.CREATE_TABLE;
        String tablaTipoRecordatoriosV2 = "DROP TABLE IF EXISTS " + TablasDb.TablaTipoRecordatorio.CREATE_TABLE;
        String tablaUsuariosV2 = "DROP TABLE IF EXISTS " + TablasDb.TablaUsuario.CREATE_TABLE;

        db.execSQL(tablaRecordatorioV2);
        db.execSQL(tablaTipoRecordatoriosV2);
        db.execSQL(tablaUsuariosV2);

        onCreate(db);
    }
}
