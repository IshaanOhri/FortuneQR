package com.sarveshsbibhuty.fortuneqr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    DatabaseReference timeRef;
    Boolean value;

    String Start;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final String acode = pref.getString("code","a");

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref1 =ref.child("teamId");
                DatabaseReference ref2 =ref1.child(acode);
                final DatabaseReference ref3=ref2.child("endgame");

                            if(!acode.equals("a"))
                            {
                                timeRef = FirebaseDatabase.getInstance().getReference().child("teamId").child(acode);
                                timeRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Start = dataSnapshot.child("timestamp").getValue(String.class);

                                        ref3.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                value = (Boolean)dataSnapshot.getValue();


                                                if(value.equals(true))
                                                {
                                                    Intent intent = new Intent(splash.this,endgame.class);
                                                    startActivity(intent);
                                                }
                                                else {
                                                    Intent i = new Intent(splash.this, qrscanner.class);
                                                    i.putExtra("Current", acode);
                                                    i.putExtra("Start", Start);
                                                    startActivity(i);

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }
                            else {

                                    Intent mainIntent = new Intent(splash.this, team_code.class);
                                    splash.this.startActivity(mainIntent);
                                    splash.this.finish();


                            }




            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
