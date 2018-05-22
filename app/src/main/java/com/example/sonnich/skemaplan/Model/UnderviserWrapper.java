package com.example.sonnich.skemaplan.Model;

import android.database.Cursor;
import android.database.CursorWrapper;

public class UnderviserWrapper extends CursorWrapper {
    public UnderviserWrapper(Cursor cursor) {
        super(cursor);
    }

    public Underviser getUnderviser(){
        if(isBeforeFirst()||isAfterLast()){
            return null;
        }
        int dbID = getInt(0);
        String navn = getString(1);
        return new Underviser(dbID, navn);
    }

}
