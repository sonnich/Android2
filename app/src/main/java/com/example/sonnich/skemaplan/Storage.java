package com.example.sonnich.skemaplan;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sonnich.skemaplan.Model.KlasseWrapper;
import com.example.sonnich.skemaplan.Model.UnderviserWrapper;

public class Storage {
    public static final String TAG ="Storage";

    private SQLiteDatabase db;
    private static Storage storage;

    public Storage(Context context) {
        db = new MyDBHelper(context).getWritableDatabase();


    }

    public static Storage getStorage(Context context){

        if(storage==null){
            storage = new Storage(context);
            Log.v(TAG, "Storage Constructed");


        }
        return storage;
    }

    public Cursor getAllTest(){
        return db.query("TEST", new String[] {"_id", "NAME", "INFO", "LAT", "LONGT"}, null, null, null, null, "NAME");
    }

    public UnderviserWrapper getAllUnderviser(){
        return (UnderviserWrapper) db.query(MyDBHelper.UNDERVISER, new String[] {"_id", "NAVN"}, null, null, null, null, "NAVN");
    }

    public KlasseWrapper getAllKlasser(){
        return (KlasseWrapper) db.query(MyDBHelper.KLASSE, new String[] {"_id", "NAVN", "SEMESTER"}, null, null, null, null, "NAVN");
    }


}
