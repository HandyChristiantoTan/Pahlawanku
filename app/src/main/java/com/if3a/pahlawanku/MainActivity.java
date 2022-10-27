package com.if3a.pahlawanku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvPahlawan;
    private ArrayList<ModelPahlawan> data = new ArrayList<>();

    private int mView = 0;
    static final String STATE_MODE = "MODE_VIEW";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvPahlawan = findViewById(R.id.rv_pahlawan);
        rvPahlawan.setHasFixedSize(true);

        data.addAll(DataPahlawan.ambilDataPahlawan());
        if(savedInstanceState != null)
        {
            mView = savedInstanceState.getInt(STATE_MODE);
            if(mView==0){
                tampilDataCard();
            }
            else {
                tampilDataGrid();
            }
        }
        else {
            tampilDataCard();
        }
    }


    private void tampilDataCard(){
        mView = 0;
        rvPahlawan.setLayoutManager(new LinearLayoutManager(this));
        AdapterCard adapterCard = new AdapterCard(data);
        rvPahlawan.setAdapter(adapterCard);
    }
    private void tampilDataGrid(){
        mView = 1;
        rvPahlawan.setLayoutManager(new GridLayoutManager(this, 2));
        AdapterGrid adapterGrid = new AdapterGrid(data);
        rvPahlawan.setAdapter(adapterGrid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tampilan, menu);

        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode==AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.menu_night).setTitle("Mode Day");
        } else{
            menu.findItem(R.id.menu_night).setTitle("Mode Night");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_card:
                setTitle("Mode Card");
                tampilDataCard();
                break;
            case R.id.menu_grid:
                setTitle("Mode Grid");
                tampilDataGrid();
                break;
            case R.id.menu_night:
                setTitle("Mode Night");
                int nightMode = AppCompatDelegate.getDefaultNightMode();
                if (nightMode==AppCompatDelegate.MODE_NIGHT_YES){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();
                break;
            case R.id.menu_help:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 082279248937"));
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(STATE_MODE, mView);
        super.onSaveInstanceState(outState);
    }
}