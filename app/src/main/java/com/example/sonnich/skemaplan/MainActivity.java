package com.example.sonnich.skemaplan;

import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Cursor klassecursor;
    private Cursor undervisercursor;
    private Storage storage;
    private CoordinatorLayout coord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coord = findViewById(R.id.mainCoordinator);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        storage=Storage.getStorage(this);

        updateData();
/*        ReadDBforList reader = new ReadDBforList();
        reader.execute();*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.oversigt2);
        setSupportActionBar(toolbar);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return super.onCreateOptionsMenu(menu);
    }

    public void updateData(){
        ReadDBforList reader = new ReadDBforList();
        reader.execute();
    }

    public Cursor getKCursor(){
        return klassecursor;
    }

    public Cursor getUndervisercursor(){
        return undervisercursor;
    }

    private class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0) {

                return new UnderviserListe();
            }else{
                return new KlasseListe();
            }

        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.undervisere);
                case 1:
                    return getResources().getText(R.string.klasser);
            }
            return null;
        }
    }

    //Klasse til async DBtrafik

    private class ReadDBforList extends AsyncTask<Void, Void, Boolean>{
        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean succes = true;
            try{
                klassecursor = storage.getAllKlasser();
                undervisercursor = storage.getAllUnderviser();
            }catch(SQLException r){
                Snackbar sb = Snackbar.make(coord, "DB Unavailable", Snackbar.LENGTH_SHORT);
                sb.show();

                succes = false;

            }
            return succes;
        }
    }


}
