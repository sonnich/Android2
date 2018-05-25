package com.example.sonnich.skemaplan;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class UnderviserInfo extends AppCompatActivity {
    public static final String UNDERVISERID ="underviserid";
    private static final String TAG = "UnderviserInfo";
    private TextView navnTxt;
    private ListView fagLw;

    private CoordinatorLayout coord;
    private Storage storage;
    private int underviserID;
    private int fagToAdd;
    private Cursor underviserCursor;
    private Cursor fagCursor;
    private Spinner fagSpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_underviser_info);


        storage = Storage.getStorage(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.klasseInfo);
        setSupportActionBar(toolbar);

        coord = findViewById(R.id.underviserinfoCOord);



        underviserID = getIntent().getIntExtra(UNDERVISERID, 0);
        initData();

        FloatingActionButton addKlasse = findViewById(R.id.underviserInfoFab);
        addKlasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UnderviserInfo.this);
                View fagView = getLayoutInflater().inflate(R.layout.fag_dialogue, null);
                builder.setTitle(R.string.tilføjFag);
                fagSpinner = (Spinner) fagView.findViewById(R.id.fagSpinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(UnderviserInfo.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.fagNavne));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                fagSpinner.setAdapter(adapter);
                fagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        fagToAdd = position+1;
                        Log.d(TAG, "onItemSelected: "+ position);

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                        fagToAdd=1;

                    }
                });

                builder.setPositiveButton(R.string.tilføj, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddFagToUnderViser writer = new AddFagToUnderViser();
                        writer.execute();
                        initData();
                        dialogInterface.dismiss();

                    }
                });

                builder.setNegativeButton(getString(R.string.fortryd), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });

                builder.setView(fagView);
                builder.show();


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(underviserCursor!=null){
            underviserCursor.close();
        }
        if(fagCursor!=null){
            fagCursor.close();
        }
    }

    public void initData(){
        ReadDBForUnderviser reader = new ReadDBForUnderviser();
        reader.execute();
    }

    private class ReadDBForUnderviser extends AsyncTask<Integer, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            navnTxt = findViewById(R.id.underviserNavnTxt);
            fagLw = findViewById(R.id.underviserFagLW);
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            boolean succes = true;
            try {
                underviserCursor = storage.getUnderviserByID(underviserID);
                fagCursor = storage.getFagByUnderViserID(underviserID);
            }catch(SQLException e){
                Snackbar sb = Snackbar.make(coord, "DB Unavailable", Snackbar.LENGTH_SHORT);
                sb.show();

                succes = false;
            }
            return succes;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(underviserCursor.moveToFirst()) {
                navnTxt.setText(underviserCursor.getString(1));
            }
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,
                                                            fagCursor, new String[]{"NAVN"}, new int[]{android.R.id.text1}, 0);

            fagLw.setAdapter(adapter);

        }




    }
    private class AddFagToUnderViser extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean succes =true;
            try{
                storage.addFagToUnderViser(underviserID, fagToAdd);
            }catch (SQLException e){
                Snackbar sb = Snackbar.make(coord, "DB Unavailable", Snackbar.LENGTH_SHORT);
                sb.show();
                Log.d(TAG, "doInBackground: "+ e);

                succes = false;
            }

            return succes;
        }
    }
}
