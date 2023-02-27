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

public class ClipsActivity extends AppCompatActivity {

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_clip);

        shareButton();

        randomButton();
        homeButton();
        favoritesButton();


        extras = getIntent().getExtras();

    }



    private void shareButton() {
        Button ibList = (Button) findViewById(R.id.buttonShare);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I found this clip on the" +
                        " Nostalgia App: " +
                        "\nwww.youtube.com/watch?v=" );
                startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });
    }

    private void randomButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonRandom);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ClipsActivity.this,RandomActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void homeButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonHome);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ClipsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void favoritesButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonFavorites);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ClipsActivity.this, FavoritesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}