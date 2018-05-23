package com.example.sonnich.skemaplan;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sonnich.skemaplan.Model.Klasse;
import com.example.sonnich.skemaplan.Model.KlasseWrapper;

public class FagAdapter extends CursorAdapter{
    private LayoutInflater inflater;
    private Storage storage;
    private int klasseID;
    private TextView tvNavn;
    private TextView tvNom;
    private TextView tvReel;

    public FagAdapter(Context context, Cursor c, int flags, int kID){
        super(context, c, flags);
        storage = Storage.getStorage(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        klasseID = kID;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fag_row, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        KlasseWrapper kw = (KlasseWrapper) cursor;
        Klasse k = kw.getKlasse();


        Cursor bloktæller = storage.getAllBlokkeBySEMAndKlasse(k.getSemester(), k.getDbID());

    }


    private class Blokcounter extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fagLW
        }

        @Override
        protected Boolean doInBackground(Integer... intergers) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
}
