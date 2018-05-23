package com.example.sonnich.skemaplan;

import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddKlasseActivity extends AppCompatActivity {
    private static final String TAG = "AddKlasseActivity";
    private Storage storage;
    private Spinner spinner;
    private EditText navneEdit;
    private int semester;
    private String navn;
    private CoordinatorLayout coord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_klasse);
        storage = Storage.getStorage(this);

        coord = findViewById(R.id.coordAddklasse);

        //setupToolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.tilføjklasse);
        setSupportActionBar(toolbar);

        //setup spinner
        spinner = findViewById(R.id.semSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.semestre, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester = position+1;
                Log.d(TAG, "onItemSelected: "+ position);

            }
            public void onNothingSelected(AdapterView<?> parent) {
                semester=1;

            }
        });

        //setup FAB

        FloatingActionButton addKlasse = findViewById(R.id.AddFAB);
        addKlasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KlasseToDB writer = new KlasseToDB();
                writer.execute();



            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_home, menu);


        return super.onCreateOptionsMenu(menu);
    }
    //Klasse itl Async tilgang til DB;
    private class KlasseToDB extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            navneEdit = findViewById(R.id.opretKlassenavnTxt);
            if(navneEdit.getText().length()<1){

                Snackbar sb = Snackbar.make(coord, "Klassen Skal have et navn", Snackbar.LENGTH_SHORT);
                sb.show();
                this.cancel(true);

            }else{
                navn = navneEdit.getText().toString();
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean succes = true;
            try{
                Log.d(TAG, "doInBackground:  præ storage kald navn = "+ navn +" sem = "+ semester );
                storage.addKlasse(navn, semester);
                Log.d(TAG, "doInBackground: post storage kald");

            }catch (SQLException e){
                succes = false;
                Snackbar sb = Snackbar.make(coord, "DB Unavailable", Snackbar.LENGTH_SHORT);
                sb.show();

                succes = false;
            }

            return succes;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            finish();
        }
    }


}
