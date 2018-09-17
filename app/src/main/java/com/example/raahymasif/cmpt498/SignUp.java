package com.example.raahymasif.cmpt498;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.raahymasif.cmpt498.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {


    MaterialEditText edtFirstName,edtLastName,edtPassword,edtEmail,edtUsername;
    Button btnSignUp;
    String encodeEmail;

    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtFirstName = (MaterialEditText)findViewById(R.id.edtFirstName);
        edtLastName = (MaterialEditText)findViewById(R.id.edtLastName);
        edtEmail = (MaterialEditText)findViewById(R.id.edtEmail);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtUsername = (MaterialEditText)findViewById(R.id.edtUsername);


        btnSignUp = (Button)findViewById(R.id.btnSignUp);


        // Initialize the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                //need to replace any "." due to firebase not being able to handle them
                encodeEmail = EncodeString(edtEmail.getText().toString());


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // Check if username already exists
                        if(dataSnapshot.child(edtUsername.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Username already exists!", Toast.LENGTH_SHORT).show();

                        }
                        else
                            {
                                // check if email already exists
                            if(dataSnapshot.child(encodeEmail.toString()).exists()) {
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this, "Account already exists under this email!", Toast.LENGTH_SHORT).show();
                            }
                            else
                                {
                                mDialog.dismiss();
                                //User user = new User(edtFirstName.getText().toString(),edtLastName.getText().toString(),edtPassword.getText().toString(),edtEmail.getText().toString(),edtUsername.getText().toString());
                                User user = new User(edtFirstName.getText().toString(),edtLastName.getText().toString(),edtPassword.getText().toString(),encodeEmail.toString());
                                table_user.child(edtUsername.getText().toString()).setValue(user);
                                Toast.makeText(SignUp.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                finish();

                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    //since fire base can not handle "." we need to replace it with ","
    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }
}
