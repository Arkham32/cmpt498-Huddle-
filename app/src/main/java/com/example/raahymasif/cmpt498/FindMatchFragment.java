package com.example.raahymasif.cmpt498;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

