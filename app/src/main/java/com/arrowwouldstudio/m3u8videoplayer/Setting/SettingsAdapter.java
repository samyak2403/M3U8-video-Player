package com.arrowwouldstudio.m3u8videoplayer.Setting;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.arrowwouldstudio.m3u8videoplayer.PandTC.PrivacyPolicyActivity;
import com.arrowwouldstudio.m3u8videoplayer.PandTC.TermsandConditionsActivity;
import com.arrowwouldstudio.m3u8videoplayer.R;
import com.arrowwouldstudio.m3u8videoplayer.databinding.DialogGuideBinding;
import com.arrowwouldstudio.m3u8videoplayer.databinding.ItemSettingsBinding;

import java.util.ArrayList;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {
    private ArrayList<SettingsModel> list;
    private Context context;

    public SettingsAdapter(ArrayList<SettingsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSettingsBinding binding;

        public ViewHolder(ItemSettingsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SettingsModel model, int position, Context context) {
            binding.settingsTitle.setText(model.getTitle());
            binding.settingsDesc.setText(model.getDesc());

            binding.getRoot().setOnClickListener(view -> {
                switch (position) {
                    case 0: {
                        // how to use 1st item
                        Dialog dialog = new Dialog(context);
                        DialogGuideBinding dialogBinding = DialogGuideBinding.inflate(((Activity) context).getLayoutInflater());
                        dialogBinding.okayBtn.setOnClickListener(v -> dialog.dismiss());
                        dialog.setContentView(dialogBinding.getRoot());

                        if (dialog.getWindow() != null) {
                            dialog.getWindow().setLayout(
                                    ActionBar.LayoutParams.MATCH_PARENT,
                                    ActionBar.LayoutParams.WRAP_CONTENT
                            );
                        }

                        dialog.show();
                        break;
                    }
                    case 2: {

                        Intent intent = new Intent(context, TermsandConditionsActivity.class);
                        context.startActivity(intent);


                        break;
                    }
                    case 3: {
                        Intent intent = new Intent(context, PrivacyPolicyActivity.class);
                        context.startActivity(intent);
                        break;
                    }
                    case 4: {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
                        intent.putExtra(Intent.EXTRA_TEXT, "My App is so cool, please download it: https://play.google.com/store/apps/details?id=" + context.getPackageName());
                        context.startActivity(intent);
                        break;
                    }
                    case 5: {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName()));
                        context.startActivity(intent);
                        break;
                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemSettingsBinding binding = ItemSettingsBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position), position, context);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
