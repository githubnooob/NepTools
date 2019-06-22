package h.mobile.neptools.HelperMobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


import java.util.ArrayList;

import h.mobile.neptools.HelperMobile.adapters.EmergencyNumberAdapter;
import h.mobile.neptools.HelperMobile.data.EmergencyNumberFeatures;
import h.mobile.neptools.HelperMobile.data.EmergencyNumbersGenerator;
import h.mobile.neptools.HelperMobile.data.SimNumbers;
import h.mobile.neptools.R;

import static h.mobile.neptools.HelperMobile.data.SimNumbers.CallForwardCheck;

public class MiscellaneousActivity extends AppCompatActivity {

    private ArrayList<EmergencyNumberFeatures> features;
    private RecyclerView emergencyNumberRecyclerView;
    private EmergencyNumberAdapter emergencyNumberAdapter;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscellaneous);

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emergencyNumberRecyclerView = findViewById(R.id.emergencyNumberRecyclerView);
        emergencyNumberAdapter = new EmergencyNumberAdapter(new EmergencyNumbersGenerator(this).generateEmergencyNumber(),this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        emergencyNumberRecyclerView.setLayoutManager(mLayoutManager);
        emergencyNumberRecyclerView.setItemAnimator(new DefaultItemAnimator());
        emergencyNumberRecyclerView.setAdapter(emergencyNumberAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mobile_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.source:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setMessage("This phone numbers are derived from https://www.nepalpolice.gov.np/.Any numbers that were present during the extraction  are kept here.");
                alertDialog.setCancelable(true);
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();
                break;
        }

        return false;
    }

    public static class MobileHelperActivity extends AppCompatActivity {

        private CardView balanceCheck;
        private CardView rechargeCardScanner;
        private CardView mobileNumberCheck;
        private CardView dataPackCheck;
        private CardView callForwardCheck;
        private CardView miscellaneousNumber;
        private AlertDialog alertDialog;

        private Button NcellCallingButton;
        private Button NTCCallingButton;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mobile_helper);

            initialize();
        }

        private void initialize() {
            balanceCheck = findViewById(R.id.balanceCheck);
            rechargeCardScanner = findViewById(R.id.rechargeScanner);
            mobileNumberCheck = findViewById(R.id.mobileNumberCheck);
            dataPackCheck = findViewById(R.id.dataPackCheck);
            callForwardCheck = findViewById(R.id.callForwardCheck);
            miscellaneousNumber = findViewById(R.id.miscelleanousNumber);



            balanceCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSimChooserDialog(v.getId());
                }
            });

            rechargeCardScanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSimChooserDialog(v.getId());
                }
            });

            mobileNumberCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSimChooserDialog(v.getId());
                }
            });

            dataPackCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSimChooserDialog(v.getId());

                }
            });

            callForwardCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSimChooserDialog(v.getId());
                }
            });

            miscellaneousNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //miscelleanous Number Showing
                    Intent intent = new Intent(MobileHelperActivity.this, h.mobile.neptools.HelperMobile.MiscellaneousActivity.class);
                    startActivity(intent);
                }
            });


        }

        private void showSimChooserDialog(final int id) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View simChooserDialog = inflater.inflate(R.layout.sim_chooser_dialog, null);
            dialogBuilder.setView(simChooserDialog);

            final AlertDialog alertDialog = dialogBuilder.create();
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(alertDialog.getWindow().getAttributes());
            lp.windowAnimations = R.style.DialogAnimation;
            alertDialog.getWindow().setAttributes(lp);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            NcellCallingButton = simChooserDialog.findViewById(R.id.ncellButton);
            NTCCallingButton = simChooserDialog.findViewById(R.id.ntcButton);

            //2131230882

            NcellCallingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (id)
                    {
                        case R.id.balanceCheck:
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(SimNumbers.NcellBalanceCheck)));
                            startActivity(intent);

                            break;
                        case R.id.dataPackCheck:
                            Intent dataIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(SimNumbers.NcellDataPack)));
                            startActivity(dataIntent);
                            break;
                        case R.id.mobileNumberCheck:
                            Intent numberCheckIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(SimNumbers.NcellNumberCheck)));
                            startActivity(numberCheckIntent);
                            break;
                        case R.id.callForwardCheck:
                            Intent callForward = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(CallForwardCheck)));
                            startActivity(callForward);

                            break;
                        case R.id.rechargeScanner:
                            Intent recharge = new Intent(getApplicationContext(),h.mobile.neptools.HelperMobile.RechargeUtils.RechargeActivity.class);
                            recharge.putExtra("type", "NCELL");
                            alertDialog.dismiss();
                            startActivity(recharge);
                            break;


                    }
                }
            });

            NTCCallingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (id)
                    {
                        case R.id.balanceCheck:
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(SimNumbers.NtcBalanceCheck)));
                            startActivity(intent);

                            break;
                        case R.id.dataPackCheck:
                            Intent dataIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(SimNumbers.NtcDataPack)));
                            startActivity(dataIntent);
                            break;

                        case R.id.mobileNumberCheck:
                            Intent numberCheckIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(SimNumbers.NtcNumberCheck)));
                            startActivity(numberCheckIntent);
                            break;
                        case R.id.callForwardCheck:
                            Intent callForward = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(CallForwardCheck)));
                            startActivity(callForward);

                            break;
                        case R.id.rechargeScanner:
                            Intent recharge = new Intent(getApplicationContext(),h.mobile.neptools.HelperMobile.RechargeUtils.RechargeActivity.class);
                            recharge.putExtra("type", "NTC");
                            alertDialog.dismiss();
                            startActivity(recharge);
                            break;
                    }

                }
            });
            alertDialog.show();
        }
    }
}
