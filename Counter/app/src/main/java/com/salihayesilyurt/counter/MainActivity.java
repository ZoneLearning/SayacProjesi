package com.salihayesilyurt.counter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private int counter = 0;
    private Button btnCounter;
    private SharedPreferences sharedPreferences, settings;
    private RelativeLayout relativeLayout;
    private Boolean sound_state, vibrate_state;
    private MediaPlayer sound;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCounter = (Button) findViewById(R.id.btn_counter);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_counter);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        sound = MediaPlayer.create(getApplicationContext(), R.raw.btn_counter_sound);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        LoadSettings();

        counter = sharedPreferences.getInt("count_key", 0);
        btnCounter.setText("" + counter);


        btnCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_state) {
                    sound.start();
                }else if(vibrate_state){
                    vibrator.vibrate(250);
                    //vibrate icin izin almak gerekli.
                }

                counter++;
                btnCounter.setText("" + counter);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void LoadSettings() {
        String position = settings.getString("background", "4");
        switch (Integer.valueOf(position)) {
            case 0:
                //Eger R. diyerek almak istiyorsak setBackgroundResource metodunu kullanacagiz.
                relativeLayout.setBackgroundResource(R.color.background_kirmizi);
                break;
            case 1:
                relativeLayout.setBackgroundResource(R.color.background);
                break;
            case 2:
                relativeLayout.setBackgroundResource(R.color.background_mavi);
                break;
            case 3:
                //Direk olarak background a color vermek icin ise setBackgroundColor() metodunu kulllaniyoruz.
                relativeLayout.setBackgroundColor(Color.DKGRAY);
                break;
            case 4:
                relativeLayout.setBackgroundColor(Color.LTGRAY);
                break;

        }
        /*Butona ses eklemek icin kullandigim site:
        http://www.soundjay.com/button-sounds-2.html*/
        sound_state = settings.getBoolean("sound", false);
        vibrate_state = settings.getBoolean("vibration", false);
        //onClickListener gibi implemente edip, onSharedPreferenceChanged() metodunu kullanmak icin
        settings.registerOnSharedPreferenceChangeListener(MainActivity.this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("count_key", counter);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_reset) {
            counter = 0;
            btnCounter.setText("" + counter);
        }

        return super.onOptionsItemSelected(item);
    }

    //yapılan degisiklikler anında saglansın, uygulamyı
    //kapatıp acmaya gerek kalmasın idye implemente ettigimiz metod.
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        LoadSettings();
    }
}
