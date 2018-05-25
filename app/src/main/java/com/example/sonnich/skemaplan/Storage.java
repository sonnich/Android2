package com.example.sonnich.skemaplan;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sonnich.skemaplan.Model.Blok;
import com.example.sonnich.skemaplan.Model.BlokWrapper;
import com.example.sonnich.skemaplan.Model.FagWrapper;
import com.example.sonnich.skemaplan.Model.Klasse;
import com.example.sonnich.skemaplan.Model.KlasseWrapper;
import com.example.sonnich.skemaplan.Model.Underviser;
import com.example.sonnich.skemaplan.Model.UnderviserWrapper;

import java.util.ArrayList;

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
                            null, null, null, null, null);
        return new UnderviserWrapper(c);
    }

    public ArrayList<Underviser> getArrayAllUnderviser(){
        ArrayList<Underviser> undervisere = new ArrayList<>();
        UnderviserWrapper uw = getAllUnderviser();
        while (uw.moveToNext()){
            Underviser u = uw.getUnderviser();
            undervisere.add(u);
        }
        uw.close();
        return undervisere;
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
                            null, null, null, null, null);
        return new KlasseWrapper(c);
    }

    public ArrayList<Klasse> getArrayAllKlasser(){
        ArrayList<Klasse> klasser = new ArrayList<>();
        KlasseWrapper kw = getAllKlasser();
        while (kw.moveToNext()){
            Klasse k = kw.getKlasse();
            klasser.add(k);
        }
        kw.close();
        return klasser;
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
        Cursor c = db.query(MyDBHelper.BLOK, new String[] {"_id", "UGE", "BLOKNR", "FAG", "KLASSE", "UNDERVISNINGSFRI"},
                "SEMESTER=? AND KLASSE=?", new String[] {Integer.toString(s), Integer.toString(k)},
                null, null, "FAG");
        if(c.moveToFirst()){
            Log.d(TAG, "getAllBlokkeBySEMAndKlasse: "+c.getInt(1));
        }
        return c;
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

    public boolean addFagToUnderViser(int u, int f){
        boolean succes = true;
        ContentValues cv = new ContentValues();
        cv.put("UNDERVISER", u);
        try {
            db.update(MyDBHelper.FAG, cv, "_id=?", new String[]{Integer.toString(f)});
        }catch (SQLException e){
            Log.d(TAG, "addFagToUnderViser: "+ e);
            succes=false;
        }
        return succes;

    }

    public ArrayList<Blok> getBlokForKlassePerUge(int uge, int kID){
        Log.d(TAG, "getBlokForKlassePerUge: kaldt med uge "+uge+" kId "+kID);
        ArrayList<Blok> blokke = new ArrayList<>();
        Cursor c = db.query(MyDBHelper.BLOK, new String[] {"_id", "UGE", "DAG", "BLOKNR", "FAG", "KLASSE", "UNDERVISNINGSFRI"},
                "UGE=? AND KLASSE=?", new String[] {Integer.toString(uge), Integer.toString(kID)},
                null, null, "BLOKNR");
        Log.d(TAG, "getBlokForKlassePerUge: c "+c.getCount());


        BlokWrapper bw = new BlokWrapper(c);
        Log.d(TAG, "getBlokForKlassePerUge: "+bw.getCount());
        while(bw.moveToNext()){
            //Log.d(TAG, "getBlokForKlassePerUge: "+"enter here");

            Blok blok = bw.getBlok();
            //Log.d(TAG, "getBlokForKlassePerUge: blok= "+blok.getDbID());


            String fagnavn = getFagnavnByID(blok.getFag());
            blok.setFagnavn(fagnavn);

            String klassenavn = getKlassenavnByID(blok.getKlasse());
            blok.setKlasseNavn(klassenavn);


            UnderviserWrapper uw = storage.getUnderviserByFag(blok.getFag());
            if(uw.moveToFirst()){
                Underviser u = uw.getUnderviser();
                String underviserNavn = u.getNavn();
                blok.setUndervisernavn(underviserNavn);
            }

            blokke.add(blok);
            uw.close();
            //Log.d(TAG, "getBlokForKlassePerUge: antal "+blokke.size());
        }
        bw.close();

        Log.d(TAG, "getBlokForKlassePerUge: antal "+blokke.size());
        return blokke;
    }

    public String getFagnavnByID(int i){
        String s =null;
        Cursor c = db.query(MyDBHelper.FAG, new String[]{"NAVN"}, "_id=?", new String[]{Integer.toString(i)}, null, null, "NAVN");
        if(c.moveToFirst()){
            s = c.getString(0);
        }
        c.close();
        return s;
    }


    public String getKlassenavnByID(int i){
        String s =null;
        Cursor c = db.query(MyDBHelper.KLASSE, new String[]{"NAVN"}, "_id=?", new String[]{Integer.toString(i)}, null, null, "NAVN");
        if(c.moveToFirst()){
            s = c.getString(0);
        }
        c.close();
        return s;
    }





}
