package com.example.sonnich.skemaplan;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sonnich.skemaplan.Model.FagWrapper;
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

    public Cursor getUnderviserByID(int i){
        return db.query(MyDBHelper.UNDERVISER, new String[] {"_id", "NAVN"},
                "_id=?", new String[]{Integer.toString(i)}, null, null, "NAVN");
    }

    public Cursor getFagByUnderViserID(int i){
        return db.query(MyDBHelper.FAG, new String[]{"_id", "NAVN", "SEMESTER", "UNDERVISER", "NOMBLOKKE"},
                "UNDERVISER=?", new String[]{Integer.toString(i)}, null, null, "NAVN");
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

    public Cursor getFagBySemester(int i){
        return db.query(MyDBHelper.FAG, new String[] {"_id", "NAVN", "SEMESTER", "UNDERVISER", "NOMBLOKKE"},
                "SEMESTER=?", new String[] {Integer.toString(i)},
                null, null, "NAVN");
    }

    public FagWrapper getFagWrapperBySemester(int i){

        Cursor c = db.query(MyDBHelper.FAG, new String[] {"_id", "NAVN", "SEMESTER", "UNDERVISER", "NOMBLOKKE"},
                            "SEMESTER=?", new String[] {Integer.toString(i)},
                            null, null, "NAVN");

        return new FagWrapper(c);
    }



    public Cursor getAllBlokkeBySEMAndKlasse(int s, int k ){
        return db.query(MyDBHelper.BLOK, new String[] {"_id", "UGE", "BLOKNR", "FAG", "KLASSE", "UNDERVISNINGSFRI"},
                            "SEMESTER=? AND KLASSE=?", new String[] {Integer.toString(s), Integer.toString(k)},
                            null, null, "FAG");
    }

    public UnderviserWrapper getUnderviserByFag(int u){
        Cursor c = db.query(MyDBHelper.UNDERVISER, new String[] {"_id", "NAVN"},
                "_id=?", new String[] {Integer.toString(u)}, null, null, "NAVN");
        return new UnderviserWrapper(c);
    }

    public boolean addKlasse(String navn, int semester){

        Log.d(TAG, "addKlasse: "+"Storage kald");
        boolean succes = true;
        ContentValues cv = new ContentValues();
        cv.put("NAVN", navn);
        cv.put("SEMESTER", semester);
        try {
            Log.d(TAG, "addKlasse: " +"i trycatch");
            db.insert(MyDBHelper.KLASSE, null, cv);
        }catch (SQLException e){
            succes = false;
            Log.d(TAG, "addKlasse: "+e);
        }
        return succes;
    }


}
