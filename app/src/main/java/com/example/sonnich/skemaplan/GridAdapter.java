package com.example.sonnich.skemaplan;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sonnich.skemaplan.Model.Blok;

import java.util.ArrayList;



public class GridAdapter extends BaseAdapter {

    private static final String TAG = "GridAdapter";
    private Context context;
    private ArrayList<Blok> blokke;
    private Blok[] toAdapt = new Blok[20];
    private LayoutInflater inflater;
    private SkemaFragment fragment;


    public GridAdapter(Context context, ArrayList<Blok> b, SkemaFragment fragment){
        this.fragment=fragment;
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
}
