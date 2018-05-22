package com.example.sonnich.skemaplan;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = getView();
        klasseView = view.findViewById(R.id.klasseLW);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_klasse_liste, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity act = (MainActivity) getActivity();
        cursor = act.getKCursor();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(act,
                android.R.layout.simple_list_item_1, cursor,
                new String[]{"NAVN"}, new int[]{android.R.id.text1}, 0);
        klasseView.setAdapter(adapter);
    }


}
