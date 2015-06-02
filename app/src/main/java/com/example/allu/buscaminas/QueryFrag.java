package com.example.allu.buscaminas;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Allu on 02/06/2015.
 */
public class QueryFrag extends ListFragment {

    private SQLiteDatabase db;
    private String value;
    private ArrayList<String> values = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PartidasSQLiteHelper padbh =
                new PartidasSQLiteHelper(getActivity(), "DBPartidas", null, 1);
        db = padbh.getReadableDatabase();
        if (savedInstanceState != null) {
            values = savedInstanceState.getStringArrayList("values");
        }else {


            Cursor c = db.rawQuery("SELECT query FROM Partidas", null);
            c.moveToFirst();
            while (c.moveToNext()) {
                value = c.getString(c.getColumnIndex("query"));
                values.add(value);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        RegistroFrag frag = (RegistroFrag) getFragmentManager().findFragmentById(R.id.frag_reg);
        if (frag != null && frag.isInLayout()) {
            frag.showText(getRegistro(item));
        }
        else {
            Intent in = new Intent(getActivity(), RegistroActivity.class);
            in.putExtra("value", getRegistro(item));
            startActivity(in);
        }
    }

    private String getRegistro(String query) {
        String[] args = new String[]{query};
        String[] campo = new String[]{"registro"};
        Cursor c = db.query("Partidas",campo,"query=?",args,null,null,null);
        c.moveToFirst();
            return c.getString(c.getColumnIndex("registro"));
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        db.close();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("values", values);
        super.onSaveInstanceState(outState);
    }
}
