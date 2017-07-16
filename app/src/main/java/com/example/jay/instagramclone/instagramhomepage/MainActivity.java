package com.example.jay.instagramclone.instagramhomepage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.jay.instagramclone.LoginActivity;
import com.example.jay.instagramclone.R;
import com.example.jay.instagramclone.instagrampostinfo.UserPostSend;
import com.example.jay.instagramclone.usermanage.UserProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mDbRef = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth;
    ProgressDialog mDialog;
    ViewPager mPager;
    ImageView mToolbarCam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        mDialog.setMessage("Please wait");
        mDialog.show();

        if(currentUser!=null){
            DatabaseReference ref = mDbRef.child("users");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(currentUser.getUid())) {
                        mDialog.dismiss();
                        finish();
                        Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                        startActivity(intent);
                    } else {
                        updateUI();
                        mDialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {



                }
            });

        }
        else {
            mDialog.dismiss();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void updateUI() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        mToolbarCam = (ImageView) findViewById(R.id.cam_icon);
        mToolbarCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToPostSend();
            }
        });
        mPager = (ViewPager) findViewById(R.id.homePager);
        mPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
    }

    private void moveToPostSend() {
        startActivity(new Intent(this, UserPostSend.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_menu : {
                signOut();
                break;
            }
        }
        return true;
    };

    private void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
