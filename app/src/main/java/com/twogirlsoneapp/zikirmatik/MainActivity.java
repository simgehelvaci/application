package com.twogirlsoneapp.zikirmatik;

import android.content.Context;
import android.graphics.Typeface;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tx, txZikirHint;
    View btnZikir,btnReset;
    Button btnResett;
    int counter;
    private AdView mAdView;
    boolean isVibrationOn;
    private InterstitialAd mInterstitialAd;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(adListener);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
         tx = (TextView)findViewById(R.id.tvZikir);
        txZikirHint= findViewById(R.id.txZikirHint);
        setTypeFace();
        btnZikir= findViewById(R.id.btnZikir);

        btnReset= findViewById(R.id.btnReset);
        isVibrationOn=true;
        counter=0;
        tx.setText(Integer.toString(counter));


        btnZikir.setOnClickListener(this);
        btnReset.setOnClickListener(this);



    }


   void setTypeFace(){

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "font/digitalnew.otf");
        tx.setTypeface(custom_font);
       txZikirHint.setTypeface(custom_font);
    }

    @Override
    public void onClick(View v) {
        if (v==btnZikir){
            counter++;
            tx.setText(Integer.toString(counter));

            if (isVibrationOn){
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(150);
            }

        }
        else if (v==btnReset){
            counter=0;
            tx.setText(Integer.toString(counter));
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
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
