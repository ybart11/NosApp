package com.example.nosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements FavoritesAdapter.RecyclerViewInterface, SettingsDialogFragment.SettingsDialogListener {

    private RecyclerView recyclerView;
    private FavoritesAdapter favsAdapter;
    ArrayList<Favorites> favsList;
    AzureSQL az;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        randomButton();
        homeButton();
        favoritesButton();
        settingsButton();

        recyclerView = findViewById(R.id.rvFavorites);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        az = new AzureSQL();
        String sortBy = getSharedPreferences("NosAppPreferences", Context.MODE_PRIVATE).getString("sortby", "logo");
        String sortOrder = getSharedPreferences("NosAppPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");

        favsList = az.getFavorites(sortBy, sortOrder);

        favsAdapter = new FavoritesAdapter(favsList,this, this);
        recyclerView.setAdapter(favsAdapter);
//
//        loadFavorites();
    }

    @Override
    public void onResume(Bundle savedInstanceState) {
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(FavoritesActivity.this, ClipsActivity.class);
        Bundle args = new Bundle();


        intent.putExtra("favShowLogo", favsList.get(position).getLogo());
        startActivity(intent);

        Toast.makeText(this, "Generating \"" +  favsList.get(position).getLogo()
                        + "\" video" ,
                Toast.LENGTH_LONG).show();
    }

    private void settingsButton() {
        ImageButton ibSettings = findViewById(R.id.ibSettings);
        ibSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SettingsDialogFragment dialogFragment = new SettingsDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "SettingsDialogFragment");
            }
        });
    }


    private void randomButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonRandom);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FavoritesActivity.this,RandomActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void homeButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonHome);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FavoritesActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void favoritesButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonFavorites);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FavoritesActivity.this, FavoritesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSettingsDialogApply(String sortBy, String sortOrder) {
        az = new AzureSQL();
        favsList = az.getFavorites(sortBy, sortOrder);
        favsAdapter = new FavoritesAdapter(favsList, this, this);
        recyclerView.setAdapter(favsAdapter);
        favsAdapter.notifyDataSetChanged();
    }

}