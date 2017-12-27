package com.example.josepablo.connecttoitueswebservices.model;

/**
 * Created by Jose Pablo on 15/12/2017.
 */

//Class where the data is saved
public class ItunesStuff {

    private String type;
    private String kind;
    private String artistName;
    private String collectionName;
    private String trackName;
    private String artistViewURL;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistViewURL() {
        return artistViewURL;
    }

    public void setArtistViewURL(String artistViewURL) {
        this.artistViewURL = artistViewURL;
    }
}
