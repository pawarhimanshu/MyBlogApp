package com.example.himanshu.myblogapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPostActivity extends AppCompatActivity {
    private ImageView mImage;
    private EditText mtitle;
    private Uri imageUri;
    private EditText mdesc;
    private Button mSubmit;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialog mProgress;
    public static final int GALLERY_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mProgress=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Blog");

        mSubmit=findViewById(R.id.submitpostId);
        mImage=findViewById(R.id.postimageId);
        mtitle=findViewById(R.id.posttitleId);
        mdesc=findViewById(R.id.postdescId);

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST_CODE);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startPosting();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST_CODE&&resultCode==RESULT_OK){
            imageUri=data.getData();
            mImage.setImageURI(imageUri);
        }
    }

    private void startPosting() {
        mProgress.setMessage("Posting to Blog........");
        mProgress.show();
        String titleVal=mtitle.getText().toString().trim();
        String descVal=mdesc.getText().toString().trim();

        if(!TextUtils.isEmpty(titleVal)&&!TextUtils.isEmpty(descVal)){
           Blog blog=new Blog("title","description","imageUrl","time","userId");
           mDatabaseReference.setValue(blog).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                   Toast.makeText(getApplicationContext(),"Item Added",Toast.LENGTH_LONG).show();
                   mProgress.dismiss();
               }
           });
        }
    }
}
