package com.example.raahymasif.cmpt498;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.raahymasif.cmpt498.Model.CreatePosts;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class FindMatchActivity extends AppCompatActivity {

    private RecyclerView mPostList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_findmatch);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");
        mDatabase.keepSynced(true);

        mPostList = (RecyclerView)findViewById(R.id.myRecycleView);
        mPostList.setHasFixedSize(true);
        mPostList.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<CreatePosts,PostViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CreatePosts,PostViewHolder>
                (CreatePosts.class,R.layout.post_row,PostViewHolder.class,mDatabase){
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, CreatePosts model, int position) {
                viewHolder.setInfo(model.getInfo());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setPlayers(model.getPlayers());
                viewHolder.setSport(model.getSport());
            }

        };

        mPostList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public PostViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setInfo(String info){
            TextView post_info = (TextView)mView.findViewById(R.id.post_info);
            post_info.setText(info);
        }

        public void setLocation(String location){
            TextView post_location = (TextView)mView.findViewById(R.id.post_location);
            post_location.setText(location);
        }

        public void setPlayers(String players){
            TextView post_players = (TextView)mView.findViewById(R.id.post_players);
            post_players.setText(players);
        }

        public void setSport(String sport){
            TextView post_sport = (TextView)mView.findViewById(R.id.post_sport);
            post_sport.setText(sport);
        }
    }
}
