package com.example.allu.buscaminas;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Allu on 15/04/2015.
 */
public class HelpActivity extends ActionBarActivity {

    private boolean atrasSalir;
    private Button volver;
    private TextView text,text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        if(getApplicationContext().getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)){
           text = (TextView)findViewById(R.id.ayudaText);
           text.setTextSize(22);
            text2 = (TextView)findViewById(R.id.bienvenidoText);
            text2.setTextSize(22);
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.help);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff5bc610")));

        volver = (Button)findViewById(R.id.volver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpActivity.this, MainActivity.class);
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
