package com.example.jay.instagramclone.Models;

/**
 * Created by Jay on 7/10/2017.
 */

public class User {
    private String user_id;
    private String profile_image;
    private String display_name;
    private String description;
    private Object timestamp;

    public User(){

    }

    public User(String user_id, String displayName, String profile_image,String description,Object timestamp){
        this.user_id = user_id;
        this.profile_image = profile_image;
        this.display_name = displayName;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getUserid() {
        return user_id;
    }

    public String getProfileimage() {
        return profile_image;
    }

    public String getDescription() {
        return description;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public String getDisplay_name() {
        return display_name;
    }
}
