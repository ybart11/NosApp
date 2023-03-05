package com.example.nosapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.menu.MenuItem;

import java.util.ArrayList;

public class ClipsAdapter extends RecyclerView.Adapter<ClipsAdapter.ViewHolder>{

    private ArrayList<Favorites> videoIds;
    private Lifecycle lifecycle;

    public ClipsAdapter(ArrayList<Favorites> videoIds, Lifecycle lifecycle) {
        this.videoIds = videoIds;
        this.lifecycle = lifecycle;
    }

    @NonNull
    @Override
    public ClipsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView)
                LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_clip_item, parent, false);
        lifecycle.addObserver(youTubePlayerView);

        return new ViewHolder(youTubePlayerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClipsAdapter.ViewHolder holder, int position) {
        holder.cueVideo(String.valueOf(videoIds.get(position)));
    }

    @Override
    public int getItemCount() {
        return videoIds.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private YouTubePlayerView youTubePlayerView;
        YouTubePlayer youTubePlayer;
        private String currentVideoId;

        ViewHolder (YouTubePlayerView playerView) {
            super(playerView);
            youTubePlayerView = playerView;


            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer initializedYouTubePlayer) {
                    youTubePlayer = initializedYouTubePlayer;
//                    youTubePlayer.cueVideo(currentVideoId, 0);


                    // using pre-made custom ui
                    DefaultPlayerUiController defaultPlayerUiController =
                            new DefaultPlayerUiController(youTubePlayerView, youTubePlayer);
                    // Can be used to delete favorite

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

                        }
                    }));
                    defaultPlayerUiController.showMenuButton(true);


                    defaultPlayerUiController.showYouTubeButton(true);
                    defaultPlayerUiController.showVideoTitle(true);
                    youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());

                    youTubePlayer.cueVideo(currentVideoId, 0 );
                }
            });
        }

        void cueVideo(String videoId) {
            currentVideoId = videoId;

            if (youTubePlayer == null) {
                return;
            }
            youTubePlayer.cueVideo(videoId, 0);

        }

    }
}

