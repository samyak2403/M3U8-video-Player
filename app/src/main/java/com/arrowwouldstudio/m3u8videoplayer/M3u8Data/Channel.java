package com.arrowwouldstudio.m3u8videoplayer.M3u8Data;
public class Channel {
    private String name;
    private String link;

    // Default constructor required for calls to DataSnapshot.getValue(Channel.class)
    public Channel() {
    }

    public Channel(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}


