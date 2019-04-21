package org.sairaa.atmfinder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.sairaa.atmfinder.Model.AccountDetail;
import org.sairaa.atmfinder.Utils.ApiUtilsData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BankAccountActivity extends AppCompatActivity implements View.OnClickListener {

    SurfaceView mCameraView;
    TextView mTextView;
    CameraSource mCameraSource;
    private Button capture, createAcc;
    private EditText name, panNo, phoneNo;

    private List<String> gDetails = new ArrayList<>();

    private static final String TAG = "MainActivity";
    private static final int requestPermissionID = 101;
    AccountDetail accountDetail = new AccountDetail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account);

        name = findViewById(R.id.name_t);
        panNo = findViewById(R.id.pan_no);
        phoneNo = findViewById(R.id.phone_no_t);
        createAcc = findViewById(R.id.create_acc);
        createAcc.setOnClickListener(this);


        mCameraView = findViewById(R.id.surfaceView);
        mTextView = findViewById(R.id.surface_text);
        capture = findViewById(R.id.capture);
        mCameraView.setVisibility(View.GONE);

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capture.setVisibility(View.GONE);
                mCameraView.setVisibility(View.VISIBLE);
                startCameraSource();
                accountDetail = new AccountDetail();
            }
        });


    }

    private void startCameraSource() {

        //Create the TextRecognizer
        final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Log.w(TAG, "Detector dependencies not loaded yet");
        } else {

            //Initialize camerasource to use high resolution and set Autofocus on.
            mCameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setAutoFocusEnabled(true)
                    .setRequestedFps(2.0f)
                    .build();

            /**
             * Add call back to SurfaceView and check if camera permission is granted.
             * If permission is granted we can start our cameraSource and pass it to surfaceView
             */
            mCameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {

                        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(BankAccountActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    requestPermissionID);
                            return;
                        }
                        mCameraSource.start(mCameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                /**
                 * Release resources for cameraSource
                 */
                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    mCameraSource.stop();
                }
            });

            //Set the TextRecognizer's Processor.
            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {
                }

                /**
                 * Detect all the text from camera using TextBlock and the values into a stringBuilder
                 * which will then be set to the textView.
                 * */
                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0) {

                        mTextView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < items.size(); i++) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    gDetails.add(item.getValue());
                                    stringBuilder.append("\n");
                                }

                                mTextView.setText(stringBuilder.toString());
//                                findDetails(gDetails);
                                findDetailString(stringBuilder.toString());
                            }
                        });
                    }
                }
            });
        }
    }

    private void findDetailString(String readS) {


        String[] separated = readS.split("\n");
        String lastS = "test";
        for (String current : separated) {
            if (lastS.contains("DEPARTMENT")) {
                accountDetail.setName(current);
            }
            if (lastS.contains("Number")) {
                accountDetail.setPan(current);
            }
            lastS = current;
        }
        if (accountDetail.getName() != null && accountDetail.getPan() != null) {
            mCameraSource.stop();
            mCameraView.setVisibility(View.GONE);
            capture.setVisibility(View.VISIBLE);
            capture.setText("Re-Capture");

            accountDetail.setAddress(readS);
            setUpUi(accountDetail);

        }


    }

    private void findDetails(List<String> gDetails) {

//        int startIndex = str.lastIndexOf("\n");
//
//        String result = null;
//
//// see if its valid index then just substring it to the end from that
//
//        if(startIndex!=-1 && startIndex!= str.length()){
//            str.subString(startIndex+1);
//        }

        String prevLine = "test";
        for (String det : gDetails) {
            int startIndex = prevLine.lastIndexOf("DEPARTMENT");
            int panIndex = prevLine.lastIndexOf("Number");
            if (startIndex != -1) {
//                Toast.makeText(this, ""+det, Toast.LENGTH_SHORT).show();
                accountDetail.setName(det);

            }
            if (panIndex != -1) {
                accountDetail.setPan(det);
            }

            if (accountDetail.getName() != null && accountDetail.getPan() != null) {
                mCameraSource.stop();
                mCameraView.setVisibility(View.GONE);
                capture.setVisibility(View.VISIBLE);
                capture.setText("Re-Capture");

                setUpUi(accountDetail);
            }
//            str.subString(startIndex+1);
//        }
            prevLine = det;

        }

    }

    private void setUpUi(AccountDetail accountDetail) {
        name.setText(accountDetail.getName());
        panNo.setText(accountDetail.getPan());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_acc:
                if (!name.getText().toString().trim().isEmpty()) {
                    if (!panNo.getText().toString().trim().isEmpty()) {
                        if (!phoneNo.getText().toString().trim().isEmpty()) {
                            if (phoneNo.getText().toString().trim().length() == 10) {
                                accountDetail.setName(name.getText().toString().trim());
                                accountDetail.setPan(panNo.getText().toString().trim());
                                accountDetail.setPhoneNo(phoneNo.getText().toString().trim());
                                createNewAcc(accountDetail);
                            } else {
                                Toast.makeText(this, "Enter 10 digit phone no", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(this, "Enter phone no", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Enter PAN Number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Enter Valid Name", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void createNewAcc(AccountDetail accountDetail) {

        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.READ_SMS,

        };

        if (!ApiUtilsData.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }



        @SuppressLint("MissingPermission") String mPhoneNumber = tMgr.getLine1Number();
//        Toast.makeText(this, ""+mPhoneNumber, Toast.LENGTH_SHORT).show();
        if(accountDetail.getPhoneNo().equals(mPhoneNumber)){
            ApiUtilsData.addAccount(accountDetail);
            Toast.makeText(this, "Account Added Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this, "Enter Your Existing Phone Number", Toast.LENGTH_SHORT).show();
        }
    }
}
