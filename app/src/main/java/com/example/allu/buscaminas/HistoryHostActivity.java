package com.example.allu.buscaminas;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Allu on 02/06/2015.
 */
public class HistoryHostActivity extends ActionBarActivity {

    private boolean atrasSalir;
    private Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.result);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff5bc610")));

        volver = (Button)findViewById(R.id.volver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryHostActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
