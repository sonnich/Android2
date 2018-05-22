package com.example.sonnich.skemaplan;


import android.database.Cursor;
import android.os.Bundle;
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
public class UnderviserListe extends Fragment {
    public static final String TAG = "UNDERVISERLISTE";

    private Cursor cursor;
    private ListView underviserView;

    public UnderviserListe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_underviser_liste, container, false);
        initList(v);
        return v;
    }

    public void initList(View v){
        MainActivity act = (MainActivity) getActivity();
        cursor = act.getUndervisercursor();

        Log.d(TAG, cursor.getColumnCount()+"");

        underviserView = v.findViewById(R.id.underviserLW);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(act,
                android.R.layout.simple_list_item_1, cursor,
                new String[]{"NAVN"}, new int[]{android.R.id.text1}, 0);
        ;

        underviserView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.v("Adapter", "clicked + id");

                //Intent intent = new Intent(getActivity(), Note.class);
                //intent.putExtra(Note.REJSE_ID, (int) id);

                //startActivity(intent);

            }
        });

        underviserView.setAdapter(adapter);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
    }
}
