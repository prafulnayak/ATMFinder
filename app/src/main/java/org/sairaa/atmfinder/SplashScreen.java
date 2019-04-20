package org.sairaa.atmfinder;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        final Intent intent = new Intent(this,LoginActivity.class);

        //Splash will be dissapeared after 2 Sec
        new Handler().postDelayed(() -> {
            startActivity(intent);
            finish();
        }, 2000);
    }
}
