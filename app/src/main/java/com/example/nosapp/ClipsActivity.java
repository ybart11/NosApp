package com.example.nosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ClipsActivity extends AppCompatActivity {

    Bundle extras;
    ClipsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clips);

        RecyclerView recyclerView = findViewById(R.id.rvFavoriteClips);
        recyclerView.setHasFixedSize(true);

        AzureSQL az = new AzureSQL();
        extras = getIntent().getExtras();

        // Show logo passed from FavoritesActivity
        String favShowName = extras.getString("favShowLogo");

        if (extras != null) {
            adapter = new ClipsAdapter(az.getClips(favShowName), this.getLifecycle());
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        shareButton();

        randomButton();
        homeButton();
        favoritesButton();
        settingsButton();

    }



    private void shareButton() {
        ImageButton ibList = findViewById(R.id.ibShare);
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
        ImageButton ibList = findViewById(R.id.ibRandom);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ClipsActivity.this,RandomActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void homeButton() {
        ImageButton ibList = findViewById(R.id.ibHome);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ClipsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void favoritesButton() {
        ImageButton ibList = findViewById(R.id.ibFavorites);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ClipsActivity.this, FavoritesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}