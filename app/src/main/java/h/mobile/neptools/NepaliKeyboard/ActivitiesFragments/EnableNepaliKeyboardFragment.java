package h.mobile.neptools.NepaliKeyboard.ActivitiesFragments;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import h.mobile.neptools.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class EnableNepaliKeyboardFragment extends Fragment {

    public static EditText editTextForTyping;
    public static TextView enableKeyboardText;
    public static Button clickButton;
    public static TextView successInfo;
    public static TextView successKeyboardText2;

    private static ImageView circle_two;

    private String keyboard;
    private static final String MY_KEYBOARD_ID = "h.mobile.neptools/.NepaliKeyboard.NepaliKeyboardService";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.from(getContext()).inflate(R.layout.enable_nepali_keyboard_fragment,container,false);
        clickButton = view.findViewById(R.id.enableKeyboardButton);
        editTextForTyping = view.findViewById(R.id.editTextForTyping);
        enableKeyboardText = view.findViewById(R.id.enableKeyboardText);
        successInfo = view.findViewById(R.id.successKeyboardText);
        successKeyboardText2 = view.findViewById(R.id.successKeyboardText2);

        circle_two = view.findViewById(R.id.circle_two);

        keyboard = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);

        Log.e("CURRENT KEYBOARD",keyboard);
        checkIfKeyboardIsSelected(keyboard);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imeManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                imeManager.showInputMethodPicker();
            }
        });

        return view;
    }

    public static void checkIfKeyboardIsSelected(String keyboard) {


        if(keyboard.equals(MY_KEYBOARD_ID))
        {
            Log.e("IN SECTIOn", keyboard);
            enableKeyboardText.setVisibility(View.GONE);
            editTextForTyping.setVisibility(View.VISIBLE);
            clickButton.setVisibility(View.GONE);
            successInfo.setVisibility(View.VISIBLE);
            successKeyboardText2.setVisibility(View.VISIBLE);
            circle_two.setImageResource(R.drawable.done);
        }

        else
        {
            Log.e("In Wrong Section", keyboard);
            enableKeyboardText.setVisibility(View.VISIBLE);
            editTextForTyping.setVisibility(View.GONE);
            clickButton.setVisibility(View.VISIBLE);
            successInfo.setVisibility(View.GONE);
            successKeyboardText2.setVisibility(View.GONE);
            circle_two.setImageResource(R.drawable.not_done);

        }

    }
}
