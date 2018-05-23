package com.example.sonnich.skemaplan.Model;

import android.database.Cursor;
import android.database.CursorWrapper;

public class BlokWrapper extends CursorWrapper{

    public BlokWrapper(Cursor cursor) {
        super(cursor);
    }

    public Blok getBlok(){
        if(isBeforeFirst()||isAfterLast()){
            return null;
        }
        int dbID= getInt(0);
        int uge = getInt(1);
        String dag = getString(2);
        int bloknr = getInt(3);
        int fag = getInt(4);
        int klasse = getInt(5);
        boolean fri = (getInt(6)==1);

        return new Blok(dbID,uge, dag, bloknr, fag, klasse, fri);
    }
}
