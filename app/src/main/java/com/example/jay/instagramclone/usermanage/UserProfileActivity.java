package com.example.jay.instagramclone.usermanage;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jay.instagramclone.Models.User;
import com.example.jay.instagramclone.R;
import com.example.jay.instagramclone.instagramhomepage.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class UserProfileActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 2;
    DatabaseReference mDbRef;
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    FirebaseStorage mStorage;
    StorageReference mStorageRef;
    EditText mName;
    EditText mStatus;
    String mProfileImageLink;
    Button mAddImageBtn;
    Button mSaveProfileBtn;
    Uri imageHoldUri = null;
    View.OnClickListener addImageClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openPictureSelectionAndCrop();
        }
    };

    private void cameraIntent() {

        //CHOOSE CAMERA
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {

        //CHOOSE IMAGE FROM GALLERY
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    View.OnClickListener saveProfileClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveUserProfile();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFirebase();
        showUI();
    }

//    Initializing Firebase instances
    private void initFirebase(){
        mDbRef = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mStorageRef = mStorage.getInstance().getReference().child("user_profile");
    }

//    Showing the UI if user is logged in
    private void showUI() {
        setContentView(R.layout.activity_user_profile);
        mName = (EditText) findViewById(R.id.profile_name_text);
        mAddImageBtn = (Button) findViewById(R.id.add_image_btn);
        mStatus = (EditText) findViewById(R.id.profile_status_text);
        mSaveProfileBtn = (Button) findViewById(R.id.save_profile_btn);
        mAddImageBtn.setOnClickListener(addImageClick);
        mSaveProfileBtn.setOnClickListener(saveProfileClick);
    }

//    Opening picture selection and crop activity
    private void openPictureSelectionAndCrop() {

        //DISPLAY DIALOG TO CHOOSE CAMERA OR GALLERY
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
        builder.setTitle("Add Photo!");

        //SET ITEMS AND THERE LISTENERS
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    //Saving user profile to the database
    private void saveUserProfile() {
        final String name = mName.getText().toString();
        final String status = mStatus.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(status)) {
            if (imageHoldUri != null) {

                final StorageReference mChildStorage = mStorageRef.child(imageHoldUri.getLastPathSegment());
                mChildStorage.putFile(imageHoldUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mProfileImageLink = taskSnapshot.getDownloadUrl().toString();
                        mDbRef = mDbRef.child(mCurrentUser.getUid());
                        User user = new User(mCurrentUser.getUid(),name,mProfileImageLink,status, ServerValue.TIMESTAMP);
                        mDbRef.setValue(user);
                        finish();
                        Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
            else{
                Toast.makeText(this, "Please select a profile picture", Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(this, "Name or Status cannot be empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_FILE: {
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();

                    CropImage.activity(imageUri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .start(this);
                }
                break;
            }
            case REQUEST_CAMERA: {
                if (resultCode == RESULT_OK) {
                    //SAVE URI FROM CAMERA

                    Uri imageUri = data.getData();

                    CropImage.activity(imageUri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .start(this);

                }
                break;
            }

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE: {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    imageHoldUri = result.getUri();
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        }
    }
}
