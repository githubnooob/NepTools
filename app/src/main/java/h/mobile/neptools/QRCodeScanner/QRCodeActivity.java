package com.utilnepal.QRCodeScanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.utilnepal.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class QRCodeActivity extends AppCompatActivity {

    private TextView QrCodeText;
    private Button QrCodeScannerButton;

    //For QrScanner
    private IntentIntegrator qrScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        QrCodeText = findViewById(R.id.QrCodeText);
        QrCodeScannerButton = findViewById(R.id.QrCodeScannerButton);

        QrCodeText.setVisibility(View.GONE);
        qrScan = new IntentIntegrator(this);


        QrCodeScannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data

                    QrCodeText.setText(result.getContents());
                    QrCodeText.setVisibility(View.VISIBLE);

                    ;

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
