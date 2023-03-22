package com.example.nosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import java.util.Date;
import java.util.List;

public class ViewActivity extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    Bundle extras;
    String currentVideoId;
    YoutubeUtil yt;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ArrayList<Shows> details;
    ShowExpandAdapter adapter;
    AzureSQL az;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        recyclerView = findViewById(R.id.recycView);

        az = new AzureSQL();

        extras = getIntent().getExtras();


        Toast.makeText(this, "Generating \"" + extras.getString("showname") + "\" videos",
                Toast.LENGTH_LONG).show();
        addDetails(extras.getString("showname"));


        if (extras != null) {
            currentVideoId = extras.getString("videoId");
            displayClip(currentVideoId);
        }


        final ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleHeart);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDefaultToggleClick(v);
            }

        });


        details = AzureSQL.getShowDetail(extras.getString("showname"));

        adapter = new ShowExpandAdapter(details);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setHasFixedSize(true);

        // What happens when user refreshes
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                yt = new YoutubeUtil();

                //      Uncheck heart button
                //   editToggle.setChecked(false);

                // Generate new videoId
                String newVideoId = yt.searchForVideoInPlaylist(extras.getString("playlistId"));
                if (!newVideoId.equals(currentVideoId)) {
                    currentVideoId = newVideoId;
                    displayNewClip(currentVideoId);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        homeButton();

        // setRecyclerView();
    }

    private void onDefaultToggleClick(View v) {
        ToggleButton toggleButton = (ToggleButton) v;
        if (toggleButton.isChecked()) {
            AzureSQL.addFavorite(currentVideoId, extras.getString("showname"));
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            AzureSQL.deleteFavorite(currentVideoId);
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void setRecyclerView() {
        adapter = new ShowExpandAdapter(details);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }


    private void displayClip(String videoId) {

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                // using pre-made custom ui
                DefaultPlayerUiController defaultPlayerUiController =
                        new DefaultPlayerUiController(youTubePlayerView, youTubePlayer);

                defaultPlayerUiController.getMenu().addItem(new MenuItem("Share",
                        R.drawable.ic_baseline_share_24, new View.OnClickListener() {
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
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }

    private void displayNewClip(String videoId) {
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


    public void addDetails(String showname) {

        AzureSQL az = new AzureSQL();
        ArrayList<Shows> details = AzureSQL.getShowDetail(showname);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.card, null);
        setContentView(layout);

        if (details.isEmpty()) {
            // handle the case where there are no shows with the given name
            Log.d("addDetails", "No shows found with name " + showname);
            return;
        }
        //setContentView(R.layout.card);
        TextView editShowName = findViewById(R.id.textShowName2);
        TextView editChannel = findViewById(R.id.editChannel2);
        TextView editStartDate = findViewById(R.id.editStartDate2);
        TextView editEndDate = findViewById(R.id.editEndDate2);
        TextView editSeasons = findViewById(R.id.editSeasons2);
        TextView editEpisodes = findViewById(R.id.editEpisode2);
        TextView editSynopsis = findViewById(R.id.editSynopsis);


        for (Shows show : details) {
            Log.d("addDetails", "Show: " + Shows.getShowName() + ", " + Shows.getChannel() + ", " + Shows.getStartDate() + ", " + Shows.getEndDate() + ", " + Shows.getSeasons() + ", " + Shows.getEpisodes() + ", " + Shows.getSynopsis());


            editShowName.setText(Shows.getShowName());
            editChannel.setText(Shows.getChannel());
            editStartDate.setText(Shows.getStartDate()); // or use a SimpleDateFormat to format the date
            editEndDate.setText(Shows.getEndDate()); // or use a SimpleDateFormat to format the date
            editSeasons.setText(Integer.toString(Shows.getSeasons()));
            editEpisodes.setText(Integer.toString(Shows.getEpisodes()));
            editSynopsis.setText(Shows.getSynopsis());

        }

    //    ViewGroup parentView = findViewById(R.id.recycView);
     //   parentView.addView(layout);
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

}
