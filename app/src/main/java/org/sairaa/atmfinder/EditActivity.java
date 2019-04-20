package org.sairaa.atmfinder;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import org.sairaa.atmfinder.Repository.AtmViewModel;
import org.sairaa.atmfinder.Utils.Constants;
import org.sairaa.atmfinder.database.AtmDetails;

public class EditActivity extends AppCompatActivity implements View.OnClickListener, Constants {

    private Spinner bankSpinner;
    private EditText latitude;
    private EditText longitude;
    private CheckBox deposite, withDraw;
    private Switch workingStatus;
    private Button submit;

    private AtmViewModel viewModel;

    private String bankName = null;
    private int position = -1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        init();

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

        int taskEditOrNew = getIntent().getIntExtra(adminUserT,0);
        position = getIntent().getIntExtra("position",-1);

        switch (taskEditOrNew){
            case adminT:
                editDataAndUpdate();
                break;
            case  userT:
                onlyViewData();
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

    private void editDataAndUpdate() {
//        if(position>=0){
//            AtmDetails details = viewModel.getItemAtLoc(position);
//        }

    }

    private void onlyViewData() {

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
                insertToDb();
                break;
        }
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
