package org.sairaa.atmfinder;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;

import org.sairaa.atmfinder.Repository.AtmViewModel;
import org.sairaa.atmfinder.Utils.Constants;
import org.sairaa.atmfinder.database.AtmDetails;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity implements View.OnClickListener, Constants {

    private Spinner bankSpinner;
    private EditText latitude;
    private EditText longitude;
    private CheckBox deposite, withDraw;
    private Switch workingStatus;
    private Button submit;

    private AtmViewModel viewModel;

    private String bankName = null;
    private int atmId = -1;
    private int taskEditOrNew = 0;

    AtmDetails atmDetails= null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        init();

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

        taskEditOrNew = getIntent().getIntExtra(adminUserT,0);
        atmId = getIntent().getIntExtra("atmId",-1);
        String data = getIntent().getStringExtra("data");

        if(data != null){
             atmDetails = new Gson().fromJson(data, AtmDetails.class);
        }


        switch (taskEditOrNew){
            case adminT:
                editDataAndUpdate(atmDetails);
                break;
            case  userT:
                onlyViewData(atmDetails);
                break;
        }

        viewModel = ViewModelProviders.of(this).get(AtmViewModel.class);

        bankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bankName = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void editDataAndUpdate(AtmDetails atmDetails) {
        List<String> bankList = new ArrayList<>();
        bankList.add(0,atmDetails.getBankName());
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,bankList);
        bankSpinner.setAdapter(arrayAdapter);
        bankSpinner.setEnabled(false);

        latitude.setText(String.valueOf(atmDetails.getLatitude()));
        latitude.setEnabled(false);
        longitude.setText(String.valueOf(atmDetails.getLongitude()));
        longitude.setEnabled(false);

        if(atmDetails.getCashDeposite() == 1){
            deposite.setChecked(true);
        }else
            deposite.setChecked(false);

        if(atmDetails.getCashWithdraw() == 1){
            withDraw.setChecked(true);
        }else
            withDraw.setChecked(false);

        if(atmDetails.getWorkingStatus() == 1){
            workingStatus.setChecked(true);
        }else
            workingStatus.setChecked(false);

        submit.setText("Update");


//        if(position>=0){
//            AtmDetails details = viewModel.getItemAtLoc(position);
//        }

    }

    private void onlyViewData(AtmDetails atmDetails) {

    }

    private void init() {
        bankSpinner = findViewById(R.id.spinnerBank);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        deposite = findViewById(R.id.diposite);
        withDraw = findViewById(R.id.withdraw);
        workingStatus = findViewById(R.id.on_off);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:
                if(taskEditOrNew == adminT && atmId == -1){
                    insertToDb();
                }else {
                    updateToDb();
                }

                break;
        }
    }

    private void updateToDb() {
        int depos = deposite.isChecked()?1:0;
        int withD = withDraw.isChecked()?1:0;
        int status = workingStatus.isChecked()?1:0;

        viewModel.update(depos,withD,status,atmId);
        Toast.makeText(this, "Updated ATM Detials", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void insertToDb() {

        int depos = deposite.isChecked()?1:0;
        int withD = withDraw.isChecked()?1:0;
        int status = workingStatus.isChecked()?1:0;


        if(bankName!= null && !bankName.equals("Select Bank")){
            if(!latitude.getText().toString().trim().isEmpty()){
                if(!longitude.getText().toString().trim().isEmpty()){
                    AtmDetails atmDetails = new AtmDetails(bankName,
                            Double.parseDouble(latitude.getText().toString().trim()),
                            Double.parseDouble(longitude.getText().toString().trim()),
                            withD,
                            depos,1,
                            status,"Good Location");
                    viewModel.insert(atmDetails);
                    Toast.makeText(this, "New ATM Details Added Succesfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else
                    Toast.makeText(this, "Enter Longitude", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, "Enter Latitude", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Select Bank", Toast.LENGTH_SHORT).show();
        }



    }
}
