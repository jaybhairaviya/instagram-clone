package com.example.jay.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jay.instagramclone.instagramhomepage.MainActivity;
import com.example.jay.instagramclone.usermanage.ForgetPassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button mRegister;
    Button mLogin;
    EditText mUsername;
    EditText mPassword;
    TextView mForgetPassword;
    ProgressDialog mDialog;
    View.OnClickListener mLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginUser();
        }
    };

    private void loginUser() {
        mDialog.show();
        String username = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mDialog.dismiss();
                                Intent login = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(login);
                                finish();
                            }
                            else {
                                mDialog.dismiss();
                                Toast.makeText(LoginActivity.this, task.getException()+"", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else {
            Toast.makeText(LoginActivity.this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    View.OnClickListener mForgetPwdListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,ForgetPassword.class);
            startActivity(intent);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Logging in");
        if (currentUser != null) {
            Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            updateUI();
        }
    }

    private void updateUI() {
        setContentView(R.layout.activity_login);
        mRegister = (Button) findViewById(R.id.register);
        mLogin = (Button) findViewById(R.id.login);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mForgetPassword = (TextView) findViewById(R.id.forget_password);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });
        mLogin.setOnClickListener(mLoginClickListener);
        mForgetPassword.setOnClickListener(mForgetPwdListener);
    }
}
