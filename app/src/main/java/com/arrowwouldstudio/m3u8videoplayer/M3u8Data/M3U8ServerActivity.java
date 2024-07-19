package com.arrowwouldstudio.m3u8videoplayer.M3u8Data;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arrowwouldstudio.m3u8videoplayer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class M3U8ServerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChannelAdapter adapter;
    private List<Channel> channelList;
    private DatabaseReference databaseReference;
    private LottieAnimationView lottieAnimationViewServerDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m3_u8_server);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("M3U8 Server");
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lottieAnimationViewServerDown = findViewById(R.id.lottieAnimationViewServerDown);

        channelList = new ArrayList<>();
        adapter = new ChannelAdapter(channelList, this);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("channels");
        fetchChannels();

        // Change status bar color to black
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_bg));
    }

    private void fetchChannels() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                channelList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Channel channel = postSnapshot.getValue(Channel.class);
                    channelList.add(channel);
                }
                adapter.notifyDataSetChanged();
                checkDataAvailability();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("M3U8ServerActivity", "Error getting data.", databaseError.toException());
                showServerDownAnimation();
            }
        });
    }

    private void checkDataAvailability() {
        if (channelList.isEmpty()) {
            showServerDownAnimation();
        } else {
            hideServerDownAnimation();
        }
    }

    private void showServerDownAnimation() {
        lottieAnimationViewServerDown.setVisibility(View.VISIBLE);
        lottieAnimationViewServerDown.playAnimation();
    }

    private void hideServerDownAnimation() {
        lottieAnimationViewServerDown.setVisibility(View.GONE);
        lottieAnimationViewServerDown.cancelAnimation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Optionally, you can add an animation or specific behavior on back press
    }
}



//this code need for error and bug
//package com.arrowwouldstudio.m3u8videoplayer.M3u8Data;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.arrowwouldstudio.m3u8videoplayer.R;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class M3U8ServerActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private ChannelAdapter adapter;
//    private List<Channel> channelList;
//    private FirebaseFirestore db;
//    private ImageView imageViewServerDown;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_m3_u8_server);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setTitle("M3U8 Server");
//        }
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        imageViewServerDown = findViewById(R.id.imageViewServerDown);
//
//        channelList = new ArrayList<>();
//        adapter = new ChannelAdapter(channelList, this);
//        recyclerView.setAdapter(adapter);
//
//        db = FirebaseFirestore.getInstance();
//        fetchChannels();
//
//        // Change status bar color to black
//        Window window = this.getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(getResources().getColor(R.color.color_bg)); // Correctly reference the color resource
//    }
//
//    private void fetchChannels() {
//        db.collection("channels")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        channelList.clear();
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Channel channel = document.toObject(Channel.class);
//                            channelList.add(channel);
//                        }
//                        adapter.notifyDataSetChanged();
//                        checkDataAvailability();
//                    } else {
//                        Log.w("MainActivity", "Error getting documents.", task.getException());
//                        showServerDownImage();
//                    }
//                });
//    }
//
//    private void checkDataAvailability() {
//        if (channelList.isEmpty()) {
//            showServerDownImage();
//        } else {
//            hideServerDownImage();
//        }
//    }
//
//    private void showServerDownImage() {
//        imageViewServerDown.setVisibility(View.VISIBLE);
//    }
//
//    private void hideServerDownImage() {
//        imageViewServerDown.setVisibility(View.GONE);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        // Optionally, you can add an animation or specific behavior on back press
//    }
//}
