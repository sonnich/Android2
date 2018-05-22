package com.example.sonnich.skemaplan.Model;

import android.database.Cursor;
import android.database.CursorWrapper;

public class KlasseWrapper extends CursorWrapper{

    public KlasseWrapper(Cursor cursor) {
        super(cursor);
    }

    public Klasse getKlasse(){
        if(isBeforeFirst()||isAfterLast()){
            return null;
        }
        int dbID = getInt(0);
        String navn = getString(1);
        int semester = getInt(2);

        return new Klasse(dbID, navn, semester );

    }
}
