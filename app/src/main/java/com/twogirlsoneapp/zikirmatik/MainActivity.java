package com.twogirlsoneapp.zikirmatik;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Dimension;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tx, txZikirHint;
    View btnZikir,btnReset;
    Button btnResett;
    ImageButton btnVibration;
    int counter;
    private AdView mAdView;
    boolean isVibrationOn;
    private InterstitialAd mInterstitialAd;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_layout);
        MobileAds.initialize(this, getString(R.string.app_id));
        mAdView = findViewById(R.id.adView);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.full_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(adListener);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
         tx = (TextView)findViewById(R.id.tvZikir);
        txZikirHint= findViewById(R.id.txZikirHint);
        setTypeFace();
        btnZikir= findViewById(R.id.btnZikir);

        btnReset= findViewById(R.id.btnReset);
        btnVibration = findViewById(R.id.btnVibration);
        isVibrationOn=true;
        counter=0;
        tx.setText(Integer.toString(counter));


        btnZikir.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnVibration.setOnClickListener(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


    }


   void setTypeFace(){

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "font/digitalnew.otf");
        tx.setTypeface(custom_font);
       txZikirHint.setTypeface(custom_font);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v==btnZikir){
            counter++;
            tx.setText(Integer.toString(counter));

            if (isVibrationOn){
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(150);
            }
            Bundle bundle = new Bundle();
            mFirebaseAnalytics.logEvent("ZikirCekme",bundle);
        }
        else if (v==btnReset){


            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.aler_dialog_layout,null);
            //dialogView.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            // Set the custom layout as alert dialog view
            builder.setView(dialogView);

            // Get the custom alert dialog view widgets reference
            Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
            Button btn_negative = (Button) dialogView.findViewById(R.id.dialog_negative_btn);


            // Create the alert dialog
            final AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            // Set positive/yes button click listener
            btn_positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Dismiss the alert dialog
                    counter=0;
                    tx.setText(Integer.toString(counter));

                    dialog.cancel();

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                        Bundle bundle = new Bundle();
                        mFirebaseAnalytics.logEvent("ResetPositive",bundle);
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }

                }
            });

            // Set negative/no button click listener
            btn_negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Dismiss the alert dialog
                    dialog.cancel();

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                        Bundle bundle = new Bundle();
                        mFirebaseAnalytics.logEvent("ResetNegative",bundle);
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }

                }
            });


            // Display the custom alert dialog on interface
            dialog.show();



        }
        else if (v==btnVibration){
            if(isVibrationOn){
                isVibrationOn = false;
                btnVibration.setImageResource((R.drawable.vibration_button_off));
            }
            else
            {
                isVibrationOn = true;
                btnVibration.setImageResource((R.drawable.vibration_button_on));
            }
        }
    }

    AdListener adListener = new AdListener() {
        @Override
        public void onAdClosed() {
            super.onAdClosed();
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }

        @Override
        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
        }

        @Override
        public void onAdClicked() {
            super.onAdClicked();
        }

        @Override
        public void onAdImpression() {
            super.onAdImpression();
        }
    };
}
