package org.sairaa.atmfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.sairaa.atmfinder.Utils.Constants;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Constants {

    private Button admin;
    private Button user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        admin = findViewById(R.id.admin);
        admin.setOnClickListener(this);

        user = findViewById(R.id.user);
        user.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, AdminActivity.class);

        switch (view.getId()){
            case R.id.admin:
                intent.putExtra(adminUserT,adminT);
                startActivity(intent);
                break;

            case  R.id.user:
                intent.putExtra(adminUserT,userT);
                startActivity(intent);
                break;
        }

    }
}
