package com.example.sonnich.skemaplan.Model;

import android.database.Cursor;
import android.database.CursorWrapper;

public class FagWrapper extends CursorWrapper {
    public FagWrapper(Cursor cursor) {
        super(cursor);
    }

    public Fag getFag(){
        if(isBeforeFirst()||isAfterLast()){
            return null;
        }
        int dbID= getInt(0);
        String navn = getString(1);
        int semester = getInt(2);
        int blokke = getInt(4);

        return new Fag(dbID,navn,semester,blokke);
    }
}
