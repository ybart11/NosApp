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
            adapter = new ClipsAdapter(az.getClips(favShowName), this.getLifecycle(),this);
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);


        homeButton();


    }



    private void homeButton() {
        ImageButton ibList = findViewById(R.id.imageButton);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ClipsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


}