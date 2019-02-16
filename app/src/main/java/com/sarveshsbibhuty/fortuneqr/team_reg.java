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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class team_reg extends AppCompatActivity {

    EditText re1,re2,re3;
    Button btn;
    Boolean teamIdEntrd=true;
   // int latestQrId=0;
   // String latestQrQ="";
   // String duration="00:00";
   // String timestamp="00:00";



    @Override
    public void onBackPressed() {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_team_reg);
        re1= (EditText)findViewById(R.id.reg1);
        re2= (EditText) findViewById(R.id.reg2);
        re3= (EditText) findViewById(R.id.reg3);
        btn= (Button)findViewById(R.id.btnreg);
        Intent intent = getIntent();
        final String result = intent.getStringExtra("code");






        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String registration1 = re1.getText().toString();
                final String registration2 = re2.getText().toString();
                final String registration3 = re3.getText().toString();

                if(TextUtils.isEmpty(registration1))
                {
                    Toast.makeText(getApplicationContext(),"Enter Registration Number",Toast.LENGTH_SHORT).show();
                    return;
                }
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                String currentTime = mdformat.format(cal.getTime());


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().child("teamId");
                DatabaseReference ref1 =ref.child(result);
                ref1.child("members").child("0").setValue(registration1);
                ref1.child("members").child("1").setValue(registration2);
                ref1.child("members").child("2").setValue(registration3);
                ref1.child("teamIdEntrd").setValue(teamIdEntrd);
                ref1.child("timestamp").setValue(currentTime);

                Intent intent = new Intent(team_reg.this,qrscanner.class);


                startActivity(intent);






            }
        });


    }
}
