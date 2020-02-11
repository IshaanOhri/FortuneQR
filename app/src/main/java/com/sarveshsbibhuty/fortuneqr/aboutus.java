package com.sarveshsbibhuty.fortuneqr;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class aboutus extends AppCompatActivity {

    private TabHost tabHost;
    private TextView nameTextView, regNoTextView, emailTextView;
    private Button logoutButton;
    private ImageView imtap, impap, imtp;
    private CardView privacyPolicy, contactUs;
    private final int CALL_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_aboutus);

        final String papvit = "https://play.google.com/store/apps/details?id=com.namankhurpia.paper";
        final String tapvit = "https://play.google.com/store/apps/details?id=patel.abhay.adg_tap_app";
        final String privacy = "https://5e425749b1ded.htmlsave.com/";
        final String taskPicker = "https://taskpicker.adgvit.com/";

        tabHost =  findViewById(R.id.tabHost);
        tabHost.setup();

        privacyPolicy =  findViewById(R.id.privacyPolicy);
        contactUs =  findViewById(R.id.contactUs);

        imtap = findViewById(R.id.imtap);
        impap =  findViewById(R.id.impap);
        imtp =  findViewById(R.id.imtp);


        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog1 = new Dialog(aboutus.this);
                dialog1.setCancelable(false);
                dialog1.setContentView(R.layout.contact_card);
                dialog1.show();

                final Button doneButton = dialog1.findViewById(R.id.doneButton);
                Button callButton = dialog1.findViewById(R.id.callButton);

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });

                callButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checkSelfPermission(Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                        {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},CALL_CODE);
                        }
                        else
                        {
                            callPhone();
                        }
                    }
                });
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse(privacy)));
                startActivity(intent);
            }
        });

        TabHost.TabSpec spec = tabHost.newTabSpec("TAB ONE");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Profile");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("TAB TWO");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Team");
        tabHost.addTab(spec);

        int tab = tabHost.getCurrentTab();
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            // When tab is not selected
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#EEEEF0"));
            TextView tv = tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(14);
        }
        // When tab is selected
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#FFFFFF"));
        TextView tv = tabHost.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(14);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int tab = tabHost.getCurrentTab();
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    // When tab is not selected
                    tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#EEEEF0"));
                    TextView tv = tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(14);
                }
                // When tab is selected
                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#FFFFFF"));
                TextView tv = tabHost.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(14);
            }
        });

        imtap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse(tapvit)));
                startActivity(intent);
            }
        });
        impap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse(papvit)));
                startActivity(intent);
            }
        });
        imtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse(taskPicker)));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED && requestCode == CALL_CODE) {
                callPhone();
            }
        }
    }

    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + "+919099946404"));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

}
