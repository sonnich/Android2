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

    public UnderviserWrapper getAllUnderviser(){
        Cursor c = db.query(MyDBHelper.UNDERVISER, new String[] {"_id", "NAVN"},
                            null, null, null, null, "NAVN");
        return new UnderviserWrapper(c);
    }

    public KlasseWrapper getAllKlasser(){
         Cursor c = db.query(MyDBHelper.KLASSE, new String[] {"_id", "NAVN", "SEMESTER"},
                            null, null, null, null, "NAVN");
        return new KlasseWrapper(c);
    }

    public KlasseWrapper getKlasseByID(int i){
        Cursor c = db.query(MyDBHelper.KLASSE, new String[] {"_id", "NAVN", "SEMESTER"},
                            "_id=?", new String[] {Integer.toString(i)},
                            null, null, "NAVN");
        return new KlasseWrapper(c);
    }

    public Cursor getFagByKlasse(int i){
        return db.query(MyDBHelper.FAG, new String[] {"_id", "NAVN", "SEMESTER", "UNDERVISER", "BLOKKE"},
                            "SEMESTER=?", new String[] {Integer.toString(i)},
                            null, null, "NAVN");
    }

    public Cursor getAllBlokkeByFagANDKlasse(int f, int k ){
        return db.query(MyDBHelper.BLOK, new String[] {"_id", "UGE", "FAG", "UNDERVISER", "BLOKKE"},
                            "SEMESTER=? AND KL", new String[] {Integer.toString(k)},
                            null, null, "NAVN");
    }


}
