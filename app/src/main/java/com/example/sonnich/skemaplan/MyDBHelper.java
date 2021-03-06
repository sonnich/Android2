package com.example.sonnich.skemaplan;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DB_NAME = "shopApp";
    private static final int DB_VERSION = 2;

    //

    public static final String FAG ="FAG";
    public static final String UNDERVISER ="UNDERVISER";
    public static final String KLASSE ="KLASSE";
    public static final String KLASSEFAG ="KLASSEFAG";
    public static final String BLOK = "BLOK";

    public MyDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        context.deleteDatabase(this.DB_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //opretter et table til undervisere
        db.execSQL("CREATE TABLE UNDERVISER ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAVN TEXT);"
        );

        //opretter testdata til undervisere

        createTestUnderviser(db, "Allan");
        createTestUnderviser(db, "Benny");
        createTestUnderviser(db, "Carsten");
        createTestUnderviser(db, "Dennis");
        createTestUnderviser(db, "Esben");
        createTestUnderviser(db, "Frank");
        createTestUnderviser(db, "Gert");




        //opretter et table til fag
        db.execSQL("CREATE TABLE FAG ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAVN TEXT, "
                + "SEMESTER INTEGER, "
                + "UNDERVISER INTEGER, "
                + "NOMBLOKKE INTEGER,"
                + "CONSTRAINT enkelt UNIQUE(NAVN, UNDERVISER) );"
        );

        //opretter testdata til fag
        createTestFAG(db, "PRO1", 1, 1, 75);
        createTestFAG(db, "SU1", 1, 2,45);
        createTestFAG(db, "FIT1", 1, 3,30);
        createTestFAG(db, "PRO2", 2, 4,60);
        createTestFAG(db, "SU2", 2, 5,30);
        createTestFAG(db, "FIT2", 2, 5,20);
        createTestFAG(db, "DAOS", 2, 4,40);
        createTestFAG(db, "SUM", 3, 3,50);
        createTestFAG(db, "DIP", 3, 2,50);
        createTestFAG(db, "DIS", 3, 1,50);
        createTestFAG(db, "Android", 4, 2,50);
        createTestFAG(db, "C#&.Net", 4, 3,50);
        createTestFAG(db, "Spilud.", 4, 4,50);


        //Opretter tabel til klasse
        db.execSQL("CREATE TABLE KLASSE ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAVN TEXT, "
                + "SEMESTER INTEGER);"
        );

        //opretter testdata til klasse
        createTestKlasse(db, "15v", 1);
        createTestKlasse(db, "15s", 1);
        createTestKlasse(db, "16v", 2);
        createTestKlasse(db, "16s", 2);
        createTestKlasse(db, "17v", 3);
        createTestKlasse(db, "17s", 3);
        createTestKlasse(db, "18v", 4);


        db.execSQL("CREATE TABLE BLOK ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "UGE INTEGER, "
                + "DAG TEXT, "
                + "BLOKNR INTEGER,  "
                + "FAG INTEGER, "
                + "KLASSE INTEGER, "
                + "UNDERVISNINGSFRI INTEGER DEFAULT 0,"
                + "CONSTRAINT dobbelt UNIQUE(BLOKNR, UGE, FAG));"
        );

        createBlok(db, 31, "mandag", 1, 1,1,0);
        createBlok(db, 31, "mandag", 2, 1,1,0);
        createBlok(db, 31, "tirsdag", 5, 2,1, 0);
        createBlok(db, 31, "tirsdag", 6, 2,1, 0);
        createBlok(db, 31, "onsdag", 9, 1,1, 0);
        createBlok(db, 31, "onsdag", 10, 1,1, 0);
        createBlok(db, 31, "torsdag", 13, 3,1, 0);
        createBlok(db, 31, "torsdag", 14, 3,1, 0);
        createBlok(db, 31, "fredag", 17, 1,1, 0);
        createBlok(db, 31, "fredag", 18, 1,1, 0);

        createBlok(db, 32, "mandag", 1, 3,1, 0);
        createBlok(db, 32, "mandag", 2, 3,1, 0);
        createBlok(db, 32, "tirsdag", 5, 2,1, 0);
        createBlok(db, 32, "tirsdag", 6, 2,1, 0);
        createBlok(db, 32, "onsdag", 9, 1,1, 0);
        createBlok(db, 32, "onsdag", 10, 1,1, 0);
        createBlok(db, 32, "torsdag", 13, 3,1, 0);
        createBlok(db, 32, "torsdag", 14, 3,1, 0);
        createBlok(db, 32, "fredag", 17, 1,1, 0);
        createBlok(db, 32, "fredag", 18, 1,1, 0);

        createBlok(db, 32, "mandag", 1, 1,2, 0);
        createBlok(db, 32, "mandag", 2, 1,2, 0);
        createBlok(db, 32, "tirsdag", 4, 2,2, 0);
        createBlok(db, 32, "tirsdag", 5, 2,2, 0);
        createBlok(db, 32, "onsdag", 9, 3,2, 0);
        createBlok(db, 32, "onsdag", 10, 3,2, 0);
        createBlok(db, 32, "torsdag", 13, 1,2, 0);
        createBlok(db, 32, "torsdag", 14, 1,2, 0);
        createBlok(db, 32, "fredag", 17, 3,2, 0);
        createBlok(db, 32, "fredag", 18, 3,2, 0);

        createBlok(db, 33, "mandag", 1, 1,3, 0);
        createBlok(db, 33, "mandag", 2, 1,3, 0);
        createBlok(db, 33, "tirsdag", 4, 2,3, 0);
        createBlok(db, 33, "tirsdag", 5, 2,3, 0);
        createBlok(db, 33, "onsdag", 9, 3,3, 0);
        createBlok(db, 33, "onsdag", 10, 3,3, 0);
        createBlok(db, 33, "torsdag", 13, 1,3, 0);
        createBlok(db, 33, "torsdag", 14, 1,3, 0);
        createBlok(db, 33, "fredag", 17, 3,3, 0);
        createBlok(db, 33, "fredag", 18, 3,3, 0);











    }

    public void createBlok(SQLiteDatabase db, int uge, String dag, int bloknr, int fag, int klasse, int fri){
        ContentValues cv = new ContentValues();
        cv.put("UGE", uge);
        cv.put("DAG", dag);
        cv.put("BLOKNR", bloknr);
        cv.put("FAG", fag);
        cv.put("KLASSE", klasse);
        cv.put("UNDERVISNINGSFRI", fri);
        db.insert(BLOK, null, cv);

    }

    public void createTestKlasse(SQLiteDatabase db, String navn, int semester){
        ContentValues cv = new ContentValues();
        cv.put("NAVN", navn);
        cv.put("SEMESTER", semester);
        db.insert(KLASSE, null, cv);

    }


    public void createTestFAG(SQLiteDatabase db, String navn, int semester, int underviser, int blokke){
        ContentValues cv = new ContentValues();
        cv.put("NAVN", navn);
        cv.put("SEMESTER", semester);
        cv.put("NOMBLOKKE", blokke);
        cv.put("UNDERVISER", underviser);

        db.insert(FAG, null, cv);

    }

    public void createTestUnderviser(SQLiteDatabase db, String navn){
        ContentValues cv = new ContentValues();
        cv.put("NAVN", navn);
        db.insert(UNDERVISER, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }




}

