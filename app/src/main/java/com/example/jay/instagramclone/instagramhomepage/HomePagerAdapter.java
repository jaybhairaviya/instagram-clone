package com.example.jay.instagramclone.instagramhomepage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jay on 7/4/2017.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : {
                return new PopularPosts();
            }
            case 1 : {
                return new InstaPost();
            }
            default : {
                return new FollowingUsers();
            }
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 : {
                return "POPULAR";
            }

            case 1:{
                return "POSTS";
            }

            default:{
                return "FOLLOWING";
            }
        }
    }
}
