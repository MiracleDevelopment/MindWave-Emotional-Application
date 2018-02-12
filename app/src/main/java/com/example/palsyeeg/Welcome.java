package com.example.palsyeeg;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.app.Activity;
import android.content.Intent;

public class Welcome extends Activity {
    protected boolean _active = true;
    protected int _splashTime = 5000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        MediaPlayer splash = MediaPlayer.create(this, R.raw.splash);
        splash.start();

        Thread splashTread = new Thread() {
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                } finally {
                    finish();
                    Intent goMain = new Intent(getApplicationContext(), MainWindowPager.class);
                    startActivity(goMain);
                }
            }

        };
        splashTread.start();
    }
}

   