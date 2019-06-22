package h.mobile.neptools.NepaliKeyboard.ActivitiesFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

import h.mobile.neptools.R;

public class SelectNepaliKeyboardScreenFragment extends Fragment {

    private static String KEYBOARDID = "com.utilnepal/.NepaliKeyboard.NepaliKeyboardService";
    public static Timer timer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.from(getContext()).inflate(R.layout.select_nepali_keyboard_fragment,container,false);

//        MobileAds.initialize(getContext(),AdUnits.FAKE_APP_ID);
//        adView = v.findViewById(R.id.mainActivityAdView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Button selectNepaliKeyboard;

        selectNepaliKeyboard = view.findViewById(R.id.selectKeyboardButton);
        selectNepaliKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkIfKeyboardIsEnabled())
                {
                    enableKeyboard();
                }

            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void enableKeyboard()
    {
        startActivityForResult(new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS),1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        String list = imm.getEnabledInputMethodList().toString();
        Log.e("THIS ARE AVAILABLE ",list );
        if (requestCode == 1) {
            if (checkIfKeyboardIsEnabled()) {
                    Intent intent = new Intent(getContext(), KeyboardActivity.class);
                    startActivity(intent);
                    getActivity().finish();

            } else {
                Toast.makeText(getContext(), "Keyboard Not Selected", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Boolean checkIfKeyboardIsEnabled() {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        String list = imm.getEnabledInputMethodList().toString();

        Log.e("Listing", list);

        if(list.contains(KEYBOARDID))
        {
            Intent intent = new Intent(getContext(), KeyboardActivity.class);
            startActivity(intent);
            getActivity().finish();
            return true;
        }

        return false;
    }



}
