package com.example.nosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class ViewActivity extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    Bundle extras;
    YoutubeUtil youtubeUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        shareButton();
        randomButton();
        homeButton();
        favoritesButton();

        extras = getIntent().getExtras();
        displayClip(extras.getString("randomVideoString"));
        addDetails(extras.getString("randomVideoString2"));

    }

    private void displayClip (String videoId) {

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

    }

    private void addDetails(String showname) {
        AzureSQL az = new AzureSQL();
        List<Shows> details = az.getShowDetail(showname);

        if (details.isEmpty()) {
            // handle the case where there are no shows with the given name
            Log.d("addDetails", "No shows found with name " + showname);
            return;
        }

        TextView editShowName = findViewById(R.id.textShowName);
        TextView editChannel = findViewById(R.id.editChannel);
        TextView editStartDate = findViewById(R.id.editStartDate);
        TextView editEndDate = findViewById(R.id.editEndDate);
        TextView editSeasons = findViewById(R.id.editSeasons);
        TextView editEpisodes = findViewById(R.id.editEpisodes);
        TextView editSynopsis = findViewById(R.id.editSynopsis);

        for (Shows show : details) {
            Log.d("addDetails", "Show: " + show.getShowName() + ", " + show.getChannel() + ", " + show.getStartDate() + ", " + show.getEndDate() + ", " + show.getSeasons() + ", " + show.getEpisodes() + ", " + show.getSynopsis());

            editShowName.setText(show.getShowName());
            editChannel.setText(show.getChannel());
            editStartDate.setText(show.getStartDate().toString()); // or use a SimpleDateFormat to format the date
            editEndDate.setText(show.getEndDate().toString()); // or use a SimpleDateFormat to format the date
            editSeasons.setText(Integer.toString(show.getSeasons()));
            editEpisodes.setText(Integer.toString(show.getEpisodes()));
            editSynopsis.setText(show.getSynopsis());
        }
    }
    private void favoriteButton(String videoID){
        ImageButton ibList = findViewById(R.id.imageButton2);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

    }




    private void shareButton() {
        ImageButton ibList = findViewById(R.id.buttonshare);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I found this clip on the" +
                        " Nostalgia App: " +
                        "\nwww.youtube.com/watch?v=" +
                        extras.getString("randomVideoString"));
                startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });
    }

    private void randomButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonRandom);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ViewActivity.this,RandomActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void homeButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonHome);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ViewActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void favoritesButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonFavorites);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ViewActivity.this, FavoritesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


}