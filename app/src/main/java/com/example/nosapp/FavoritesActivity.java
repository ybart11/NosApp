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
            int contactId = favsList.get(position).getShowID();
            Intent intent = new Intent(FavoritesActivity.this, ClipsActivity.class);
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

        favsList.add(new Favorites(1,"Show 1", R.drawable.the_adventures_of_jimmy_neutron_boy_genius_logo));
        favsList.add(new Favorites(2,"Show 2", R.drawable.kim_possible_logo));
        favsList.add(new Favorites(3,"Show 3", R.drawable.the_fairly_oddparents_logo));
        favsList.add(new Favorites(4,"Show 4", R.drawable.hannah_montana_logo));
        favsList.add(new Favorites(5,"Show 5", R.drawable.courage_the_cowardly_dog_logo));

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