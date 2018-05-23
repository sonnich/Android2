package com.example.sonnich.skemaplan;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.sonnich.skemaplan.Model.Fag;
import com.example.sonnich.skemaplan.Model.FagWrapper;

public class FagAdapter extends CursorAdapter{
    public static final String TAG = "Fagadapter";
    private LayoutInflater inflater;
    private Storage storage;
    private int klasseID;
    private TextView tvNavn;
    private TextView tvUnderviser;
    private TextView tvNom;
    private TextView tvReel;
    private Cursor blokTæller;
    private FagWrapper fagWrapper;

    public FagAdapter(Context context, Cursor c, int flags){

        super(context, c, flags);
        Log.d(TAG, "FagAdapter: Created");
        Log.d(TAG, "FagAdapter: "+ c.getColumnCount());
        boolean isStorage = storage==null;
        Log.d(TAG, "FagAdapter: " +isStorage);
        storage = Storage.getStorage(context);

        Log.d(TAG, "FagAdapter: " +isStorage);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fag_row, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Log.d(TAG, "bindView: called" );
        //KlasseWrapper kw = (KlasseWrapper) cursor;

        //Klasse k = kw.getKlasse();
        tvNavn = view.findViewById(R.id.fagnavnTxt);
        //tvUnderviser = view.findViewById(R.id.underviserTxtRow);
        tvNom = view.findViewById(R.id.blokNomTxt);
        //tvReel =view.findViewById(R.id.blokReelTxt);

   /*     FagListReader reader = new FagListReader();
        reader.execute(k.getSemester(), k.getDbID());*/


        //Cursor bloktæller = storage.getAllBlokkeBySEMAndKlasse(k.getSemester(), k.getDbID());

    }


    private class FagListReader extends AsyncTask<Integer, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(Integer... values) {
            Log.d(TAG, "doInBackground: Started");
            boolean succes = true;
            int semester = values[0];
            int klasse = values[1];
            try{
                blokTæller = storage.getAllBlokkeBySEMAndKlasse(semester, klasse);
                //fagWrapper = storage.getFagByKlasse(klasse);
                Log.d(TAG, "doInBackground: "+blokTæller.getColumnCount());
                Log.d(TAG, "doInBackground: "+fagWrapper.getColumnCount());

            }catch(SQLException s){
                succes = false;
                Log.d(TAG, "doInBackground: "+ s);
                Log.v(TAG, "GEtting doinbav");

            }
            return succes;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.d(TAG, "onPostExecute: reached"+aBoolean);

            fillRow();
        }
    }

    private void fillRow(){
        Log.d(TAG, "fillRow: fillrow called");
        if(fagWrapper.moveToFirst()){

            Fag f = fagWrapper.getFag();
            Log.d(TAG, "fillRow: " +f.getNavn());
            tvNavn.setText(f.getNavn());
            tvNom.setText(f.getBlokke()+"");
            int fordelte = 0;



        }
    }
}
