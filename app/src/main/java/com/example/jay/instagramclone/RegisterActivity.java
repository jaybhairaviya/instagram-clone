package com.example.jay.instagramclone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jay.instagramclone.instagramhomepage.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mDbRef;
    Button mRegisterBtn;
    EditText mUsername;
    EditText mPassword;

    View.OnClickListener mRegisterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String username = mUsername.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            mAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                finish();
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Toast.makeText(this, "Logout before registration", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            finish();
            startActivity(intent);
        }
        else {
            updateUI();
        }
    }

//    Updating UI when to provide with the registration screen
    private void updateUI() {
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_register);
        mRegisterBtn = (Button) findViewById(R.id.register);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mDbRef = FirebaseDatabase.getInstance().getReference();
        mRegisterBtn.setOnClickListener(mRegisterClick);
    }
}
