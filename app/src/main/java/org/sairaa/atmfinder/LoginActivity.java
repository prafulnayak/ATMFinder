package org.sairaa.atmfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.sairaa.atmfinder.Utils.ApiUtilsData;
import org.sairaa.atmfinder.Utils.Constants;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Constants {

    private TextView admin, user;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        admin = findViewById(R.id.admin);
        user = findViewById(R.id.user);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, AdminUserActivity.class);

        switch (view.getId()){
            case R.id.login:

                if(!admin.getText().toString().trim().isEmpty()){
                    if(!user.getText().toString().trim().isEmpty()){
                        int typeUser = ApiUtilsData.checkUserOrAdmin(admin.getText().toString().trim(),user.getText().toString().trim());

                            switch (typeUser){
                                case adminT:
                                    intent.putExtra(adminUserT,adminT);
                                    startActivity(intent);
                                    break;

                                case userT:
                                    intent.putExtra(adminUserT,userT);
                                    startActivity(intent);
                                    break;

                                 default:
                                     Toast.makeText(this, "Invalid UserName and Password", Toast.LENGTH_SHORT).show();
                            }

                    }else {
                        Toast.makeText(this, "Fill password", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(this, "Fill User Name", Toast.LENGTH_SHORT).show();
                }
                // open Admin Part

                break;


        }

    }
}
