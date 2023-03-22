package com.example.nosapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    private static ArrayList<String> videoIds;
    private final Context context;
    private Lifecycle lifecycle;

    public ClipsAdapter(ArrayList<String> videoIds, Lifecycle lifecycle, Context context) {
        this.videoIds = videoIds;
        this.lifecycle = lifecycle;
        this.context = context;
    }

    @NonNull
    @Override
    public ClipsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView)
                LayoutInflater.from(parent.getContext()).inflate(R.layout.clips_item, parent, false);
        lifecycle.addObserver(youTubePlayerView);

        return new ViewHolder(youTubePlayerView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ClipsAdapter.ViewHolder holder, int position) {
        holder.cueVideo(String.valueOf(videoIds.get(position)));
    }

    @Override
    public int getItemCount() {
        return videoIds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private YouTubePlayerView youTubePlayerView;
        YouTubePlayer youTubePlayer;
        private String currentVideoId;

        ViewHolder (YouTubePlayerView playerView,Context context) {
            super(playerView);
            this.context = context;
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
                            AzureSQL.deleteFavorite(currentVideoId);
                            int index = videoIds.indexOf(currentVideoId);
                            if (index != -1) {
                                videoIds.remove(index);
                                notifyItemRemoved(index);
                            }
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
                                    "\nwww.youtube.com/watch?v=" + currentVideoId);
                            context.startActivity(Intent.createChooser(shareIntent, "Share using"));

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

