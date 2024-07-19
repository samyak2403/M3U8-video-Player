package com.arrowwouldstudio.m3u8videoplayer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaylistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private List<Channel> channelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("M3U8 PlayList");
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        channelList = new ArrayList<>();
        loadSavedChannels();

        adapter = new PlaylistAdapter(channelList, this);
        recyclerView.setAdapter(adapter);

        // Change status bar color to black
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_bg)); // Correctly reference the color resource
    }

    private void loadSavedChannels() {
        SharedPreferences sharedPreferences = getSharedPreferences("M3U8Links", MODE_PRIVATE);
        Set<String> links = sharedPreferences.getStringSet("links", new HashSet<>());

        for (String link : links) {
            String[] parts = link.split("###");
            if (parts.length == 2) {
                channelList.add(new Channel(parts[0], parts[1]));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        channelList.clear();
        loadSavedChannels();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}


//package com.arrowwouldstudio.m3u8videoplayer;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class PlaylistActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private PlaylistAdapter adapter;
//    private List<Channel> channelList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_playlist);
//
//        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        channelList = new ArrayList<>();
//        loadSavedChannels();
//
//        adapter = new PlaylistAdapter(channelList, this);
//        recyclerView.setAdapter(adapter);
//    }
//
//    private void loadSavedChannels() {
//        SharedPreferences sharedPreferences = getSharedPreferences("M3U8Links", MODE_PRIVATE);
//        Set<String> links = sharedPreferences.getStringSet("links", new HashSet<>());
//
//        for (String link : links) {
//            String[] parts = link.split("###");
//            if (parts.length == 2) {
//                channelList.add(new Channel(parts[0], parts[1]));
//            }
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        channelList.clear();
//        loadSavedChannels();
//        adapter.notifyDataSetChanged();
//    }
//}
