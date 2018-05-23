package com.example.sonnich.skemaplan;

import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

public class UnderviserInfo extends AppCompatActivity {
    public static final String UNDERVISERID ="underviserid";
    private static final String TAG = "UnderviserInfo";
    private TextView navnTxt;
    private ListView fagLw;
    private FloatingActionButton nytFag;
    private CoordinatorLayout coord;
    private Storage storage;
    private int underviserID;
    private Cursor underviserCursor;
    private Cursor fagCursor;



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

            //underviserCursor.close();
            //fagCursor.close();



        }


    }
}
