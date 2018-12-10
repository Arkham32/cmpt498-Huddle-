package com.example.raahymasif.cmpt498;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.raahymasif.cmpt498.Model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SignUp extends AppCompatActivity {


    EditText edtFirstName, edtLastName, edtPassword, edtEmail, edtUsername;
    CardView btnSignUp;
    String encodeEmail;
    String adminStatus = "admin";
    Context context = this;


    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtFirstName = (EditText) findViewById(R.id.edtFirstName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtUsername = (EditText) findViewById(R.id.edtUsername);


        btnSignUp = (CardView) findViewById(R.id.btnSignUp);


        // Initialize the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encodeEmail = EncodeString(edtEmail.getText().toString());
                final User user = new User(edtFirstName.getText().toString(), edtLastName.getText().toString(), edtPassword.getText().toString(), encodeEmail.toString(), adminStatus.toString());
                //Toast.makeText(SignUp.this, "Account Created!", Toast.LENGTH_SHORT).show();
                //finish();

                //need to replace any "." due to firebase not being able to handle them



                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        ArrayList email = get_email((Map<String, Object>) dataSnapshot.getValue());
                        if (dataSnapshot.child((edtUsername.getText().toString())).exists()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Error")
                                    .setMessage("Username already exists!")
                                    .setNeutralButton("OK", null);

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else if (email.contains(encodeEmail)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Error")
                                    .setMessage("Email already exists!")
                                    .setNeutralButton("OK", null);

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            table_user.child(edtUsername.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Account Created!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    private ArrayList get_email(Map<String, Object> users) {

        ArrayList<String> location = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            //System.out.println(entry.getKey().toString());
            //System.out.println("###########################");
            location.add((String) singleUser.get("email"));

        }
        return location;
    }

    //since fire base can not handle "." we need to replace it with ","
    public String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public String DecodeString(String string) {
        return string.replace(",", ".");
    }
}


