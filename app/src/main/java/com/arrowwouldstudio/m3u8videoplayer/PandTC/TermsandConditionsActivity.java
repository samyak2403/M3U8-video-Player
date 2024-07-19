package com.arrowwouldstudio.m3u8videoplayer.PandTC;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.arrowwouldstudio.m3u8videoplayer.R;

public class TermsandConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_condtions); // Set the content view first

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Terms & Conditions");
        }

        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();

        // Enable JavaScript (optional)
        webSettings.setJavaScriptEnabled(true);

        // Load URLs opened in the WebView itself
        webView.setWebViewClient(new WebViewClient());

        // Enable full-screen mode support (optional, for video playback)
        webView.setWebChromeClient(new WebChromeClient());

        // Load the about.html file from the assets directory
        webView.loadUrl("file:///android_asset/Terms&Conditions.html");

        // Change status bar color to black
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_bg)); // Correctly reference the color resource
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
