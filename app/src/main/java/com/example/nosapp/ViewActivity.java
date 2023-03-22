package com.example.nosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.menu.MenuItem;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    Bundle extras;
    TextView actors;
    LinearLayout layout;
    String currentVideoId;
    YoutubeUtil yt;
    SwipeRefreshLayout swipeRefreshLayout;
    ToggleButton editToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


        homeButton();



        extras = getIntent().getExtras();
        if (extras != null) {
            currentVideoId = extras.getString("videoId");
            displayClip(currentVideoId);
        }

        Toast.makeText(this, "Generating \"" + extras.getString("showname") + "\" video" ,
                Toast.LENGTH_LONG).show();
        addDetails(extras.getString("showname"));

        editToggle = (ToggleButton) findViewById(R.id.toggleHeart);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDefaultToggleClick(v);
            }
        });

        initRefresh();
    }

    public void initRefresh() {
        // What happens when user refreshes
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                yt = new YoutubeUtil();

                // Uncheck heart button
                editToggle.setChecked(false);

                // Generate new videoId
                String newVideoId = yt.searchForVideoInPlaylist(extras.getString("playlistId"));
                if (!newVideoId.equals(currentVideoId)) {
                    currentVideoId = newVideoId;
                    displayClip(currentVideoId);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public void expand(View view) {
        int v = (actors.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        actors.setVisibility(v);
    }

    private void displayClip (String videoId) {

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        // Display video when user first clicks on this activity
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                // using pre-made custom ui
                DefaultPlayerUiController defaultPlayerUiController =
                        new DefaultPlayerUiController(youTubePlayerView, youTubePlayer);

                defaultPlayerUiController.getMenu().addItem(new MenuItem("Share",
                        R.drawable.ic_baseline_share_24,new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Handle click on the delete menu item here
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I found this clip on the" +
                                " Nostalgia App: " +
                                "\nwww.youtube.com/watch?v=" +
                                extras.getString("videoId"));
                        startActivity(Intent.createChooser(shareIntent, "Share using"));

                    }
                }));

                defaultPlayerUiController.showMenuButton(true);
                defaultPlayerUiController.showYouTubeButton(false);

                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());
                youTubePlayer.cueVideo(videoId, 0 );
            }
        });

        // Display video when user refreshes
        youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
            @Override
            public void onYouTubePlayer(@NonNull YouTubePlayer youTubePlayer) {
                // using pre-made custom ui
                DefaultPlayerUiController defaultPlayerUiController =
                        new DefaultPlayerUiController(youTubePlayerView, youTubePlayer);

                defaultPlayerUiController.getMenu().addItem(new MenuItem("Share",
                        R.drawable.ic_baseline_share_24, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Handle click on the share menu item here
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I found this clip on the" +
                                " Nostalgia App: " +
                                "\nwww.youtube.com/watch?v=" +
                                currentVideoId);
                        startActivity(Intent.createChooser(shareIntent, "Share using"));

                    }
                }));

                defaultPlayerUiController.showMenuButton(true);
                defaultPlayerUiController.showYouTubeButton(false);

                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }

    private void addDetails(String showname) {
        AzureSQL az = new AzureSQL();
        ArrayList<Shows> details = az.getShowDetail(showname);

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


    private void homeButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.backButton);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ViewActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }



    public void onDefaultToggleClick(View view) {
        ToggleButton toggleButton = (ToggleButton) view;
        if (toggleButton.isChecked()) {
            AzureSQL.addFavorite(currentVideoId, extras.getString("showname"));
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            AzureSQL.deleteFavorite(currentVideoId);
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }
}