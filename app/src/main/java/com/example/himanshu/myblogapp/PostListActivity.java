package com.example.himanshu.myblogapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostListActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRefernce;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseRefernce=mDatabase.getReference().child("Blog");
        mDatabaseRefernce.keepSynced(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

      switch (item.getItemId()){
          case R.id.add_posts:
              if(mUser!=null&&mAuth!=null){
                  Intent intent=new Intent(PostListActivity.this,AddPostActivity.class);
                  startActivity(intent);
                  finish();
              }
                  break;

          case R.id.sign_out:
              if(mUser!=null&&mAuth!=null){
                  mAuth.signOut();
                  Intent intent=new Intent(PostListActivity.this,MainActivity.class);
                  startActivity(intent);
                  finish();
              }
              break;


      }
        return super.onOptionsItemSelected(item);
    }

}
