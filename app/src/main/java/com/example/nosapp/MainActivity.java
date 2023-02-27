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
    YoutubeUtil youtubeUtil;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            int contactId = showList.get(position).getShowID();
            Intent intent = new Intent(MainActivity.this, ViewActivity.class);
            intent.putExtra("randomVideoString",
                    youtubeUtil.searchForRandomVideo("Rugrats"));
            intent.putExtra("randomVideoString2",
                    "Rugrats");
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

        youtubeUtil = new YoutubeUtil();


        loadShows();
    }



    private void loadShows() {
        /*AzureSQL az = new AzureSQL();
        ArrayList<Show> showList = AzureSQL.getShows();

        ShowAdapter adapter = new ShowAdapter( showList,this);
        recyclerView.setAdapter(adapter);*/


        showList.add(new Shows(1,"Show 1", R.drawable.the_adventures_of_jimmy_neutron_boy_genius_logo));
        showList.add(new Shows(2,"Show 2", R.drawable.kim_possible_logo));
        showList.add(new Shows(3,"Show 3", R.drawable.the_fairly_oddparents_logo));
        showList.add(new Shows(4,"Show 4", R.drawable.hannah_montana_logo));
        showList.add(new Shows(5,"Show 5", R.drawable.courage_the_cowardly_dog_logo));
        showList.add(new Shows(6,"Show 6", R.drawable.ben_10_logo));
        showList.add(new Shows(7,"Show 7", R.drawable.drake_josh_logo));
        showList.add(new Shows(8,"Show 8", R.drawable.icarly_logo));
        showList.add(new Shows(9,"Show 9", R.drawable.neds_declassified_school_survival_guide_logo));
        showList.add(new Shows(10,"Show 10", R.drawable.rugrats_logo));
        showList.add(new Shows(11,"Show 11", R.drawable.spongebob_squarepants_logo));
        showList.add(new Shows(12,"Show 12", R.drawable.thats_so_raven_logo));
        showList.add(new Shows(13,"Show 13", R.drawable.the_powerpuff_girls_logo));
        showList.add(new Shows(14,"Show 14", R.drawable.the_suite_life_of_zack_cody_logo));
        showList.add(new Shows(15,"Show 15", R.drawable.the_wild_thornberrys_logo));
        showList.add(new Shows(16,"Show 16", R.drawable.zoey_101));
        showList.add(new Shows(17,"Show 17", R.drawable.danny_phantom_logo));
        showAdapter.notifyDataSetChanged();
    }


    /*private void viewButton() {
        Button ibList = (Button) findViewById(R.id.buttonView);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ViewActivity.class);

                // Gets new videoId to be passed to View Activity

                intent.putExtra("randomVideoString",
                        youtubeUtil.searchForRandomVideo("Rugrats"));
                intent.putExtra("randomVideoString2",
                        "Rugrats");

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }*/

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
