package com.example.allu.buscaminas;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Allu on 24/05/2015.
 */
public class GameHostActivity extends ActionBarActivity implements ParrillaFrag.ParrillaListener {

    private boolean atrasSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff5bc610")));

        ParrillaFrag fragmentParrilla = (ParrillaFrag) getSupportFragmentManager().findFragmentById(R.id.frag_parr);
        fragmentParrilla.setParrillaListener(this);

    }






    @Override
    public void OnArticleSelected(String position) {
        boolean hayDetalle =
                (getSupportFragmentManager().findFragmentById(R.id.frag_log) != null);
        if(hayDetalle){
            LogFrag fragmentLog = (LogFrag) getSupportFragmentManager().findFragmentById(R.id.frag_log);
            fragmentLog.mostrarLog(position+"");
        }
    }

    @Override
    public void onBackPressed() {
        if (atrasSalir) {
            super.onBackPressed();
            return;
        }
        this.atrasSalir = true;
        Toast.makeText(this, R.string.atrasToast, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
