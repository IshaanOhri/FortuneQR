package com.sarveshsbibhuty.fortuneqr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class endgame extends AppCompatActivity {

    Button about;
    Boolean endgame=true;



    @Override
    public void onBackPressed() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_endgame);


        about = (Button) findViewById(R.id.endgame);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String acode = pref.getString("code","a");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("teamId");
        DatabaseReference ref1 =ref.child(acode);
        ref1.child("endgame").setValue(endgame);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(endgame.this, aboutus.class);
                startActivity(i);
            }
        });
    }
}
