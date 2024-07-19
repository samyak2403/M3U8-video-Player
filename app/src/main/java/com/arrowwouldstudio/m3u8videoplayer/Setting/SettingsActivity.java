package com.arrowwouldstudio.m3u8videoplayer.Setting;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.arrowwouldstudio.m3u8videoplayer.R;
import com.arrowwouldstudio.m3u8videoplayer.databinding.ActivitySettingsBinding;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    private ArrayList<SettingsModel> list;
    private SettingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("M3U8 Setting");
        }

        list = new ArrayList<>();
        adapter = new SettingsAdapter(list, this);
        binding.settingsRecyclerView.setAdapter(adapter);

        list.add(new SettingsModel("How to use", "Know how to download statuses"));
        list.add(new SettingsModel("Save in Folder", "/internalstorage/Documents/" + getString(R.string.app_name)));
        list.add(new SettingsModel("Terms & Conditions", "Read Our Terms & Conditions"));
        list.add(new SettingsModel("Privacy Policy", "Read Our Terms & Conditions"));
        list.add(new SettingsModel("Share", "Sharing is caring"));
        list.add(new SettingsModel("Rate Us", "Please support our work by rating on PlayStore"));


        // Change status bar color to black
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_bg)); // Correctly reference the color resource
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
