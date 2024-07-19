package com.arrowwouldstudio.m3u8videoplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    private final List<Channel> channelList;
    private final Context context;

    public PlaylistAdapter(List<Channel> channelList, Context context) {
        this.channelList = channelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_channel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Channel channel = channelList.get(position);
        holder.bind(channel);
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView channelNameTextView;
        private final TextView channelUrlTextView;
        private final Button editButton;
        private final RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            channelNameTextView = itemView.findViewById(R.id.channel_name_text_view);
            channelUrlTextView = itemView.findViewById(R.id.channel_url_text_view);
            editButton = itemView.findViewById(R.id.edit_button);
            relativeLayout = itemView.findViewById(R.id.item_layout);
        }

        public void bind(Channel channel) {
            channelNameTextView.setText(channel.getName());
            channelUrlTextView.setText(channel.getUrl());

            editButton.setOnClickListener(v -> {
                Intent intent = new Intent(context, SaveM3U8Activity.class);
                intent.putExtra("URL", channel.getUrl());
                intent.putExtra("NAME", channel.getName());
                context.startActivity(intent);
            });

            relativeLayout.setOnClickListener(v -> {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("URL", channel.getUrl());
                context.startActivity(intent);
            });
        }
    }
}
