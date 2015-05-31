package com.example.akonika.finalproject;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("mylogs", "--- onCreate database ---");
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "type integer,"
                + "lat real,"
                + "long real" + ");");
        UploadPlaces(db);

    }
    private void UploadPlaces(SQLiteDatabase db)
    {
        String db_query = "INSERT INTO 'mytable' " +
                "SELECT '1' as 'id', '1' as 'type', '43.3368' as 'lat', '76.9462' as 'long'  " +
                "UNION SELECT 2, 1, 43.2404, 76.9057 "+
                "UNION SELECT 3, 1, 43.2010, 76.8924 "+
                "UNION SELECT 4, 2, 43.2633, 76.9685 "+
                "UNION SELECT 5, 2, 43.2383, 76.9190 "+
                "UNION SELECT 6, 2, 43.2587, 76.9542 "+
                "UNION SELECT 7, 3, 43.2147, 76.8957 "+
                "UNION SELECT 8, 3, 43.2018, 76.8924 "+
                "UNION SELECT 9, 3, 43.2494, 76.9470 ";
        db.compileStatement(db_query).execute();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
