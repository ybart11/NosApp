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


public class FavClipsAdapter extends RecyclerView.Adapter<FavClipsAdapter.ViewHolder>{

    private String[] videoIds;
    private Lifecycle lifecycle;

    public FavClipsAdapter(String[] videoIds, Lifecycle lifecycle) {
        this.videoIds = videoIds;
        this.lifecycle = lifecycle;
    }

    @NonNull
    @Override
    public FavClipsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView)
                LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_clip_item, parent, false);
        lifecycle.addObserver(youTubePlayerView);

        return new ViewHolder(youTubePlayerView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavClipsAdapter.ViewHolder holder, int position) {
        holder.cueVideo(videoIds[position]);
    }

    @Override
    public int getItemCount() {
        return videoIds.length;
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

//    private void shareButton() {
//        ImageButton ibList = findViewById(R.id.buttonshare);
//        ibList.setOnClickListener (new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I found this clip on the" +
//                        " Nostalgia App: " +
//                        "\nwww.youtube.com/watch?v=" +
//                        extras.getString("randomVideoString"));
//                startActivity(Intent.createChooser(shareIntent, "Share using"));
//            }
//        });
//    }
}
