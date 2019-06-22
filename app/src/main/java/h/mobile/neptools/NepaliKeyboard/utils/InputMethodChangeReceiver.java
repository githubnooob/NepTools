package h.mobile.neptools.NepaliKeyboard.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import h.mobile.neptools.NepaliKeyboard.ActivitiesFragments.EnableNepaliKeyboardFragment;


public class InputMethodChangeReceiver extends BroadcastReceiver {

    private static final String MY_KEYBOARD_ID = "h.mobile.neptools/.NepaliKeyboard.NepaliKeyboardService";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_INPUT_METHOD_CHANGED)) {
            String defaultIME = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
            EnableNepaliKeyboardFragment.checkIfKeyboardIsSelected(defaultIME);
        }


    }
}