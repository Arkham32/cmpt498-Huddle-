package com.example.raahymasif.cmpt498;

//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.example.raahymasif.cmpt498.Model.CreatePosts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FindMatchFragment extends Fragment {
    //private TextView textView;
    //private FirebaseDatabase mDatabase;
   // private DatabaseReference mDatabaseRef;
    //private TextView hello;



    //---------------
    private RecyclerView mPostList;
    private DatabaseReference mDatabase;
    Button eventsnearme;
    //----------------

    public FindMatchFragment() {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       // View rootview = inflater.inflate(R.layout.fragment_findmatch, container, false);

        
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

        //delete old posts
        deleteOldPosts();

        FirebaseRecyclerAdapter<CreatePosts,FindMatchActivity.PostViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CreatePosts,FindMatchActivity.PostViewHolder>
                (CreatePosts.class,R.layout.post_row,FindMatchActivity.PostViewHolder.class,mDatabase){
            @Override
            protected void populateViewHolder(FindMatchActivity.PostViewHolder viewHolder, CreatePosts model, int position) {

                //this is to get the key(UID) of the post
                final String post_key = getRef(position).getKey();

                //this gets the username
                Bundle arguments = getArguments();
                final String user = arguments.getString("user_name");
                final String email = arguments.getString("email");

                viewHolder.setInfo(model.getInfo());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setPlayers(model.getPlayers());
                viewHolder.setSport(model.getSport());

                eventsnearme = (Button) getView().findViewById(R.id.eventsbutton);

                eventsnearme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent maps = new Intent(getActivity(), MapsActivity.class);
                        String string = "yes";
                        maps.putExtra("buttonclicked", string);
                        startActivity(maps);
                    }
                });

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(FindMatchFragment.this,"post clicked", Toast.LENGTH_LONG).show();
                        //System.out.println("--------------------post clicked-----------------"+ post_key);

                        /*Intent createGame = new Intent(getActivity(), CreateGameActivity.class);
                        createGame.putExtra("username", user);
                        startActivity(createGame);
                        */

                        Intent displayPost = new Intent(getActivity(),DisplaySpecificPostActivity.class);
                        displayPost.putExtra("post_key", post_key);
                        displayPost.putExtra("user_name",user);
                        displayPost.putExtra("email",email);
                        startActivity(displayPost);




                    }
                });



            }

        };

        mPostList.setAdapter(firebaseRecyclerAdapter);

    }




    public void deleteOldPosts(){


        Date date = new Date();

        String strTimeFormat = "HH:mm";
        String strDateFormat = "dd/MM/yyyy";

        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        DateFormat timeFormat = new SimpleDateFormat(strTimeFormat);

        String formattedDate= dateFormat.format(date);
        String formattedTime = timeFormat.format(date);

        String currentDate[] = formattedDate.split("/"); //[dd],[MM],[yyyy]
        String currentTime[] = formattedTime.split(":"); //[HH],[mm]

        final int curDate = Integer.parseInt(currentDate[0]);
        final int curMonth = Integer.parseInt(currentDate[1]);
        final int curYear = Integer.parseInt(currentDate[2]);

        final int curHour = Integer.parseInt(currentTime[0]);
        final int curMin = Integer.parseInt(currentTime[1]);


        final DatabaseReference dbPost = FirebaseDatabase.getInstance().getReference("Posts");
        //final DatabaseReference table_user = database.getReference("Posts");

        dbPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){

                    CreatePosts post = data.getValue(CreatePosts.class);

                    String id = post.getId().toString();

                    String postDateString = post.getDate().toString();
                    String postTimeString = post.getTime().toString();
                    //String postDateString = data.child("date").toString(); // dd/mm/yyyy
                    //String postTimeString = data.child("time").toString();// HH:mm

                    String postDateArray[] = postDateString.split("-");
                    String postTimeArray[] = postTimeString.split(":");

                    int postDate = Integer.parseInt(postDateArray[0]);
                    int postMonth = Integer.parseInt(postDateArray[1]);
                    int postYear = Integer.parseInt(postDateArray[2]);

                    int postHour = Integer.parseInt(postTimeArray[0]);
                    int postMin = Integer.parseInt(postTimeArray[1]);

                    if(curYear >= postYear){
                        if(curMonth >= postMonth ){
                            if(curDate > postDate){
                                dbPost.child(id).removeValue();
                                //dbPost.removeValue();
                                System.out.println("deleting==================================");
                            }

                        }

                    }
                    else{
                        System.out.println("nothing needs deleting =======================");
                        break;
                    }

                    //check if current time and date surpasses the post time and date and delete if true


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //nothing
            }
        });
    }


}





