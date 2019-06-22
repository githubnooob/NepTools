package com.utilnepal.AudioRecorder.ActivitiesFragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.utilnepal.AudioRecorder.Files.FileNames;
import com.utilnepal.AudioRecorder.adapters.RecordingAdapter;
import com.utilnepal.R;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecorderFragment extends Fragment {

    // for imageView
    private ImageView playButton;
    private ImageView stopButton;
    private TextView timeTextView;

    // constants
    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;

    //media player
    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;

    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    private long startHTime ;
    private Handler customHandler ;
    private long timeInMilliseconds ;
    private long timeSwapBuff ;
    private long updatedTime;


    public RecorderFragment() {
        customHandler = new Handler();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recorder, container, false);

        playButton = v.findViewById(R.id.playButton);
        stopButton = v.findViewById(R.id.stopButton);
        timeTextView = v.findViewById(R.id.timeForRecord);
        mFileName = getContext().getFilesDir().getPath();
        mFileName += "/recordings";







        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();


                playButton.setEnabled(false);
                stopButton.setEnabled(true);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
                playButton.setEnabled(true);
                stopButton.setEnabled(false);

            }
        });



        ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        return v;
    }




    //permission for audio record
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) {
            getActivity().finish();
        }
    }


    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    private void startRecording() {
        startHTime = 0L;
        updatedTime =0L;
        timeSwapBuff =0L;
        timeInMilliseconds = 0L;

        mFileName = getContext().getFilesDir().getPath();
        mFileName += "/recordings";

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        File f = new File(mFileName);

        String current_time = getDateTime();

        if(!f.exists())
        {
            f.mkdir();
        }

        mFileName=f+"/"+current_time+".3gp";
        Log.e("recordedFilename",mFileName);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
            playButton.setVisibility(View.INVISIBLE);
            stopButton.setVisibility(View.VISIBLE);
            mRecorder.start();
            startTime();
        } catch (IOException e) {
            Log.e("Error while recording", "prepare() failed "+e.getMessage());
        }
    }


    private Runnable updateTimerThread = new Runnable() {

        public void run() {


            timeInMilliseconds = SystemClock.uptimeMillis() - startHTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            if (timeTextView != null)
                timeTextView.setText("" + String.format("%02d", mins) + ":"
                        + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }

    };

    private void startTime() {
        startHTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }

    private void stopTime() {
        timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
        timeTextView.setText("00:00");
    }

    private void stopRecording() {

        if (mRecorder != null) {
            playButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.INVISIBLE);
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            stopTime();
            Toast.makeText(getContext(), "File Saved", Toast.LENGTH_SHORT).show();
            updateRecyclerView();
        }
    }

    private void updateRecyclerView() {
        RecordingFragment.fileNames.clear();

        String main_path = getContext().getFilesDir().getPath();
        String appended_path = main_path+"/recordings";

        File directory = new File(appended_path);
        File [] files = directory.listFiles();

        for (int i=0; i<files.length; i++)
        {
            RecordingFragment.fileNames.add(new FileNames(files[i].getName()));
        }

        if( RecordingFragment.fileNames.size()>0)
        {
            RecordingFragment.audioRecordingsRecylerView.getAdapter().notifyDataSetChanged();
        }
    }


    @Override
    public void onStop() {
        super.onStop();

        stopRecording();

        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

}
