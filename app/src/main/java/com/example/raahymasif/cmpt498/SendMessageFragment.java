package com.example.raahymasif.cmpt498;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.example.raahymasif.cmpt498.adapters.ChatAdapter;
import com.example.raahymasif.cmpt498.Model.ChatData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * Class responsible to be the chat screen of the app
 */
public class SendMessageFragment extends Fragment {


    /** Database instance **/
    private DatabaseReference mReference;

    /** UI Components **/
    private EditText mChatInput;
    private ChatAdapter mAdapter;

    /** Class variables **/
    private String mUsername;
    private String mUserId;

    /**
     * Create a instance of this fragment
     *
     * @return fragment instance
     */
    public static SendMessageFragment newInstance() {
        return new SendMessageFragment();
    }

    /// Lifecycle methods

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        //getting the username
        //Bundle extras = getIntent().getExtras();
        //postedBy = extras.getString("username");


        Bundle arguments = getArguments();
        String user = arguments.getString("user_name");
        String email = arguments.getString("email");

        //testing
        System.out.println("---------------------------------" + user);

        mUsername = user;
        mUserId = "0000";

        //testing
        System.out.println(mUsername +  "-----------------------------------------");
        setupConnection();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sendmsg, container, false);

        mChatInput = (EditText) root.findViewById(R.id.chat_input);
        mChatInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                ChatData data = new ChatData();
                data.setMessage(mChatInput.getText().toString());
                data.setId(mUserId);
                data.setName(mUsername);

                mReference.child(String.valueOf(new Date().getTime())).setValue(data);

                closeAndClean();
                return true;
            }
        });

        RecyclerView chat = (RecyclerView) root.findViewById(R.id.chat_message);
        chat.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new ChatAdapter();
        chat.setAdapter(mAdapter);

        return root;
    }


    /// Private methods

    private void closeAndClean() {
        mChatInput.setText("");
    }

    private void setupConnection() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mReference = database.getReference("chat");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                handleReturn(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void handleReturn(DataSnapshot dataSnapshot) {
        mAdapter.clearData();

        for(DataSnapshot item : dataSnapshot.getChildren()) {
            ChatData data = item.getValue(ChatData.class);
            mAdapter.addData(data);
        }

        mAdapter.notifyDataSetChanged();
    }
}
