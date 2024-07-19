package com.arrowwouldstudio.m3u8videoplayer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

public class PlayerActivity extends AppCompatActivity {

    private PlayerView playerView;
    private ExoPlayer player;
    private boolean isFullscreen = false;
    private ImageButton fullscreenButton;
    private ImageButton rotateButton;
    private TextView networkSpeedText;
    private Handler handler;
    private DefaultBandwidthMeter bandwidthMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playerView = findViewById(R.id.player_view);
        networkSpeedText = playerView.findViewById(R.id.network_speed_text);

        // Initialize ExoPlayer
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // Initialize BandwidthMeter
        bandwidthMeter = new DefaultBandwidthMeter.Builder(this).build();

        // Get the URL from the intent
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");

        // Play the video
        playVideo(url);

        // Set up fullscreen button
        fullscreenButton = playerView.findViewById(R.id.fullscreen_button);
        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFullscreen();
            }
        });

        // Set up rotate button
        rotateButton = playerView.findViewById(R.id.rotate_button);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRotation();
            }
        });

        // Start updating network speed
        handler = new Handler(Looper.getMainLooper());
        startUpdatingNetworkSpeed();

        // Change status bar color to black
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_bg)); // Correctly reference the color resource
    }

    private void playVideo(String url) {
        // Create a MediaItem from the URL
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));

        // Set the MediaItem to the player
        player.setMediaItem(mediaItem);

        // Prepare the player
        player.prepare();

        // Start playback
        player.play();
    }

    private void toggleFullscreen() {
        if (isFullscreen) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            fullscreenButton.setImageResource(R.drawable.ic_fullscreen_black_24dp);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            fullscreenButton.setImageResource(R.drawable.ic_fullscreen_exit_black_24dp);
        }
        isFullscreen = !isFullscreen;
    }

    private void toggleRotation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void startUpdatingNetworkSpeed() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                long bitrateEstimate = bandwidthMeter.getBitrateEstimate();
                double speedMbps = bitrateEstimate / 1_000_000.0;
                networkSpeedText.setText(String.format("Speed: %.2f Mbps", speedMbps));
                handler.postDelayed(this, 1000); // Update every second
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the player when the activity is destroyed
        player.release();
        handler.removeCallbacksAndMessages(null);
    }
}
