package com.utilnepal.AudioRecorder.ActivitiesFragment;


import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.utilnepal.AudioRecorder.Files.FileNames;
import com.utilnepal.AudioRecorder.adapters.RecordingAdapter;
import com.utilnepal.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordingFragment extends Fragment {

    public static RecyclerView audioRecordingsRecylerView;
    private RecordingAdapter recordingAdapter;
    public static ArrayList<FileNames> fileNames;
    private MediaPlayer mPlayer;

    public RecordingFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_recording, container, false);

        audioRecordingsRecylerView = v.findViewById(R.id.audioRecordRecyclerView);
        recordingAdapter = new RecordingAdapter(addFileNames(),getContext(),mPlayer);
        audioRecordingsRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));

        audioRecordingsRecylerView.setAdapter(recordingAdapter);


        return v;
    }

    private ArrayList<FileNames> addFileNames() {
        fileNames = new ArrayList<>();
        String main_path = getContext().getFilesDir().getPath();
        String appended_path = main_path+"/recordings";

        File directory = new File(appended_path);

        if(directory.length()==0)
        {
            return fileNames;
        }

        File [] files = directory.listFiles();


        for (int i=0; i<files.length; i++)
        {
            fileNames.add(new FileNames(files[i].getName()));
        }

        if(fileNames.size()>0)
        {
            return fileNames;
        }
        return null;
    }



    @Override
    public void onPause() {
        super.onPause();

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
