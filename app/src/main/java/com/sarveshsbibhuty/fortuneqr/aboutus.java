package com.sarveshsbibhuty.fortuneqr;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class aboutus extends AppCompatActivity {

    TextView policy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_aboutus);

    policy = (TextView)findViewById(R.id.privacy);


    policy.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Uri uri  =Uri.parse("https://8mifbx794ez4b25ordzsya-on.drv.tw/FortuneQr/privacy_policy.html");
            Intent i = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(i);

        }
    });

    }
}
