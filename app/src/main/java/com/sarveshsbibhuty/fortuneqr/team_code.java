package com.sarveshsbibhuty.fortuneqr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class team_code extends AppCompatActivity {

    EditText tcode;
    Button  btn;
    Boolean value,value1;

    @Override
    public void onBackPressed() {


    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_team_code);

        tcode = (EditText) findViewById(R.id.teamc);
        btn = (Button) findViewById(R.id.btncode);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String teamcode = tcode.getText().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref1 =ref.child("teamId");
                DatabaseReference ref2 =ref1.child(teamcode);
                final DatabaseReference ref3=ref2.child("teamIdEntrd");
                final DatabaseReference ref4=ref2.child("endgame");

                if(TextUtils.isEmpty(teamcode))
                {
                    Toast.makeText(getApplicationContext(),"Enter Team Code",Toast.LENGTH_SHORT).show();
                    return;
                }
                ref2.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {

                            ref4.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    value1 = (Boolean)dataSnapshot.getValue();
                                    if (value1.equals(false))
                                    {
                                        ref3.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                value = (Boolean)dataSnapshot.getValue();
                                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                                SharedPreferences.Editor editor = pref.edit();
                                                editor.putString("code",teamcode);
                                                editor.commit();


                                                if(value.equals(false))
                                                {

                                                    Intent i = new Intent(team_code.this,team_reg.class);
                                                    i.putExtra("code",teamcode);
                                                    startActivity(i);

                                                }
                                                else
                                                {


                                                    Intent intent = new Intent(team_code.this,qrscanner.class);
                                                    startActivity(intent);
                                                }



                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    else
                                    {
                                        Intent intent = new Intent(team_code.this,endgame.class);
                                        startActivity(intent);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                        }

                        else {

                            Toast.makeText(getApplicationContext(), "Wrong Code", Toast.LENGTH_SHORT).show();

                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





           }
        });





    }
}
