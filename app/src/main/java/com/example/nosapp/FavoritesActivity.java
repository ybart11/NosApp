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

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShowAdapter showAdapter;
    private List<Show> showList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        clipsButton();
        randomButton();
        homeButton();
        favoritesButton();

        recyclerView = findViewById(R.id.rvFavorites);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        showList = new ArrayList<>();

        showAdapter = new ShowAdapter(this, showList);
        recyclerView.setAdapter(showAdapter);

        loadShows();
    }

    private void loadShows() {

        showList.add(new Show("Show 1", R.drawable.the_adventures_of_jimmy_neutron_boy_genius_logo));
        showList.add(new Show("Show 2", R.drawable.kim_possible_logo));
        showList.add(new Show("Show 3", R.drawable.the_fairly_oddparents_logo));
        showList.add(new Show("Show 4", R.drawable.hannah_montana_logo));
        showList.add(new Show("Show 5", R.drawable.courage_the_cowardly_dog_logo));

        showAdapter.notifyDataSetChanged();
    }

    private void clipsButton() {
        Button b = (Button) findViewById(R.id.buttonClips);
        b.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(FavoritesActivity.this, FavoriteClipActivity.class);
                startActivity(intent);
            }
        });
    }

    private void settingsButton() {
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
}