package com.example.himanshu.myblogapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PostListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BlogRecyclerAdapter blogRecyclerAdapter;
    private List<Blog> blogList;
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
        blogList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

    @Override
    protected void onStart() {
        mDatabaseRefernce.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Blog blog=dataSnapshot.getValue(Blog.class);
                blogList.add(blog);
                Collections.reverse(blogList);
                blogRecyclerAdapter = new BlogRecyclerAdapter(PostListActivity.this,blogList);
                recyclerView.setAdapter(blogRecyclerAdapter);
                blogRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        super.onStart();

    }
}

