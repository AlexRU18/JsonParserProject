package com.alexsprod.jsonparserproject.items;

public class Item {
    private String id;
    private String title;
    private String fulltext;
    private String link;
    private String doptext;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return fulltext;
    }

    public void setText(String fulltext) {
        this.fulltext = fulltext;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDopText() {
        return doptext;
    }

    public void setDopText(String doptext) {
        this.doptext = doptext;
    }

}