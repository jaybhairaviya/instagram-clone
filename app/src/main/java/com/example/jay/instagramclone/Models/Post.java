package com.example.jay.instagramclone.Models;

import java.util.Objects;

/**
 * Created by Jay on 7/10/2017.
 */

public class Post {
    private Author author;
    private String imageUrl;
    private String caption;
    private Object timestamp;

    public Post() {
    }

    public Post(Author author, String imageUrl, String caption, Object timestamp) {
        this.author = author;
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.timestamp = timestamp;
    }

    public Author getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public Object getTimestamp() {
        return timestamp;
    }
}
