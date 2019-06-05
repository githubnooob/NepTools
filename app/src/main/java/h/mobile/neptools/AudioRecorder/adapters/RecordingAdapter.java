package com.utilnepal.AudioRecorder.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.utilnepal.AudioRecorder.Files.FileNames;
import com.utilnepal.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecordingAdapter extends RecyclerView.Adapter<RecordingAdapter.RecordingViewHolder> {

    private ArrayList<FileNames> fileNames ;
    private Context context;
    private MediaPlayer mPlayer;


    public RecordingAdapter(ArrayList<FileNames> fileNames, Context c, MediaPlayer mplayer)
    {
        this.fileNames = fileNames;
        context= c;
        this.mPlayer = mplayer;
    }

    @NonNull
    @Override
    public RecordingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.each_recording_view,viewGroup,false);

        RecordingViewHolder recordingViewHolder = new RecordingViewHolder(v);
        return recordingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecordingAdapter.RecordingViewHolder recordingViewHolder, final int position) {
                final String source = fileNames.get(position).getFilename();
                recordingViewHolder.recordingName.setText( fileNames.get(position).getFilename());
                recordingViewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String main_path = context.getFilesDir().getPath();
                        String appended_path = main_path+"/recordings";
                        String newSource =  appended_path+"/"+source;
                        playFile(newSource,recordingViewHolder);
                    }
                });

                recordingViewHolder.pauseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseFile(recordingViewHolder);
                    }
                });

                recordingViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String main_path = context.getFilesDir().getPath();
                        String appended_path = main_path+"/recordings";
                        String newSource =  appended_path+"/"+source;
                        deleteFile(newSource, position);
                    }
                });

                recordingViewHolder.recordingAvailableID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(recordingViewHolder.deleteButton.getVisibility()==View.GONE)
                        {
                            recordingViewHolder.deleteButton.setVisibility(View.VISIBLE);
                        }
                        else if(recordingViewHolder.deleteButton.getVisibility()==View.VISIBLE)
                        {
                            recordingViewHolder.deleteButton.setVisibility(View.GONE);
                        }
                    }
                });

    }

    private void deleteFile(String source, int position) {
        File f = new File(source);
        Log.e("FILE PATH", f.getAbsolutePath() + " haha");
        if(f.exists())
        {
            Log.e("File exists", "file exists");
            if(f.delete())
            {
                fileNames.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, fileNames.size());
            }
            else
            {
                Toast.makeText(context, "Sorry, File coulnd't be deleted",Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void togglePlay(RecordingViewHolder recordingViewHolder) {
        if (recordingViewHolder.playButton.getVisibility()== View.VISIBLE)
        {
            recordingViewHolder.playButton.setVisibility(View.GONE);
            recordingViewHolder.pauseButton.setVisibility(View.VISIBLE);
        }
    }

    private void togglePause(RecordingViewHolder recordingViewHolder) {
        if (recordingViewHolder.pauseButton.getVisibility()== View.VISIBLE)
        {
            recordingViewHolder.playButton.setVisibility(View.VISIBLE);
            recordingViewHolder.pauseButton.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        if(fileNames==null)
        {
            Log.e(" Filesname", "FILENAMES ARE NULL");
            return 0;
        }
        return fileNames.size();
    }

    public class RecordingViewHolder extends RecyclerView.ViewHolder {

        private TextView recordingName;
        private ImageView playButton;
        private ImageView pauseButton;
        private Button deleteButton;
        private LinearLayout recordingAvailableID;



        public RecordingViewHolder(@NonNull View itemView) {
            super(itemView);
            recordingName = itemView.findViewById(R.id.eachFileName);
            playButton = itemView.findViewById(R.id.playButton);
            pauseButton = itemView.findViewById(R.id.pauseButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            recordingAvailableID = itemView.findViewById(R.id.recordingAvailableID);
        }
    }

        private void playFile(String source, final RecordingViewHolder recordingViewHolder) {
//            getPermission();
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(source);
                mPlayer.prepare();
                mPlayer.start();
                if (mPlayer.isPlaying())
                {
                    togglePlay(recordingViewHolder);
                }

                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        togglePause(recordingViewHolder);
                    }
                });

            } catch (IOException e) {
                Log.e("Error Occured", "prepare() failed " +e.getMessage());
                Toast.makeText(context,"Cannot Play File",Toast.LENGTH_LONG).show();
                togglePause(recordingViewHolder);
            }
        }


    public void removeItem(int position) {
        fileNames.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, fileNames.size());
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1000);

        }
    }


    private void pauseFile(RecordingViewHolder recordingViewHolder) {
            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
                togglePause(recordingViewHolder);
            }
        }

//    private void togglePause()
//    {
//        if (playButton_dup.getVisibility()==View.VISIBLE)
//        {
//            playButton_dup.setVisibility(View.GONE);
//            pauseButton_dup.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void togglePlay()
//    {
//        if (pauseButton_dup.getVisibility()==View.VISIBLE)
//        {
//            playButton_dup.setVisibility(View.VISIBLE);
//            pauseButton_dup.setVisibility(View.GONE);
//        }
//    }


}
