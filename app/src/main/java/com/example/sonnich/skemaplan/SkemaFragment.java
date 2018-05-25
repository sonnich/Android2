package com.example.sonnich.skemaplan;


import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sonnich.skemaplan.Model.Blok;
import com.example.sonnich.skemaplan.Model.Klasse;
import com.example.sonnich.skemaplan.Model.Underviser;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SkemaFragment extends Fragment {

    private static final String TAG = "SkemaFragment";
    private int id;
    private int ugeNr;
    private boolean isKlasse;
    private Storage storage;
    private CoordinatorLayout coord;
    private ArrayList<Blok> blokke;
    private ArrayList<Underviser> undervisere;
    private ArrayList<Klasse> klasser;
    private GridView gridView;
    private String[] klasseNavne;
    private String[] underNavne;



    public SkemaFragment() {
        // Required empty public constructor
    }

    public static SkemaFragment newInstance(int uge, int id, boolean isKlasse) {
        Bundle bundle = new Bundle();
        bundle.putInt("uge", uge);
        bundle.putInt("id", id);
        bundle.putBoolean("isKlasse", isKlasse);

        SkemaFragment fragment = new SkemaFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            id =  bundle.getInt("id");
            ugeNr = bundle.getInt("uge");
            isKlasse=bundle.getBoolean("isKlasse");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_skema, container, false);

        readBundle(getArguments());
        storage = Storage.getStorage(getActivity());

        coord = v.findViewById(R.id.coordSkemaFragment);
        ReadBlokkeForUGEklasse reader = new ReadBlokkeForUGEklasse();
        reader.execute(ugeNr, id);

        TextView ugeTxt = v.findViewById(R.id.ugenavnTxt);
        ugeTxt.setText("UGE: "+ugeNr);
        //Log.d(TAG, "onCreateView: "+blokke.size());
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gridView = v.findViewById(R.id.skemaGrid);


        return v;
    }


    private class ReadBlokkeForUGEklasse extends AsyncTask<Integer, Void, Boolean> {
      @Override
        protected Boolean doInBackground(Integer... integers) {
            boolean succes = true;

                int uge = integers[0];
                int kId = integers[1];

            try{

                blokke = storage.getBlokForKlassePerUge(uge, kId);
                Log.d(TAG, "doInBackground: "+blokke.size());


            }catch(android.database.SQLException r){
                Log.d(TAG, "doInBackground: " +r);
                succes = false;
            }
            return succes;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            GridAdapter gridAdapter = new GridAdapter(getContext(), blokke );
            gridView.setAdapter(gridAdapter);
        }
    }



    private class getKlasseOGUnderviserNavne extends AsyncTask<Integer, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Integer... integers) {
            boolean succes = true;
            try{
                undervisere = storage.getArrayAllUnderviser();
                klasser = storage.getArrayAllKlasser();
            }catch (SQLException e){
                succes = false;

                Log.d(TAG, "doInBackground: Exception "+e);
                Snackbar sb = Snackbar.make(coord, "DB Unavailable", Snackbar.LENGTH_SHORT);
                sb.show();
            }
            return succes;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            klasseNavne = new String[klasser.size()];
            underNavne = new String[undervisere.size()];

            for(int i = 0;i<undervisere.size();i++){
                underNavne[i]=undervisere.get(i).getNavn();

            }
            for(int i =0; i<klasser.size()){
                klasseNavne[i]=klasser.get(i).getNavn();
            }
        }
    }



    //Klasse til at styre hvordan blokkene bliver vist
    private class GridAdapter extends BaseAdapter {

        private static final String TAG = "GridAdapter";
        private Context context;
        private ArrayList<Blok> blokke;
        private Blok[] toAdapt = new Blok[20];
        private LayoutInflater inflater;



        public GridAdapter(Context context, ArrayList<Blok> b){

            this.blokke = b;
            this.context =context;
            for(int i = 0; i<20; i++){
                Blok empty = new Blok(i+1 );
                toAdapt[i]=empty;

            }

            for(int i=0; i<blokke.size(); i++){
                Blok blok = blokke.get(i);
                int newPos = blok.getBlokNr();
                toAdapt[newPos-1]= blok;
            }

        }


        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int i) {
            return toAdapt[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final int pos = i;
            View grid = view;
            if(view ==null){
                inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                grid = inflater.inflate(R.layout.blok,null);
            }

            LinearLayout linearLayout = grid.findViewById(R.id.bloktemplate);
            TextView tvFag= grid.findViewById(R.id.blokfagTxt);
            TextView tvKlasse = grid.findViewById(R.id.blokklasseTxt);
            TextView tvUnderviser = grid.findViewById(R.id.blokUnderviserTxt);

            Blok blok = toAdapt[i];

            if(blok.isUndervisningsfri()==true) {
                linearLayout.setBackgroundColor(Color.parseColor("#e6efed"));
            }else if(blok.getFagnavn()!=null){
                linearLayout.setBackgroundColor(Color.parseColor("#b8cdef"));
            }

            tvFag.setText(blok.getFagnavn());
            tvKlasse.setText(blok.getKlasseNavn());
            tvUnderviser.setText(blok.getUndervisernavn());
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: "+pos);
                }
            });

            return grid;
        }

        //metode til at vise en dialog når en blok bliver trykker på.

        private void showDialogue(){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View dialogue = getLayoutInflater().inflate(R.layout.blok_dialogue,null);
            builder.setTitle(R.string.vaelg_handling);
            Spinner optionSpinner = dialogue.findViewById(R.id.blokOptionsSpinner);
            Spinner fag = dialogue.findViewById(R.id.blokFagSpinner);
            Spinner klasseSpinner = dialogue.findViewById(R.id.blokKlasseSpinner);



            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.blokOptions));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            optionSpinner.setAdapter(adapter);
            optionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        }
    }




}
