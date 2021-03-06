package com.example.himanshu.myblogapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Blog> blogList;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mdatabaseReference;


    public BlogRecyclerAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }


    @NonNull
    @Override
    public BlogRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_row,viewGroup,false);
        mAuth= FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mdatabaseReference=mDatabase.getReference().child("Users");
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogRecyclerAdapter.ViewHolder viewHolder, int i) {

       Blog blog=blogList.get(i);
       String imageUrl=null;

       viewHolder.Title.setText(blog.getTitle());
       viewHolder.Desc.setText(blog.getDesc());

       java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
       String formatteddate = dateFormat.format(new Date(Long.valueOf(blog.getTime())).getTime());
       viewHolder.Time.setText(formatteddate);

         imageUrl=blog.getImage();
        Picasso.get().load(imageUrl).into(viewHolder.Image);

        String Userid = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDb = mdatabaseReference.child(Userid);



        viewHolder.username.setText("Posted by : "+blog.getUserName());

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView Title;
        public TextView Desc;
        public TextView Time;
        public ImageView Image;
        String userId;

        public ViewHolder(View view,Context ctx) {
            super(view);
            ctx=context;

            username=view.findViewById(R.id.UserName_Id);
            Title=view.findViewById(R.id.PostTitleList);
            Desc=view.findViewById(R.id.PostDescription);
            Time=view.findViewById(R.id.PostDate);
            Image=view.findViewById(R.id.PostImageList);
            userId=null;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
