package com.example.nosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

import kotlin.OverloadResolutionByLambdaReturnType;

public class ViewActivity extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    Bundle extras;
    private String[] videoIds; // Declare the array of videoIds
    String showname;
    TextView actors;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        //shareButton();
        randomButton();
        homeButton();
        favoritesButton();

        actors = findViewById(R.id.actors);
        layout = findViewById(R.id.layout);


        extras = getIntent().getExtras();
        // Get the array of videoIds from the Intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            videoIds = extras.getStringArray("videoIds");
        }

        // Setting up Fragment that allows user to swipe
        ViewPager viewPager = findViewById(R.id.view_pager);
        PagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), videoIds, showname);
        viewPager.setAdapter(viewPagerAdapter);

        Toast.makeText(this, "Generating \"" + extras.getString("showname") + "\" videos" ,
                Toast.LENGTH_LONG).show();
        addDetails(extras.getString("showname"));



        final ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleHeart);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDefaultToggleClick(v);
            }
        });
    }

    public void expand(View view) {
        int v = (actors.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        actors.setVisibility(v);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private String[] videoIds;
        private String shownamee;

        public ViewPagerAdapter(@NonNull FragmentManager fm, String[] videoIds, String showname) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.videoIds = videoIds; // Assign the array of videoIds to the instance variable
            this.shownamee = showname;
        }

        @Override
        public Fragment getItem(int position) {
            ViewActivityFragment fragment = new ViewActivityFragment();
            Bundle args = new Bundle();
            args.putStringArray("videoIds", videoIds); // Pass the array of videoIds as an argument
            args.putString("showname", shownamee);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return videoIds.length;
        }

        @Override
        public CharSequence getPageTitle (int position) {
            return "Page " +position;
        }
    }

    private void displayClip (String videoId) {

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                // using pre-made custom ui
                DefaultPlayerUiController defaultPlayerUiController =
                        new DefaultPlayerUiController(youTubePlayerView, youTubePlayer);


                defaultPlayerUiController.getMenu().addItem(new MenuItem("Delete",
                        R.drawable.ic_baseline_delete_24, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Handle click on the delete menu item here
                    }
                }));

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




   /* private void shareButton() {
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
    */


    private void randomButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonRandom);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ViewActivity.this, RandomActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void homeButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonHome);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ViewActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void favoritesButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonFavorites);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ViewActivity.this, FavoritesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void onDefaultToggleClick(View view) {
        ToggleButton toggleButton = (ToggleButton) view;
        if (toggleButton.isChecked()) {
            AzureSQL.addFavorite(extras.getString("videoId"), extras.getString("showname"));
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            AzureSQL.deleteFavorite(extras.getString("videoId"));
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }
}