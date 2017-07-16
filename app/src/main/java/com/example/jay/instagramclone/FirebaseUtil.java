package com.example.jay.instagramclone;

import com.example.jay.instagramclone.Models.Author;
import com.example.jay.instagramclone.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Jay on 7/10/2017.
 */

public class FirebaseUtil {

    private static Author mAuthor;

    public static DatabaseReference getBaseRef(){
        return FirebaseDatabase.getInstance().getReference();
    }

    public static StorageReference getBaseStorageRef(){
        return FirebaseStorage.getInstance().getReference();
    }

    public static String getCurrentUserId(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            return user.getUid();
        }
        return null;
    }

    public static Author getAuthor(){
        DatabaseReference ref = getUsersRef().child(getCurrentUserId());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 User user = dataSnapshot.getValue(User.class);
                mAuthor = new Author(user.getUserid(),user.getProfileimage(),user.getDisplay_name());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return mAuthor;
    }


    public static FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static DatabaseReference getPostsRef(){
        return getBaseRef().child("posts");
    }

    public static DatabaseReference getLikesRef(){
        return getBaseRef().child("likes");
    }

    public static DatabaseReference getCommentsRef(){
        return getBaseRef().child("comments");
    }

    public static DatabaseReference getFollowersRef(){
        return getBaseRef().child("followers");
    }

    public static DatabaseReference getUsersRef(){
        return getBaseRef().child("users");
    }

    public static StorageReference getPostStorageRef(){
        return getBaseStorageRef().child("posts");
    }

    public static StorageReference getUsersStorageRef(){
        return getBaseStorageRef().child("users");
    }
}
