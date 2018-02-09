package com.example.mrticiekurs.firestoregridview;

public class Images {
    private String url;

    public Images(){} //needed for Firebasse

    public Images(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
