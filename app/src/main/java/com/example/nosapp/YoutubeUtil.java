package com.example.nosapp;



import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.YouTube;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class YoutubeUtil {

    private static final String API_KEY = "AIzaSyAL3X_VZjgcmm1r2jIgQjAFahUmuWoyVcE";
    private YouTube mYouTube;


    public YoutubeUtil () {}

    String searchForVideoInPlaylist(String playlistId) {

        // Set the thread policy to permit all network access on the current thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Create a new YouTube object using the NetHttpTransport and JacksonFactory
        mYouTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), httpRequest -> {})
                .setApplicationName("Youtube Practice")
                .build();

        try {
            // Create a new playlistItemsListRequest to get the playlist items from the specified playlist
            YouTube.PlaylistItems.List playlistItemsListRequest = mYouTube.playlistItems().list("snippet");

            // Set the API key and playlist ID for the playlistItemsListRequest
            playlistItemsListRequest.setKey(API_KEY);
            playlistItemsListRequest.setPlaylistId(playlistId);

            // Set the maximum number of results to 50
            playlistItemsListRequest.setMaxResults(50L);

            // Execute the playlistItemsListRequest and get the playlist items from the response
            PlaylistItemListResponse playlistItemListResponse = playlistItemsListRequest.execute();
            List<PlaylistItem> playlistItems = playlistItemListResponse.getItems();

            // Select a random playlist item from the list of playlist items
            int randomIndex = (int) (Math.random() * playlistItems.size());
            PlaylistItem randomPlaylistItem = playlistItems.get(randomIndex);

            // Get the video ID from the snippet of the random playlist item
            String videoId = randomPlaylistItem.getSnippet().getResourceId().getVideoId();

            // Log the video ID and return it
            Log.d("Random Video ID", videoId);
            return videoId;
        } catch (IOException e) {
            // If an IOException occurs, print the stack trace and return "Failed"
            e.printStackTrace();
            return "Failed";
        }
    }





}
