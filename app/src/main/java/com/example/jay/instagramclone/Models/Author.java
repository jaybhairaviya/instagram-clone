package com.example.jay.instagramclone.Models;

/**
 * Created by Jay on 7/11/2017.
 */

public class Author {
    private String user_id;
    private String profile_image;
    private String display_name;

    public Author(){

    }

    public Author(String user_id, String profile_image, String display_name) {
        this.user_id = user_id;
        this.profile_image = profile_image;
        this.display_name = display_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public String getDisplay_name() {
        return display_name;
    }
}
