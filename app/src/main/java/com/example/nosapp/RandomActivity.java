package com.example.nosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.menu.MenuItem;

import java.util.ArrayList;
import java.util.Random;

public class RandomActivity extends AppCompatActivity {
    YoutubeUtil yt;
    YouTubePlayerView youTubePlayerView;
    private ArrayList<Shows> showList;
    SwipeRefreshLayout swipeRefreshLayout;
    String currentVideoId;
    String currentShowname;
    ToggleButton editToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);


        homeButton();


        showList = new ArrayList<>();
        yt = new YoutubeUtil();
        currentVideoId = selectRandomVideo();

        try {
            displayClip(currentVideoId);
            addDetails(currentShowname);
        } catch (RuntimeException e) {
            Toast.makeText(this, "Something went wrong",
                    Toast.LENGTH_LONG).show();
        }

        editToggle = (ToggleButton) findViewById(R.id.toggleHeart);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDefaultToggleClick(v);
            }
        });

        initRefresh();
    }

    private void initRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Uncheck heart button
                editToggle.setChecked(false);

                String newVideoId = selectRandomVideo();

                // Generate new videoId
                if (!newVideoId.equals(currentVideoId)) {
                    currentVideoId = newVideoId;
                    displayClip(currentVideoId);
                    addDetails(currentShowname);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private String selectRandomVideo () {

        String [] shownames = getResources().getStringArray(R.array.show_names_full_text);
        String [] playlistids = getResources().getStringArray(R.array.playlist_ids);

        for (int i = 0; i < shownames.length; i++) {
            showList.add( new Shows(shownames[i], playlistids[i]));
        }

        int randomIndex = new Random().nextInt(showList.size());
        currentShowname = showList.get(randomIndex).getShowName();
        String randomPlaylistId = showList.get(randomIndex).getPlaylistId();
        return yt.searchForVideoInPlaylist(randomPlaylistId);
    }

    private void displayClip (String videoId) {

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        // Display video when user first clicks on showIcon
        youTubePlayerView.addYouTubePlayerListener( new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

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
                                videoId);
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
                                videoId);
                        startActivity(Intent.createChooser(shareIntent, "Share using"));

                    }
                }));

                defaultPlayerUiController.showMenuButton(true);
                defaultPlayerUiController.showYouTubeButton(false);

                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                findViewById(R.id.scrollView).setVisibility(View.GONE);
                findViewById(R.id.backButton).setVisibility(View.GONE);


                // Set orientation to landscape
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                findViewById(R.id.scrollView).setVisibility(View.VISIBLE);
                findViewById(R.id.backButton).setVisibility(View.VISIBLE);

                // Set orientation to portrait
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

    public void onDefaultToggleClick(View view) {
        ToggleButton toggleButton = (ToggleButton) view;
        if (toggleButton.isChecked()) {
            AzureSQL.addFavorite(currentVideoId, currentShowname);
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            AzureSQL.deleteFavorite(currentVideoId);
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void homeButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.backButton);
        ibList.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(RandomActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }



}