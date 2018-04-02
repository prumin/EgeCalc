package com.ilatis.egecalc.Data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by thymomenosgata on 26.02.18.
 */

public class DataRait extends SQLiteOpenHelper {

    public final static String TABLE_NAME = "dataR";
    public final static String id = "id";
    public final static String COLUMN_UNIVERSITY = "university";
    public final static String COLUMN_PROC = "percent";
    public final static String COLUMN_MONEY = "money";
    private Context myContext;


    //Имя файла базы данных
    private static final String DB_NAME = "app.db";
    private static String DB_PATH;

    private static final int DATABASE_VERSION = 1;

    public DataRait(Context context) {
        super(context, DB_PATH, null, DATABASE_VERSION);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DB_NAME;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void create_db(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DB_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH;

                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public SQLiteDatabase open()throws SQLException {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
