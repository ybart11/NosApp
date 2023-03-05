package com.example.nosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements FavoritesAdapter.RecyclerViewInterface {

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
        favsList = az.getFavorites();

        favsAdapter = new FavoritesAdapter(favsList,this, this);
        recyclerView.setAdapter(favsAdapter);

        loadFavorites();
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(FavoritesActivity.this, ClipsActivity.class);

        intent.putExtra("favShowName", favsList.get(position).getShowName());
        startActivity(intent);

        Toast.makeText(this, "Generating \"" +  favsList.get(position).getShowName()
                        + "\" video" ,
                Toast.LENGTH_LONG).show();

    }


    private void loadFavorites() {

        FavoritesAdapter adapter = new FavoritesAdapter( favsList,this, this);
        recyclerView.setAdapter(adapter);


        favsAdapter.notifyDataSetChanged();
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
        ImageButton ibList = (ImageButton) findViewById(R.id.ibRandom);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FavoritesActivity.this,RandomActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void homeButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.ibHome);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FavoritesActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void favoritesButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.ibFavorites);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FavoritesActivity.this, FavoritesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}