package com.example.himanshu.myblogapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CreateAccountActivity extends AppCompatActivity {
   private  Uri resultUri=null;
   private EditText fname;
   private EditText lname;
   private EditText email;
   private EditText password;
   private Button createAcc;
   private DatabaseReference mdatabaseReference;
   private FirebaseDatabase mDatabase;
   private StorageReference mfirebaseStorage;
   private FirebaseAuth mAuth;
   private ProgressDialog progressDialog;
   private ImageButton profile;
   private static int  GalleryIntent=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        fname=findViewById(R.id.FnameId);
        lname=findViewById(R.id.LnameId);
        email=findViewById(R.id.UemailId);
        password=findViewById(R.id.UpasswordId);
        createAcc=findViewById(R.id.CreateUserId);
        profile=findViewById(R.id.profilePic);

        mDatabase=FirebaseDatabase.getInstance();
        mdatabaseReference=mDatabase.getReference().child("Users");
        mAuth=FirebaseAuth.getInstance();
        mfirebaseStorage=FirebaseStorage.getInstance().getReference().child("Blog_Profile_Pics");

        progressDialog=new ProgressDialog(this);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery_intent=new Intent();
                gallery_intent.setAction(Intent.ACTION_GET_CONTENT);
                gallery_intent.setType("image/*");
                startActivityForResult(gallery_intent,GalleryIntent);
            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });

    }


    private void CreateNewAccount() {
        final String firstname=fname.getText().toString().trim();
        final String lastname=lname.getText().toString().trim();
        String Email=email.getText().toString().trim();
        String pwd=password.getText().toString().trim();

        if(firstname!=null&&lastname!=null&&Email!=null&&pwd!=null) {
            progressDialog.setMessage("Creating Account.....");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(Email, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    if (authResult != null) {

                            StorageReference imagepath = mfirebaseStorage.child("Blog_Profile_Pics").child(resultUri.getLastPathSegment());

                        imagepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    String Userid = mAuth.getCurrentUser().getUid();
                                    DatabaseReference currentUserDb = mdatabaseReference.child(Userid);
                                    currentUserDb.child("FirstName").setValue(firstname);
                                    currentUserDb.child("LastName").setValue(lastname);
                                    currentUserDb.child("Image").setValue(resultUri.toString());
                                    progressDialog.dismiss();

                                    Intent intent = new Intent(CreateAccountActivity.this, PostListActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                        });

                    }
                }
            });
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryIntent&&resultCode==RESULT_OK){
            Uri imageUri=data.getData();

            CropImage.activity(imageUri).setAspectRatio(1,1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                 resultUri = result.getUri();
                profile.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
