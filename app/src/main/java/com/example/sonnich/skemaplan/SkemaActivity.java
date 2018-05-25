package com.example.sonnich.skemaplan;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class SkemaActivity extends AppCompatActivity {

    private static final String TAG = "SkemaActivity";
    public static final String UNDERID = "UNDERID";
    public static final String KLASSEID = "KLASSEID";
    private Storage storage;
    private CoordinatorLayout coord;
    private int underID = -1;
    private int klasseID = -1;
    private int statUgeNR=31;
    private int antalUger = 22;
    private boolean isKlasse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skema);

        coord = findViewById(R.id.coordSkema);

        klasseID = getIntent().getIntExtra(KLASSEID, -1);
        underID = getIntent().getIntExtra(UNDERID, -1);
        if(klasseID!=-1){
            isKlasse=true;
        }


        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.skemaPager);
        pager.setAdapter(pagerAdapter);
        storage=Storage.getStorage(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Skema");
        setSupportActionBar(toolbar);



    }



    private class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return antalUger;
        }

        @Override
        public Fragment getItem(int position) {
            if(isKlasse) {
                Fragment fragment = SkemaFragment.newInstance(position + statUgeNR, klasseID, isKlasse);
                return fragment;
            }else{
                Fragment fragment = SkemaFragment.newInstance(position+statUgeNR, underID, !isKlasse);
                return fragment;
            }


        }


        @Override
        public CharSequence getPageTitle(int position) {
            return "Uge: "+Integer.toString(position+statUgeNR);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }




}
