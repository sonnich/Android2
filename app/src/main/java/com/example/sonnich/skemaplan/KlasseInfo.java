package com.example.sonnich.skemaplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class KlasseInfo extends AppCompatActivity {

    public static final String KLASSEID ="klasseid";
    private int klasseID;
    private Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klasse_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.klasseInfo);
        setSupportActionBar(toolbar);

        storage= Storage.getStorage(this);

        klasseID = getIntent().getIntExtra(KLASSEID,0);

    }
}
