package com.example.nosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.menu.MenuItem;

import java.util.Random;

public class ViewActivityFragment extends Fragment {

        private YouTubePlayer initializedYouTubePlayer;
        private String[] videoIds; // Declare the array of videoIds

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_view_fragment, container, false);

            YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
            Bundle args = getArguments();
            if (args != null) {
                videoIds = args.getStringArray("videoIds");
            }

            getLifecycle().addObserver(youTubePlayerView);

            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                    {

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
                                        args.getString("showname"));
                                startActivity(Intent.createChooser(shareIntent, "Share using"));
                            }
                        }));

                        defaultPlayerUiController.showMenuButton(true);
                        defaultPlayerUiController.showYouTubeButton(false);

                        youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());
                        youTubePlayer.cueVideo(getNextVideoId(), 0 );
                    }

                    initializedYouTubePlayer = youTubePlayer;
                }
            });

            return view;
        }

        // pause when fragment goes offscreen
        @Override
        public void setMenuVisibility(final boolean visible) {
            super.setMenuVisibility(visible);
            if (!visible && initializedYouTubePlayer != null)
                initializedYouTubePlayer.pause();
        }


        private String getNextVideoId() {
            Random random = new Random();
            return videoIds[random.nextInt(videoIds.length)];
        }

}