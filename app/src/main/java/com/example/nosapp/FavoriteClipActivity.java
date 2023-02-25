package com.example.nosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class FavoriteClipActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteClipAdapter favoriteclipAdapter;
    private ArrayList<FavoriteClip> favoriteClipArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_clips);

        recyclerView = findViewById(R.id.rvFavoriteClips);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        favoriteClipArrayList = new ArrayList<>();

        favoriteclipAdapter = new FavoriteClipAdapter(favoriteClipArrayList, this);
        recyclerView.setAdapter(favoriteclipAdapter);

        loadClips();

        backButton();
        shareButton();

        randomButton();
        homeButton();
        favoritesButton();
    }

    private void loadClips() {

        favoriteClipArrayList.add(new FavoriteClip("Show 1", "123","456"));

        favoriteclipAdapter.notifyDataSetChanged();
    }


    private void backButton() {
        Button ibList = (Button) findViewById(R.id.buttonSettings);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FavoriteClipActivity.this,FavoritesActivity.class);
                startActivity(intent);
            }
        });
    }
    private void shareButton() {
        Button ibList = (Button) findViewById(R.id.buttonShare);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Share this text");
                startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });
    }

    private void randomButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonRandom);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FavoriteClipActivity.this,RandomActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void homeButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonHome);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FavoriteClipActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void favoritesButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonFavorites);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FavoriteClipActivity.this, FavoritesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}