package com.a01luisrene.multirecordatorio.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by LUIS on 01/06/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MultiRecordatorio.sqlite";
    private static final int DB_SCHEME_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        super.onOpen(sqLiteDatabase);
        if (!sqLiteDatabase.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
            } else {
                sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(TablasDb.TablaRecordatorios.CREATE_TABLE);
        sqLiteDatabase.execSQL(TablasDb.TablaTipoRecordatorio.CREATE_TABLE);
        sqLiteDatabase.execSQL(TablasDb.TablaUsuario.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String tablaRecordatorioV2 = "DROP TABLE IF EXISTS " + TablasDb.TablaRecordatorios.CREATE_TABLE;
        String tablaTipoRecordatoriosV2 = "DROP TABLE IF EXISTS " + TablasDb.TablaTipoRecordatorio.CREATE_TABLE;
        String tablaUsuariosV2 = "DROP TABLE IF EXISTS " + TablasDb.TablaUsuario.CREATE_TABLE;

        sqLiteDatabase.execSQL(tablaRecordatorioV2);
        sqLiteDatabase.execSQL(tablaTipoRecordatoriosV2);
        sqLiteDatabase.execSQL(tablaUsuariosV2);

        onCreate(sqLiteDatabase);

    }
}
