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

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShowAdapter showAdapter;
    private ArrayList<Shows> showList;
    YoutubeUtil yt;
    int [] showImages = {
            R.drawable.the_adventures_of_jimmy_neutron_boy_genius_logo,
            R.drawable.kim_possible_logo,
            R.drawable.the_fairly_oddparents_logo,
            R.drawable.hannah_montana_logo,
            R.drawable.courage_the_cowardly_dog_logo,
            R.drawable.ben_10_logo,
            R.drawable.drake_josh_logo,
            R.drawable.fosters_home_for_imaginary_friends_logo,
            R.drawable.ed_edd_n_eddy_logo,
            R.drawable.icarly_logo,
            R.drawable.neds_declassified_school_survival_guide_logo,
            R.drawable.rugrats_logo,
            R.drawable.regular_show_logo,
            R.drawable.spongebob_squarepants_logo,
            R.drawable.thats_so_raven_logo,
            R.drawable.the_powerpuff_girls_logo,
            R.drawable.the_suite_life_of_zack_cody_logo,
            R.drawable.the_wild_thornberrys_logo,
            R.drawable.zoey_101,
            R.drawable.danny_phantom_logo};

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            yt = new YoutubeUtil();

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            String showName = showList.get(position).getShowName();
            String playlistId = showList.get(position).getPlaylistId();
            Intent intent = new Intent(MainActivity.this, ViewActivity.class);
            intent.putExtra("showname", showName);
            intent.putExtra("playlistId", playlistId);
            intent.putExtra("videoId", yt.searchForVideoInPlaylist(playlistId));
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomButton();
        homeButton();
        favoritesButton();

        recyclerView = findViewById(R.id.rvShows);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        showList = new ArrayList<>();

        showAdapter = new ShowAdapter(showList,this);
        showAdapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setAdapter(showAdapter);
        loadShows();
    }

    private void loadShows() {
        String [] shownames = getResources().getStringArray(R.array.show_names_full_text);
        String [] playlistids = getResources().getStringArray(R.array.playlist_ids);

        for (int i = 0; i < shownames.length; i++) {
            showList.add( new Shows(shownames[i], showImages[i], playlistids[i]));
        }

        showAdapter.notifyDataSetChanged();
    }


    private void randomButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonRandom);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RandomActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void homeButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonHome);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void favoritesButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonFavorites);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


}
