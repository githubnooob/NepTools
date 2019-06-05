package com.utilnepal.AudioRecorder.Utils;

import android.os.CountDownTimer;

public abstract class ClockUpTImer extends CountDownTimer {

    private static final long INTERVAL_MS = 1000;
    private final long duration;


    public ClockUpTImer(long millisInFuture, long countDownInterval, long duration) {
        super(millisInFuture, countDownInterval);
        this.duration = duration;
    }

    public abstract void onTick(int second);



    @Override
    public void onTick(long msUntilFinished) {
        int second = (int) ((duration - msUntilFinished) / 1000);
        onTick(second);
    }

    @Override
    public void onFinish() {
        onTick(duration / 1000);
    }
}
