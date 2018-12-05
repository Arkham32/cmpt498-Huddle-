package com.example.raahymasif.cmpt498;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;
import java.util.Calendar;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.example.raahymasif.cmpt498.Model.CreatePosts;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.example.raahymasif.cmpt498.Model.CreatePosts;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.location.places.Place;

import java.util.Random;




public class CreateGameActivity extends Activity implements View.OnClickListener {
    MaterialEditText LocationText, NumberOfPlayersText, InfoText;
    Button PostButton, CancelButton, btnDatePicker, btnTimePicker;;
    String uniqueId = new String();
    Place eventAddress;
    String usersJoined = " ";
    AutoCompleteTextView SportText;


    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    //get the username;
    //Bundle extras = getIntent().getExtras();
    String postedBy;// = extras.getString("username");
    String email;// = extras.getString("email");

    //autocomplete text for the sports that can be chosen
    private static final String[] sportCategory = new String[]{
            "Baseball", "Basketball", "Bowling","Cricket","Curling","ESports","Football","Hockey","Lacrosse","Rugby","Soccer"
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_creategame);

        //LocationText = (MaterialEditText)findViewById(R.id.LocationText);
        final PlaceAutocompleteFragment LocationText = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.LocationText);
        LocationText.getView().findViewById(R.id.place_autocomplete_search_button).setVisibility(View.GONE);
        ((EditText) LocationText.getView().findViewById(R.id.place_autocomplete_search_input))
                .setHint(" Click To Select A Location ");
        ((EditText) LocationText.getView().findViewById(R.id.place_autocomplete_search_input))
                .setTextSize(20);

        LocationText.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                eventAddress = place;
            }

            @Override
            public void onError(Status status) {
                Log.e("ERROR", status.getStatusMessage());
            }
        });


        SportText = (AutoCompleteTextView) findViewById(R.id.SportText);
        //SportText = (MaterialEditText)findViewById(R.id.SportText);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sportCategory);
        SportText.setAdapter(adapter);

        // data for date and time picker
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener((View.OnClickListener) this);
        btnTimePicker.setOnClickListener((View.OnClickListener) this);





        NumberOfPlayersText = (MaterialEditText)findViewById(R.id.NumberOfPlayerText);
        InfoText  = (MaterialEditText)findViewById(R.id.InfoText);

        //buttons for posting and cancelling
        PostButton = (Button)findViewById(R.id.PostButton);
        CancelButton = (Button)findViewById(R.id.CancelButton);

        // Initialize the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_post = database.getReference("Posts");

        //get the username;
        Bundle extras = getIntent().getExtras();
        postedBy = extras.getString("user_name");
        email = extras.getString("email");

        //when cancel is clicked
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePage = new Intent(CreateGameActivity.this,HomePageActivity.class);
                homePage.putExtra("user_name", postedBy);
                homePage.putExtra("email",email);
                startActivity(homePage);
            }
        });
        //when post is clicked
        PostButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int j = 0;
                for (int i = 0; i < sportCategory.length; i++)
                {
                    if (SportText.getText().toString().equals(sportCategory[i])){ // (you use the word "contains". either equals or indexof might be appropriate)

                        j = 1;
                    }
                }
                if(j == 0){
                    Toast.makeText(CreateGameActivity.this, "Invalid Sport, please select from list", Toast.LENGTH_SHORT).show();

                }
                if(j == 1){

                final ProgressDialog mDialog = new ProgressDialog(CreateGameActivity.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                //generates the ID
                uniqueId = generateUniqueId();

                //get the username;
                /*Bundle extras = getIntent().getExtras();
                postedBy = extras.getString("username");
                email = extras.getString("email");*/

                table_post.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Check if uniqueID already exists
                        if(dataSnapshot.child(uniqueId.toString()).exists()) {
                            mDialog.dismiss();
                            //if unique id exists it will redo the unique id to find the one that doesn't exist
                            while(dataSnapshot.child(uniqueId.toString()).exists()){
                                uniqueId = generateUniqueId();
                            }
                        }

                        else
                        {
                            mDialog.dismiss();
                            // add to the database
                            //(info, address, num of players, sport, user, joined users)
                            CreatePosts createPosts = new CreatePosts(InfoText.getText().toString(),eventAddress.getAddress().toString(),NumberOfPlayersText.getText().toString(),
                                    SportText.getText().toString(), postedBy.toString(), usersJoined.toString(), uniqueId.toString(), txtDate.getText().toString(),txtTime.getText().toString());

                            
                            table_post.child(uniqueId.toString()).setValue(createPosts);
                            //Toast.makeText(CreateGameActivity.this, "Post Submitted!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(CreateGameActivity.this, "Post Submitted!", Toast.LENGTH_SHORT).show();
                            finish();

                            Intent homePage = new Intent(CreateGameActivity.this,HomePageActivity.class);
                            homePage.putExtra("user_name", postedBy);
                            homePage.putExtra("email", email);
                            startActivity(homePage);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //no code
                    }
                });

            }
        }

        });
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            //System.out.println(txtDate.getText().toString()+"==============================================");

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                            //System.out.println(txtTime.getText().toString()+"-----------------------------------");
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    //generates unique Id for the posts to distinguish between them
    //later have to implement characters to make it more unique
    public String generateUniqueId(){
        String string = new String();
        Random rand = new Random();
        int num = rand.nextInt(1000);
        string = Integer.toString(num);


        return string;
    }


}


