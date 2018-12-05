package com.example.raahymasif.cmpt498;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.example.raahymasif.cmpt498.Model.CreatePosts;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CreateGameFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //return inflater.inflate(R.layout.fragment_creategame, container, false);
        View view = inflater.inflate(R.layout.fragment_creategame, container, false);

        //getting the username
        Bundle arguments = getArguments();
        String user = arguments.getString("user_name");
        String email = arguments.getString("email");

        //calling createGameActivity and passing the username to it
        Intent createGame = new Intent(getActivity(), CreateGameActivity.class);
        createGame.putExtra("user_name", user);
        createGame.putExtra("email",email);
        startActivity(createGame);

        return view;
    }


}
