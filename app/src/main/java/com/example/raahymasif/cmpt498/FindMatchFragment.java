package com.example.raahymasif.cmpt498;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

<<<<<<< HEAD
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FindMatchFragment extends Fragment {
    private TextView textView;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private TextView hello;
=======
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.example.raahymasif.cmpt498.Model.CreatePosts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class FindMatchFragment extends Fragment {
    //---------------
    private RecyclerView mPostList;
    private DatabaseReference mDatabase;
    //----------------

    public FindMatchFragment() {
        //empty constructor
    }

    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_findmatch);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");
        mDatabase.keepSynced(true);

        mPostList = (RecyclerView)findViewById(R.id.myRecycleView);
        mPostList.setHasFixedSize(true);
        mPostList.setLayoutManager(new LinearLayoutManager(this ));

    }*/
>>>>>>> master

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
<<<<<<< HEAD
        View rootview = inflater.inflate(R.layout.fragment_findmatch, container, false);
        hello = (TextView) rootview.findViewById(R.id.name_view);


        // Initialize the view

        // this is in the onCreate method
        // initlaize the firebase realtime database variables
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference();

        //databse listener
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.child("User").child("karn").child("Fname").getValue(String.class);

                hello.setText("Hello " + s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                hello.setText(databaseError.toString());
            }
        });
        return rootview;
    }


}

=======

        View view = inflater.inflate(R.layout.fragment_findmatch, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");
        mDatabase.keepSynced(true);

        mPostList = (RecyclerView)view.findViewById(R.id.myRecycleView);
        mPostList.setHasFixedSize(true);
        //mPostList.setLayoutManager(new LinearLayoutManager(this ));
        mPostList.setLayoutManager(new LinearLayoutManager(getContext() ));

        //Intent findMatch = new Intent(getActivity(), FindMatchActivity.class);
        //startActivity(findMatch);

        return view;
    }

    //------------------------------
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<CreatePosts,FindMatchActivity.PostViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CreatePosts,FindMatchActivity.PostViewHolder>
                (CreatePosts.class,R.layout.post_row,FindMatchActivity.PostViewHolder.class,mDatabase){
            @Override
            protected void populateViewHolder(FindMatchActivity.PostViewHolder viewHolder, CreatePosts model, int position) {
                viewHolder.setInfo(model.getInfo());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setPlayers(model.getPlayers());
                viewHolder.setSport(model.getSport());
            }

        };

        mPostList.setAdapter(firebaseRecyclerAdapter);

    }

    /*public static class PostViewHolder extends RecyclerView.ViewHolder{

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
    }*/
}
 //------------------
>>>>>>> master
