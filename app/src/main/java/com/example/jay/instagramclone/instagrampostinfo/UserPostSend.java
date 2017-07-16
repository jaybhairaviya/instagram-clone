package com.example.jay.instagramclone.instagrampostinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jay.instagramclone.FirebaseUtil;
import com.example.jay.instagramclone.Models.Post;
import com.example.jay.instagramclone.R;
import com.example.jay.instagramclone.instagramhomepage.MainActivity;
import com.example.jay.instagramclone.usermanage.UserProfileActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class UserPostSend extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 2;
    EditText mCaption;
    Button mAddPost;
    Button mSubmit;
    Uri imageHoldUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post_send);
        mCaption = (EditText) findViewById(R.id.caption_text);
        mAddPost = (Button) findViewById(R.id.add_image_btn1);
        mSubmit = (Button) findViewById(R.id.submit_post_btn);
        mAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPictureSelectionAndCrop();
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPost();
            }
        });
    }

    private void uploadPost() {
        final String caption = mCaption.getText().toString();
        if (!TextUtils.isEmpty(caption)) {
            if (imageHoldUri != null) {

                final StorageReference mChildStorage = FirebaseUtil.getPostStorageRef().child(FirebaseUtil.getCurrentUserId()).child(imageHoldUri.getLastPathSegment());
                mChildStorage.putFile(imageHoldUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String dlLink = taskSnapshot.getDownloadUrl().toString();
                        Post post = new Post(FirebaseUtil.getAuthor(),dlLink,caption, ServerValue.TIMESTAMP);
                        FirebaseUtil.getPostsRef().setValue(post);
                        finish();
                        Intent intent = new Intent(UserPostSend.this, MainActivity.class);
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

    //    Opening picture selection and crop activity
    private void openPictureSelectionAndCrop() {
        //DISPLAY DIALOG TO CHOOSE CAMERA OR GALLERY
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UserPostSend.this);
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

    private void cameraIntent() {

        //CHOOSE CAMERA
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Log.e("asd","galery");
        //CHOOSE IMAGE FROM GALLERY
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SELECT_FILE : {
                Log.e("asd","gallery returned");
                if(resultCode == RESULT_OK){Log.e("asd","ok");
                    Uri imageUri = data.getData();

                    CropImage.activity(imageUri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .start(this);
                }
                Log.e("asd","not ok");
                break;
            }
            case REQUEST_CAMERA : {
                if(resultCode == RESULT_OK){
                    Uri imageUri = data.getData();

                    CropImage.activity(imageUri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .start(this);
                }
                break;
            }

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE :{
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
