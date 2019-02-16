package com.sarveshsbibhuty.fortuneqr;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class qrscanner extends AppCompatActivity {


    Button scan;
    TextView clue;
    String qrid;
    String ques;
    String acode;
    String starttime;
    Date star,current,qrtime,strtime;

   Handler mHandler;
   Runnable timerRunnable;
    TextView timer;
    DatabaseReference ref10;
    Button gameend;
    int qr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_qrscanner);

        scan = (Button) findViewById(R.id.qrscan);
        clue = (TextView) findViewById(R.id.hint);
        timer = (TextView)findViewById(R.id.time);
        gameend = (Button)findViewById(R.id.end);

        final Activity activity = this;

      //  SharedPreferences pref = getSharedPreferences("MyPref", 0);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
         acode = pref.getString("code","a");





     //  Toast.makeText(getApplicationContext(),acode,Toast.LENGTH_SHORT).show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref1 =ref.child("teamId");
        DatabaseReference ref2 =ref1.child(acode);
        DatabaseReference ref3=ref2.child("latestQrId");
        DatabaseReference ref4=ref2.child("latestQrQ");
         ref10 = ref2.child("timestamp");

        ref10.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                starttime= dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        starttime = getIntent().getStringExtra("Start");

      //  SharedPreferences.Editor editor = pref.edit();
     //   editor.putString("start",starttime);
      //  editor.commit();

        ref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ques=dataSnapshot.getValue(String.class);
                clue.setText(ques);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                qrid = dataSnapshot.getValue(String.class);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });



        gameend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Integer.parseInt(qrid)%9==0)
                {
                    Intent i = new Intent(qrscanner.this, endgame.class);
                    startActivity(i);


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You have not completed the game",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);



            if(result != null){
                if(result.getContents()==null){

                    Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
                }
                else {
                   // Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();


                    String clues;
                    clues = result.getContents();
                    int n = clues.length();

                    String id = clues.substring(0,2);
                    String question = clues.substring(3,n);
                    int num = Integer.parseInt(id);

                    int qrcode = Integer.parseInt(qrid);
                    int qrcod;

                    if(qrcode==0)
                    {
                        qrcod= num%9;
                    }
                    else
                    {
                        qrcod=num;
                    }

                    if((qrcod-qrcode)==1) {


                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                        String currentTime = mdformat.format(cal.getTime());

                        NumberFormat format = new DecimalFormat("00");
                        try {
                            qrtime = mdformat.parse(currentTime);
                        } catch (ParseException e){
                            e.printStackTrace();
                        }

                        try {
                            strtime = mdformat.parse(starttime);
                        } catch (ParseException e){
                            e.printStackTrace();
                        }

                        long millse = qrtime.getTime()-strtime.getTime();

                        long mills = Math.abs(millse);

                        int hours = (int) (mills/(1000 * 60 * 60));
                        int min = (int) (mills/(1000*60)) % 60;
                        long sec = (int) (mills / 1000) % 60;

                        String  hour = format.format(hours);
                        String  Min = format.format(min);
                        String Sec = format.format(sec);

                        String duration;
                        duration = hour + " hours " +Min +" Mins "+ Sec + " Seconds ";


                        clue.setText(question);
                        String aa = Integer.toString(num);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref5 = database.getReference().child("teamId");
                        DatabaseReference ref6 = ref5.child(acode);
                        ref6.child("latestQrId").setValue(aa);
                        ref6.child("latestQrQ").setValue(question);
                        ref6.child("duration").setValue(duration);
                        Toast.makeText(getApplicationContext(),"Congratulations !!!",Toast.LENGTH_SHORT).show();


                    }

                    else if(num==qrcode)
                    {
                        Toast.makeText(getApplicationContext(),"Same QR scanned",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Wrong QR scanned",Toast.LENGTH_SHORT).show();
                    }




                  //  Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
                  // Toast.makeText(getApplicationContext(),question,Toast.LENGTH_SHORT).show();


                }
            }
            else {
                super.onActivityResult(requestCode, resultCode, data);
            }




    }

    private void UpdateTimer() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = mdformat.format(cal.getTime());


        NumberFormat format = new DecimalFormat("00");

        String currentTimer;
        int flag = 0;




        try {
            star = mdformat.parse(starttime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            current = mdformat.parse(currentTime);
        } catch (ParseException e){
            e.printStackTrace();
        }

        long millse = current.getTime()-star.getTime();

        long mills = Math.abs(millse);

        int hours = (int) (mills/(1000 * 60 * 60));
        int min = (int) (mills/(1000*60)) % 60;
        long sec = (int) (mills / 1000) % 60;

        String  hour = format.format(hours);
        String  Min = format.format(min);
        String Sec = format.format(sec);




        currentTimer = hour + ":" + Min+ ":"+Sec;



        timer.setText(currentTimer);


    }

    @Override
    public void onBackPressed() {

        mHandler.removeCallbacks(timerRunnable);

        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(timerRunnable);

        super.onDestroy();
    }

    @Override
    protected void onStop() {

        mHandler.removeCallbacks(timerRunnable);

        super.onStop();
    }

    @Override
    protected void onStart(){

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        starttime = pref.getString("Start","00:00:00");

        mHandler = new Handler();

        timerRunnable = new Runnable() {
            @Override
            public void run() {

                UpdateTimer();

                mHandler.postDelayed(this,1000);
            }
        };

        mHandler.post(timerRunnable);
        super.onStart();

    }

    @Override
    protected void onResume(){

        ref10.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                starttime= dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onResume();

    }



}
