package com.arrowwouldstudio.m3u8videoplayer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arrowwouldstudio.m3u8videoplayer.M3u8Data.M3U8ServerActivity;
import com.arrowwouldstudio.m3u8videoplayer.Setting.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private EditText urlEditText;
    private Button playButton;
    private Button saveButton;
    private Button viewPlaylistButton;
    private Button pasteButton;
    private Button clearButton;
    private FloatingActionButton fabCloud;
    private FloatingActionButton settingBtn;
    private Drawable originalBackground;

    private boolean isPasteMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlEditText = findViewById(R.id.url_edit_text);
        playButton = findViewById(R.id.play_button);
        saveButton = findViewById(R.id.save_button);
        viewPlaylistButton = findViewById(R.id.view_playlist_button);
        pasteButton = findViewById(R.id.paste_button);

        fabCloud = findViewById(R.id.fab_cloud);
        settingBtn = findViewById(R.id.setting_Btn);

        // Store original background
        originalBackground = urlEditText.getBackground();

        // Set up play button click listener
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlEditText.getText().toString();
                if (!url.isEmpty()) {
                    openPlayerActivity(url);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlEditText.getText().toString();
                if (!url.isEmpty()) {
                    openSaveM3U8Activity(url);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up view playlist button click listener
        viewPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlaylistActivity();
            }
        });

        // Set up floating action button click listener
        fabCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                M3u8Server();
            }
        });

        // Set up paste/clear button click listener
        pasteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasteMode) {
                    // Access clipboard
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClip().getItemCount() > 0) {
                        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                        String pasteData = item.getText().toString();
                        urlEditText.setText(pasteData);

                        // Provide feedback when paste button is clicked
                        Toast.makeText(MainActivity.this, "Pasted: " + pasteData, Toast.LENGTH_SHORT).show();

                        // Change the button text to "Clear"
                        pasteButton.setText("Clear");

                        // Reset EditText background to original
                        urlEditText.setBackground(originalBackground);

                        // Switch to clear mode
                        isPasteMode = false;
                    } else {
                        Toast.makeText(MainActivity.this, "Clipboard is empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Clear the EditText
                    urlEditText.setText("");

                    // Change the button text back to "Paste"
                    pasteButton.setText("Paste");

                    // Provide feedback when clear button is clicked
                    Toast.makeText(MainActivity.this, "Cleared", Toast.LENGTH_SHORT).show();

                    // Switch back to paste mode
                    isPasteMode = true;
                }
            }
        });

        // Set up settings button click listener
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });

        // Change status bar color to black
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_bg)); // Correctly reference the color resource
    }

    private void M3u8Server() {
        Intent intent = new Intent(MainActivity.this, M3U8ServerActivity.class);
        startActivity(intent);
    }

    private void openPlayerActivity(String url) {
        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }

    private void openSaveM3U8Activity(String url) {
        Intent intent = new Intent(MainActivity.this, SaveM3U8Activity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }

    private void openPlaylistActivity() {
        Intent intent = new Intent(MainActivity.this, PlaylistActivity.class);
        startActivity(intent);
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}



//package com.arrowwouldstudio.m3u8videoplayer;
//
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.arrowwouldstudio.m3u8videoplayer.M3u8Data.M3U8ServerActivity;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//public class MainActivity extends AppCompatActivity {
//
//    private EditText urlEditText;
//    private Button playButton;
//    private Button saveButton;
//    private Button viewPlaylistButton;
//    private Button pasteButton;
//    private FloatingActionButton fabCloud;
//    private Drawable originalBackground;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        urlEditText = findViewById(R.id.url_edit_text);
//        playButton = findViewById(R.id.play_button);
//        saveButton = findViewById(R.id.save_button);
//        viewPlaylistButton = findViewById(R.id.view_playlist_button);
//        pasteButton = findViewById(R.id.paste_button);
//        fabCloud = findViewById(R.id.fab_cloud);
//
//        // Store original background
//        originalBackground = urlEditText.getBackground();
//
//        // Set up play button click listener
//        playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = urlEditText.getText().toString();
//                if (!url.isEmpty()) {
//                    openPlayerActivity(url);
//                } else {
//                    Toast.makeText(MainActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        // Set up save button click listener
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = urlEditText.getText().toString();
//                if (!url.isEmpty()) {
//                    openSaveM3U8Activity(url);
//                } else {
//                    Toast.makeText(MainActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        // Set up view playlist button click listener
//        viewPlaylistButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openPlaylistActivity();
//            }
//        });
//
//        // Set up floating action button click listener
//        fabCloud.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                M3u8Server();
//            }
//        });
//
//        // Set up paste button click listener
//        pasteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Access clipboard
//                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClip().getItemCount() > 0) {
//                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
//                    String pasteData = item.getText().toString();
//                    urlEditText.setText(pasteData);
//                    // Change colors when paste button is clicked
////                    pasteButton.setBackgroundColor(Color.parseColor("#333333")); // Example color change
////                    pasteButton.setTextColor(Color.WHITE);
////                    Toast.makeText(MainActivity.this, "Pasted: " + pasteData, Toast.LENGTH_SHORT).show();
//
//                    // Reset EditText background to original
//                    urlEditText.setBackground(originalBackground);
//                } else {
//                    Toast.makeText(MainActivity.this, "Clipboard is empty", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        // Change status bar color to black
//        Window window = this.getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(getResources().getColor(R.color.color_bg)); // Correctly reference the color resource
//    }
//
//    private void M3u8Server() {
//        Intent intent = new Intent(MainActivity.this, M3U8ServerActivity.class);
//        startActivity(intent);
//    }
//
//    private void openPlayerActivity(String url) {
//        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
//        intent.putExtra("URL", url);
//        startActivity(intent);
//    }
//
//    private void openSaveM3U8Activity(String url) {
//        Intent intent = new Intent(MainActivity.this, SaveM3U8Activity.class);
//        intent.putExtra("URL", url);
//        startActivity(intent);
//    }
//
//    private void openPlaylistActivity() {
//        Intent intent = new Intent(MainActivity.this, PlaylistActivity.class);
//        startActivity(intent);
//    }
//}
//
//


//package com.arrowwouldstudio.m3u8videoplayer;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.arrowwouldstudio.m3u8videoplayer.M3u8Data.M3U8ServerActivity;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//public class MainActivity extends AppCompatActivity {
//
//    private EditText urlEditText;
//    private Button playButton;
//    private Button saveButton;
//    private Button viewPlaylistButton;
//    private FloatingActionButton fabCloud;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        urlEditText = findViewById(R.id.url_edit_text);
//        playButton = findViewById(R.id.play_button);
//        saveButton = findViewById(R.id.save_button);
//        viewPlaylistButton = findViewById(R.id.view_playlist_button);
//        fabCloud = findViewById(R.id.fab_cloud);
//
//        // Set up play button click listener
//        playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = urlEditText.getText().toString();
//                if (!url.isEmpty()) {
//                    openPlayerActivity(url);
//                } else {
//                    Toast.makeText(MainActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        // Set up save button click listener
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = urlEditText.getText().toString();
//                if (!url.isEmpty()) {
//                    openSaveM3U8Activity(url);
//                } else {
//                    Toast.makeText(MainActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        // Set up view playlist button click listener
//        viewPlaylistButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openPlaylistActivity();
//            }
//        });
//
//        // Set up floating action button click listener
//        fabCloud.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              M3u8Server();
//            }
//        });
//
//
//        // Change status bar color to black
//        Window window = this.getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(getResources().getColor(R.color.color_bg)); // Correctly reference the color resource
//    }
//
//    private void M3u8Server() {
//        Intent intent = new Intent(MainActivity.this, M3U8ServerActivity.class);
//        startActivity(intent);
//    }
//
//    private void openPlayerActivity(String url) {
//        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
//        intent.putExtra("URL", url);
//        startActivity(intent);
//    }
//
//    private void openSaveM3U8Activity(String url) {
//        Intent intent = new Intent(MainActivity.this, SaveM3U8Activity.class);
//        intent.putExtra("URL", url);
//        startActivity(intent);
//    }
//
//    private void openPlaylistActivity() {
//        Intent intent = new Intent(MainActivity.this, PlaylistActivity.class);
//        startActivity(intent);
//    }
//}
//


//package com.arrowwouldstudio.m3u8videoplayer;
//
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class MainActivity extends AppCompatActivity {
//
//    private EditText urlEditText;
//    private Button playButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        urlEditText = findViewById(R.id.url_edit_text);
//        playButton = findViewById(R.id.play_button);
//
//        // Set up play button click listener
//        playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = urlEditText.getText().toString();
//                if (!url.isEmpty()) {
//                    openPlayerActivity(url);
//                }
//            }
//        });
//    }
//
//    private void openPlayerActivity(String url) {
//        Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
//        intent.putExtra("URL", url);
//        startActivity(intent);
//    }
//}
