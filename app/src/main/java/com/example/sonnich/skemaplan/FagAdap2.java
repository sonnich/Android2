package com.example.sonnich.skemaplan;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sonnich.skemaplan.Model.Fag;
import com.example.sonnich.skemaplan.Model.FagWrapper;

public class FagAdap2 extends CursorAdapter {
    private static final String TAG = "FagAdap2";
    private LayoutInflater layoutInflater;
    private Storage storage;


    public FagAdap2(Context context, Cursor c, int flags){

        super(context, c, flags);
        Log.d(TAG, "FagAdap2: created");
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        storage = Storage.getStorage(context);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d(TAG, "newView: called");
        return layoutInflater.inflate(R.layout.fag_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d(TAG, "bindView: called");
        FagWrapper fagWrapper = (FagWrapper) cursor;
        Fag fag = fagWrapper.getFag();

        TextView tvnavn = view.findViewById(R.id.fagnavnTxt);
        tvnavn.setText(fag.getNavn());

        TextView tvNom = view.findViewById(R.id.blokNomTxt);
        tvNom.setText(fag.getBlokke());

        TextView tvReel = view.findViewById(R.id.blokReelTxt);
        tvReel.setText("0");


    }
}
