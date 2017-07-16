package com.example.jay.instagramclone.usermanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jay.instagramclone.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    Button mSubmit;
    FirebaseAuth mAuth;
    EditText mEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mSubmit = (Button) findViewById(R.id.submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail = (EditText)findViewById(R.id.email);
                String email = mEmail.getText().toString().trim();
                mAuth = FirebaseAuth.getInstance();
                mAuth.sendPasswordResetEmail(email);
            }
        });
    }
}
