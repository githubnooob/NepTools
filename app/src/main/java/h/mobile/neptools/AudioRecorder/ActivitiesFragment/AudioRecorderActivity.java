package com.utilnepal.AudioRecorder.ActivitiesFragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.utilnepal.AdUnits;
import com.utilnepal.AudioRecorder.adapters.AudioRecorderAdapter;
import com.utilnepal.R;

import java.io.File;
import java.io.IOException;

public class AudioRecorderActivity extends AppCompatActivity {

    //views
    private TabLayout tabLayoutHomePage;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private AudioRecorderAdapter mAudioRecorderAdapter;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiorecorder);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewPager = findViewById(R.id.viewPagerHomePage);
        mAudioRecorderAdapter = new AudioRecorderAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAudioRecorderAdapter);
        tabLayoutHomePage = findViewById(R.id.tabLayoutHomePage);
        tabLayoutHomePage.setupWithViewPager(mViewPager);
//
//        MobileAds.initialize(this,
//                AdUnits.REAL_APP_ID);
//
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(AdUnits.AUDIO_RECORDER_AD_UNIT);
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//                mInterstitialAd.show();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when the ad is displayed.
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when when the interstitial ad is closed.
//            }
//        });
    }


    @Override public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
