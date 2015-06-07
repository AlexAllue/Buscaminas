package com.example.allu.buscaminas;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Allu on 02/06/2015.
 */
public class QueryFrag extends ListFragment {

    private SQLiteDatabase db;
    private String value;
    private ArrayList<String> values = new ArrayList<>();
    private Cursor c,c2;
    private ArrayAdapter<String> adapter;
    private String[] args;

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


            c = db.rawQuery("SELECT alias,query FROM Partidas", null);
            if(c.moveToFirst()){
                value = c.getString(c.getColumnIndex("query"));
                values.add(value);
            }
            while (c.moveToNext()) {
                value = c.getString(c.getColumnIndex("query"));
                values.add(value);
            }
        }

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        registerForContextMenu(getListView());
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
            getActivity().finish();
        }
    }

    private String getRegistro(String query) {
        String[] args = new String[]{query};
        String[] campo = new String[]{"registro"};
        c = db.query("Partidas",campo,"query=?",args,null,null,null);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu,View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v, menuInfo);
        MenuInflater inflater=getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int pos = (int)info.position;
        c.moveToPosition(pos);

        switch (item.getItemId()) {
            case R.id.borrarMenu:
                args = new String[]{c.getString(c.getColumnIndex("query"))};
                db.delete("Partidas","query=?",args);
                values.remove(pos);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.modificarMenu:
                args = new String[]{c.getString(c.getColumnIndex("alias"))};
                c = db.rawQuery("SELECT query FROM Partidas "+ "WHERE alias=?", args);
                c.moveToFirst();
                values.removeAll(values);
                if(c.moveToFirst()){
                    value = c.getString(c.getColumnIndex("query"));
                    values.add(value);
                }
                while (c.moveToNext()) {
                    value = c.getString(c.getColumnIndex("query"));
                    values.add(value);
                }
                adapter.notifyDataSetChanged();
                return true;
            case R.id.enviarMenu:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL,"");
                String[] campo = new String[]{"registro"};
                String[] args = new String[]{c.getString(c.getColumnIndex("query"))};
                c2 = db.query("Partidas",campo,"query=?",args,null,null,null);
                c2.moveToFirst();

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, args);
                emailIntent.putExtra(Intent.EXTRA_TEXT, c2.getString(c2.getColumnIndex("registro")));
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Email "));
                return true;
            case R.id.borrarTodoMenu:
                db.delete("Partidas", null, null);
                values.clear();
                adapter.notifyDataSetChanged();
                return true;
        }


        return super.onContextItemSelected(item);
    }
}
