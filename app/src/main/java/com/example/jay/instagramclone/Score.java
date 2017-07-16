package com.example.jay.instagramclone;

import java.io.Serializable;

/**
 * Created by Jay on 6/25/2017.
 */

public class Score implements Serializable {
    public String mteamName;
    public int score;

    public Score(String teamName,int score){
        mteamName = teamName;
        this.score = score;
    }
}
