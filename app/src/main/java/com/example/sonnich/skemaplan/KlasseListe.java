package com.example.sonnich.skemaplan;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class KlasseListe extends Fragment {

    private Cursor cursor;
    private ListView klasseView;


    public KlasseListe() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_klasse_liste, container, false);
        initList(v);


        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public void initList(View v){
        MainActivity act = (MainActivity) getActivity();
        cursor = act.getKCursor();

        Log.d("Cursor", cursor.getColumnCount()+"");

        klasseView = v.findViewById(R.id.klasseLW);

        FloatingActionButton addKlasse = v.findViewById(R.id.klasseListeFAB);
        addKlasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddKlasseActivity.class);
                startActivity(intent);

            }
        });

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(act,
                android.R.layout.simple_list_item_1, cursor,
                new String[]{"NAVN"}, new int[]{android.R.id.text1}, 0);

        ;
        klasseView.setAdapter(adapter);

        klasseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.v("Adapter", "clicked + id");

                Intent intent = new Intent(getActivity(), KlasseInfo.class);
                intent.putExtra(KlasseInfo.KLASSEID, (int) id);

                startActivity(intent);

            }
        });

        klasseView.setAdapter(adapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
    }
}
