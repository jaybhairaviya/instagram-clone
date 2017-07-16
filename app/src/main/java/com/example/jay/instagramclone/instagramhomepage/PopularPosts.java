package com.example.jay.instagramclone.instagramhomepage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jay.instagramclone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularPosts extends Fragment {


    public PopularPosts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_posts, container, false);
    }

}
