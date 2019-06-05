package com.utilnepal.AudioRecorder.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.utilnepal.AudioRecorder.ActivitiesFragment.RecorderFragment;
import com.utilnepal.AudioRecorder.ActivitiesFragment.RecordingFragment;

import java.util.List;

public class AudioRecorderAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public AudioRecorderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return new RecorderFragment();
            case 1 :
                return new RecordingFragment();

            default:
                return new RecorderFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Recorder";
            case 1 :
                return "Recordings";
            default:
                return "Recorder";
        }
    }
}