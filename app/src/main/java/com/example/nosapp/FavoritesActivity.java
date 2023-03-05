package com.example.nosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoritesAdapter favsAdapter;
    private ArrayList<Favorites> favsList;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            int showId = favsList.get(position).getShowID();
            Intent intent = new Intent(FavoritesActivity.this, ClipsActivity.class);
            intent.putExtra("showname", showId);
            startActivity(intent);
        }
    };

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

        favsList = new ArrayList<>();

        favsAdapter = new FavoritesAdapter(favsList,this);
        favsAdapter.setOnItemClickListener(onItemClickListener);

        recyclerView.setAdapter(favsAdapter);

        loadFavorites();
    }

    private void loadFavorites() {
        AzureSQL az = new AzureSQL();
        ArrayList<Favorites> favsList = az.getFavorites();

        FavoritesAdapter adapter = new FavoritesAdapter( favsList,this);
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