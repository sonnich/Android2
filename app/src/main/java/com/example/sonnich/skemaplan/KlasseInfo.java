package com.example.sonnich.skemaplan;

import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.sonnich.skemaplan.Model.Klasse;
import com.example.sonnich.skemaplan.Model.KlasseWrapper;

public class KlasseInfo extends AppCompatActivity {

    public static final String KLASSEID ="klasseid";
    private int klasseID;
    private Storage storage;
    private CoordinatorLayout coord;
    private KlasseWrapper klasseWrapper;
    private Cursor fagCursor;
    private Integer[] idholder = new Integer[1];
    private TextView tvNavn;
    private TextView tvSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klasse_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.klasseInfo);
        setSupportActionBar(toolbar);
        coord = findViewById(R.id.klasseCoord);

        storage= Storage.getStorage(this);

        klasseID = getIntent().getIntExtra(KLASSEID,0);

        initData();

        FloatingActionButton klasseFAB = findViewById(R.id.klasseInfoFAB);
        klasseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar sb = Snackbar.make(coord, "TODOTEST", Snackbar.LENGTH_SHORT);
                sb.show();
            }
        });





    }


    //starter Async læsning af klasse fra DB;
    public void initData(){
        ReadDBForKlasse reader = new ReadDBForKlasse();
        reader.execute(klasseID);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        klasseWrapper.close();
    }



    private class ReadDBForKlasse extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvNavn = findViewById(R.id.navnTxt);
            tvSemester = findViewById(R.id.semesterTxt);
        }

        @Override
        protected Boolean doInBackground(Integer... idholder) {
            boolean succes = true;
            try{
                klasseWrapper = storage.getKlasseByID(idholder[0]);


            }catch(SQLException r){
                Snackbar sb = Snackbar.make(coord, "DB Unavailable", Snackbar.LENGTH_SHORT);
                sb.show();

                succes = false;

            }
            return succes;
        }

        @Override
        protected void onPostExecute(Boolean succes) {
            super.onPostExecute(succes);
            initFields();


        }
    }

    //metode til indlæsning af data til UI onPostExecute
    public void initFields(){
        if(klasseWrapper.moveToFirst()) {
            Klasse k = klasseWrapper.getKlasse();
            tvNavn.setText(k.getNavn());
            tvSemester.setText(""+ k.getSemester()+".");

            //tvNavn.setText(klasseWrapper.getString(1));
            //tvSemester.setText(""+ klasseWrapper.getInt(2)+".");
        }else{
            Snackbar sb = Snackbar.make(coord, "Fejl", Snackbar.LENGTH_SHORT);
            sb.show();
        }
    }



}
