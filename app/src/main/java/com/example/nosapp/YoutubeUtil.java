package com.example.nosapp;



import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.YouTube;


import java.io.IOException;
import java.util.List;
import java.util.Random;


public class YoutubeUtil extends AsyncTask<String, Void, Video> {

    private static final String TAG = YoutubeUtil.class.getSimpleName();
    private static final String API_KEY = "AIzaSyAL3X_VZjgcmm1r2jIgQjAFahUmuWoyVcE";
    private static final String PARTS = "snippet";
    private YouTube mYouTube;

    private TextView mTitleTextView;


    public YoutubeUtil (TextView titleTextView) {
        mTitleTextView = titleTextView;
    }

    public YoutubeUtil () {}

    @Override
    protected Video doInBackground(String... params) {
        YouTube youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                request -> {}).setApplicationName("Youtube Practice").build();

        try {
            YouTube.Videos.List listVideosRequest = youTube.videos()
                    .list(PARTS)
                    .setKey(API_KEY)
                    .setId(params[0]);
            VideoListResponse listResponse = listVideosRequest.execute();
            return listResponse.getItems().get(0);

        } catch (IOException e) {
            Log.e(TAG, "Error fetching video", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute (Video video) {
        if (video != null) {
            mTitleTextView.setText(video.getSnippet().getTitle());
        }
    }

    String searchForRandomVideo(String showname) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mYouTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), httpRequest -> {})
                .setApplicationName("Youtube Practice")
                .build();

        try {
            YouTube.Search.List searchList = mYouTube.search().list("id");
            searchList.setKey(API_KEY);
            searchList.setType("video");
            searchList.setRelatedToVideoId("pztX2ay7gA4"); // Spongebob
            searchList.setFields("items(id(videoId))");
            searchList.setMaxResults(1L);
            searchList.setOrder("relevance");
            searchList.setVideoDuration("short");
            String [] keywords = {showname};
            searchList.setQ(keywords[new Random().nextInt(keywords.length)]);
            SearchListResponse response = searchList.execute();
            String videoId = response.getItems().get(0).getId().getVideoId();
            Log.d("Random Video ID", videoId);
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed";
        }
    }
}
