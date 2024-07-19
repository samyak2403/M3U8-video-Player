package com.arrowwouldstudio.m3u8videoplayer;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SaveM3U8Activity extends AppCompatActivity {

    private EditText urlEditText;
    private EditText channelNameEditText;
    private ImageView channelIconImageView;
    private Button saveButton;
    private Button editButton;
    private Button deleteButton;
    private String originalUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_m3_u8);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("M3U8 Save");
        }

        urlEditText = findViewById(R.id.url_edit_text);
        channelNameEditText = findViewById(R.id.channel_name_edit_text);
        channelIconImageView = findViewById(R.id.channel_icon_image_view);
        saveButton = findViewById(R.id.save_button);
        editButton = findViewById(R.id.edit_button);
        deleteButton = findViewById(R.id.delete_button);

        // Initialize buttons as invisible
        saveButton.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);

        String url = getIntent().getStringExtra("URL");
        String name = getIntent().getStringExtra("NAME");

        if (url != null) {
            originalUrl = url;
            urlEditText.setText(url);
            if (name != null) {
                channelNameEditText.setText(name);
            }
            // Show edit and delete buttons if URL is provided
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            // Show save button if no URL is provided
            saveButton.setVisibility(View.VISIBLE);
        }

        // Set up save button click listener
        saveButton.setOnClickListener(v -> saveChannelDetails());

        // Set up edit button click listener
        editButton.setOnClickListener(v -> editChannelDetails());

        // Set up delete button click listener
        deleteButton.setOnClickListener(v -> confirmDeleteChannel());

        // Change status bar color to black
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_bg)); // Correctly reference the color resource
    }

    private void saveChannelDetails() {
        String url = urlEditText.getText().toString();
        String channelName = channelNameEditText.getText().toString();

        if (url.isEmpty() || channelName.isEmpty()) {
            Toast.makeText(SaveM3U8Activity.this, "Please enter both URL and channel name", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("M3U8Links", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> links = sharedPreferences.getStringSet("links", new HashSet<>());
        Set<String> newLinks = new HashSet<>(links);
        newLinks.add(channelName + "###" + url);
        editor.putStringSet("links", newLinks);
        editor.apply();

        Toast.makeText(SaveM3U8Activity.this, "Channel details saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void editChannelDetails() {
        String url = urlEditText.getText().toString();
        String channelName = channelNameEditText.getText().toString();

        if (url.isEmpty() || channelName.isEmpty()) {
            Toast.makeText(SaveM3U8Activity.this, "Please enter both URL and channel name", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("M3U8Links", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> links = sharedPreferences.getStringSet("links", new HashSet<>());
        Set<String> newLinks = new HashSet<>(links);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newLinks.removeIf(link -> link.contains(originalUrl));
        } else {
            Iterator<String> iterator = newLinks.iterator();
            while (iterator.hasNext()) {
                String link = iterator.next();
                if (link.contains(originalUrl)) {
                    iterator.remove();
                }
            }
        }
        newLinks.add(channelName + "###" + url);
        editor.putStringSet("links", newLinks);
        editor.apply();

        Toast.makeText(SaveM3U8Activity.this, "Channel details updated", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void confirmDeleteChannel() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Channel")
                .setMessage("Are you sure you want to delete this channel?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteChannelDetails();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteChannelDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("M3U8Links", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> links = sharedPreferences.getStringSet("links", new HashSet<>());
        Set<String> newLinks = new HashSet<>(links);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newLinks.removeIf(link -> link.contains(originalUrl));
        } else {
            Iterator<String> iterator = newLinks.iterator();
            while (iterator.hasNext()) {
                String link = iterator.next();
                if (link.contains(originalUrl)) {
                    iterator.remove();
                }
            }
        }
        editor.putStringSet("links", newLinks);
        editor.apply();

        Toast.makeText(SaveM3U8Activity.this, "Channel details deleted", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

//this code need for error bug fix time
//package com.arrowwouldstudio.m3u8videoplayer;
//
//import android.content.SharedPreferences;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;
//
//public class SaveM3U8Activity extends AppCompatActivity {
//
//    private EditText urlEditText;
//    private EditText channelNameEditText;
//    private ImageView channelIconImageView;
//    private Button saveButton;
//    private Button editButton;
//    private String originalUrl;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_save_m3_u8);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setTitle("M3U8 Save");
//        }
//
//        urlEditText = findViewById(R.id.url_edit_text);
//        channelNameEditText = findViewById(R.id.channel_name_edit_text);
//        channelIconImageView = findViewById(R.id.channel_icon_image_view);
//        saveButton = findViewById(R.id.save_button);
//        editButton = findViewById(R.id.edit_button);
//
//        // Initialize buttons as invisible
//        saveButton.setVisibility(View.GONE);
//        editButton.setVisibility(View.GONE);
//
//        String url = getIntent().getStringExtra("URL");
//        String name = getIntent().getStringExtra("NAME");
//
//        if (url != null) {
//            originalUrl = url;
//            urlEditText.setText(url);
//            if (name != null) {
//                channelNameEditText.setText(name);
//            }
//            // Show edit button if URL is provided
//            editButton.setVisibility(View.VISIBLE);
//        } else {
//            // Show save button if no URL is provided
//            saveButton.setVisibility(View.VISIBLE);
//        }
//
//        // Set up save button click listener
//        saveButton.setOnClickListener(v -> saveChannelDetails());
//
//        // Set up edit button click listener
//        editButton.setOnClickListener(v -> editChannelDetails());
//
//        // Change status bar color to black
//        Window window = this.getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(getResources().getColor(R.color.color_bg)); // Correctly reference the color resource
//    }
//
//    private void saveChannelDetails() {
//        String url = urlEditText.getText().toString();
//        String channelName = channelNameEditText.getText().toString();
//
//        if (url.isEmpty() || channelName.isEmpty()) {
//            Toast.makeText(SaveM3U8Activity.this, "Please enter both URL and channel name", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        SharedPreferences sharedPreferences = getSharedPreferences("M3U8Links", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        Set<String> links = sharedPreferences.getStringSet("links", new HashSet<>());
//        Set<String> newLinks = new HashSet<>(links);
//        newLinks.add(channelName + "###" + url);
//        editor.putStringSet("links", newLinks);
//        editor.apply();
//
//        Toast.makeText(SaveM3U8Activity.this, "Channel details saved", Toast.LENGTH_SHORT).show();
//        finish();
//    }
//
//    private void editChannelDetails() {
//        String url = urlEditText.getText().toString();
//        String channelName = channelNameEditText.getText().toString();
//
//        if (url.isEmpty() || channelName.isEmpty()) {
//            Toast.makeText(SaveM3U8Activity.this, "Please enter both URL and channel name", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        SharedPreferences sharedPreferences = getSharedPreferences("M3U8Links", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        Set<String> links = sharedPreferences.getStringSet("links", new HashSet<>());
//        Set<String> newLinks = new HashSet<>(links);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            newLinks.removeIf(link -> link.contains(originalUrl));
//        } else {
//            Iterator<String> iterator = newLinks.iterator();
//            while (iterator.hasNext()) {
//                String link = iterator.next();
//                if (link.contains(originalUrl)) {
//                    iterator.remove();
//                }
//            }
//        }
//        newLinks.add(channelName + "###" + url);
//        editor.putStringSet("links", newLinks);
//        editor.apply();
//
//        Toast.makeText(SaveM3U8Activity.this, "Channel details updated", Toast.LENGTH_SHORT).show();
//        finish();
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
//}



//package com.arrowwouldstudio.m3u8videoplayer;
//
//import android.content.SharedPreferences;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;
//
//public class SaveM3U8Activity extends AppCompatActivity {
//
//    private EditText urlEditText;
//    private EditText channelNameEditText;
//    private ImageView channelIconImageView;
//    private Button saveButton;
//    private Button editButton;
//    private String originalUrl;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_save_m3_u8);
//
//        urlEditText = findViewById(R.id.url_edit_text);
//        channelNameEditText = findViewById(R.id.channel_name_edit_text);
//        channelIconImageView = findViewById(R.id.channel_icon_image_view);
//        saveButton = findViewById(R.id.save_button);
//        editButton = findViewById(R.id.edit_button);
//
//        // Initialize buttons as invisible
//        saveButton.setVisibility(View.GONE);
//        editButton.setVisibility(View.GONE);
//
//        String url = getIntent().getStringExtra("URL");
//        String name = getIntent().getStringExtra("NAME");
//
//        if (url != null) {
//            originalUrl = url;
//            urlEditText.setText(url);
//            if (name != null) {
//                channelNameEditText.setText(name);
//            }
//            // Show edit button if URL is provided
//            editButton.setVisibility(View.VISIBLE);
//        } else {
//            // Show save button if no URL is provided
//            saveButton.setVisibility(View.VISIBLE);
//        }
//
//        // Set up save button click listener
//        saveButton.setOnClickListener(v -> saveChannelDetails());
//
//        // Set up edit button click listener
//        editButton.setOnClickListener(v -> editChannelDetails());
//    }
//
//    private void saveChannelDetails() {
//        String url = urlEditText.getText().toString();
//        String channelName = channelNameEditText.getText().toString();
//
//        if (url.isEmpty() || channelName.isEmpty()) {
//            Toast.makeText(SaveM3U8Activity.this, "Please enter both URL and channel name", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        SharedPreferences sharedPreferences = getSharedPreferences("M3U8Links", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        Set<String> links = sharedPreferences.getStringSet("links", new HashSet<>());
//        Set<String> newLinks = new HashSet<>(links);
//        newLinks.add(channelName + "###" + url);
//        editor.putStringSet("links", newLinks);
//        editor.apply();
//
//        Toast.makeText(SaveM3U8Activity.this, "Channel details saved", Toast.LENGTH_SHORT).show();
//        finish();
//    }
//
//    private void editChannelDetails() {
//        String url = urlEditText.getText().toString();
//        String channelName = channelNameEditText.getText().toString();
//
//        if (url.isEmpty() || channelName.isEmpty()) {
//            Toast.makeText(SaveM3U8Activity.this, "Please enter both URL and channel name", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        SharedPreferences sharedPreferences = getSharedPreferences("M3U8Links", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        Set<String> links = sharedPreferences.getStringSet("links", new HashSet<>());
//        Set<String> newLinks = new HashSet<>(links);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            newLinks.removeIf(link -> link.contains(originalUrl));
//        } else {
//            Iterator<String> iterator = newLinks.iterator();
//            while (iterator.hasNext()) {
//                String link = iterator.next();
//                if (link.contains(originalUrl)) {
//                    iterator.remove();
//                }
//            }
//        }
//        newLinks.add(channelName + "###" + url);
//        editor.putStringSet("links", newLinks);
//        editor.apply();
//
//        Toast.makeText(SaveM3U8Activity.this, "Channel details updated", Toast.LENGTH_SHORT).show();
//        finish();
//    }
//}



//package com.arrowwouldstudio.m3u8videoplayer;
//
//import android.content.SharedPreferences;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;
//
//public class SaveM3U8Activity extends AppCompatActivity {
//
//    private EditText urlEditText;
//    private EditText channelNameEditText;
//    private ImageView channelIconImageView;
//    private Button saveButton;
//    private Button editButton;
//    private String originalUrl;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_save_m3_u8);
//
//        urlEditText = findViewById(R.id.url_edit_text);
//        channelNameEditText = findViewById(R.id.channel_name_edit_text);
//        channelIconImageView = findViewById(R.id.channel_icon_image_view);
//        saveButton = findViewById(R.id.save_button);
//        editButton = findViewById(R.id.edit_button);
//
//        // Initialize buttons as invisible
//        saveButton.setVisibility(View.GONE);
//        editButton.setVisibility(View.GONE);
//
//        String url = getIntent().getStringExtra("URL");
//        String name = getIntent().getStringExtra("NAME");
//
//        if (url != null) {
//            originalUrl = url;
//            urlEditText.setText(url);
//            if (name != null) {
//                channelNameEditText.setText(name);
//            }
//            // Show edit button if URL is provided
//            editButton.setVisibility(View.VISIBLE);
//        } else {
//            // Show save button if no URL is provided
//            saveButton.setVisibility(View.VISIBLE);
//        }
//
//        // Set up save button click listener
//        saveButton.setOnClickListener(v -> saveChannelDetails());
//
//        // Set up edit button click listener
//        editButton.setOnClickListener(v -> editChannelDetails());
//    }
//
//    private void saveChannelDetails() {
//        String url = urlEditText.getText().toString();
//        String channelName = channelNameEditText.getText().toString();
//
//        SharedPreferences sharedPreferences = getSharedPreferences("M3U8Links", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        Set<String> links = sharedPreferences.getStringSet("links", new HashSet<>());
//        Set<String> newLinks = new HashSet<>(links);
//        newLinks.add(channelName + "###" + url);
//        editor.putStringSet("links", newLinks);
//        editor.apply();
//        finish();
//    }
//
//    private void editChannelDetails() {
//        String url = urlEditText.getText().toString();
//        String channelName = channelNameEditText.getText().toString();
//
//        SharedPreferences sharedPreferences = getSharedPreferences("M3U8Links", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        Set<String> links = sharedPreferences.getStringSet("links", new HashSet<>());
//        Set<String> newLinks = new HashSet<>(links);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            newLinks.removeIf(link -> link.contains(originalUrl));
//        } else {
//            Iterator<String> iterator = newLinks.iterator();
//            while (iterator.hasNext()) {
//                String link = iterator.next();
//                if (link.contains(originalUrl)) {
//                    iterator.remove();
//                }
//            }
//        }
//        newLinks.add(channelName + "###" + url);
//        editor.putStringSet("links", newLinks);
//        editor.apply();
//        finish();
//    }
//}
