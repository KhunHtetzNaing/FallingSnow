package com.htetznaing.fallingsnow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private static final int Req_Code = 1;
    Switch onOff;
    TextView tvSpeed,tvCount;
    SeekBar Speed,Count;
    Button btnChoose;
    int color = 0xffffff00;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AdView B;
    AdRequest adRequest;
    InterstitialAd IAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("myFile",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        adRequest = new AdRequest.Builder().build();
        B = (AdView) findViewById(R.id.adView);
        B.loadAd(adRequest);
        IAd = new InterstitialAd(this);
        IAd.setAdUnitId("ca-app-pub-4173348573252986/5384792194");
        IAd.loadAd(adRequest);
        IAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                if (!IAd.isLoaded()){
                    IAd.loadAd(adRequest);
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if (!IAd.isLoaded()){
                    IAd.loadAd(adRequest);
                }
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                if (!IAd.isLoaded()){
                    IAd.loadAd(adRequest);
                }
            }
        });


        onOff = (Switch) findViewById(R.id.onOff);
        tvSpeed = (TextView) findViewById(R.id.tvSpeed);
        tvCount = (TextView) findViewById(R.id.tvCount);
        Speed = (SeekBar) findViewById(R.id.speed);
        Count = (SeekBar) findViewById(R.id.count);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnChoose.setOnClickListener(this);

        firstStep();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Application \"Snow on screen winter effect\" displays very realistic animation of falling snow on the screen of your phone. A real, snowy, frosty winter will spread in your phone.\n Download free at Google Play Store : http://play.google.com/store/apps/details?id=com.htetznaing.fallingsnow");
                startActivity(new Intent(Intent.createChooser(intent,"Falling Snow Android App")));
            }
        });
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
            startActivity(new Intent(MainActivity.this,About.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChoose:
                openDialog(true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Attention!");
        builder.setMessage("Do you want to Exit ?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showAds();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showAds();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    void openDialog(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(MainActivity.this, color, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                MainActivity.this.color = color;
                displayColor();
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }
        });
        dialog.show();
    }

    public void showAds(){
        if (IAd.isLoaded()){
            IAd.show();
        }else{
            IAd.loadAd(adRequest);
        }
    }

    void displayColor() {
        editor.putInt("color",color);
        editor.commit();
        startLOL();
        showAds();
    }

    public void firstStep(){
        checkPermission();
        int checkOnoff = sharedPreferences.getInt("onOff",0);
        if (checkOnoff!=0){
            onOff.setChecked(true);
            startLOL();
        }

        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showAds();
                checkPermission();
                if (isChecked!=false){
                    editor.putInt("onOff",1);
                    startLOL();
                }else{
                    editor.putInt("onOff",0);
                    stopService(new Intent(MainActivity.this,MyService.class));
                }
                editor.commit();
            }
        });

        int forSpeed = sharedPreferences.getInt("speed",0);
        Speed.setProgress(forSpeed);
        tvSpeed.setText(String.valueOf(forSpeed));


        Speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSpeed.setText(String.valueOf(progress));
                editor.putInt("speed",progress);
                editor.commit();
                startLOL();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        int forCount = sharedPreferences.getInt("count",150);
        Count.setProgress(forCount);
        tvCount.setText(String.valueOf(forCount));

        Count.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCount.setText(String.valueOf(progress));
                editor.putInt("count",progress);
                editor.commit();
                startLOL();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void startLOL(){
        try {
            checkPermission();
            stopService(new Intent(MainActivity.this, MyService.class));
            startService(new Intent(MainActivity.this, MyService.class));
            onOff.setChecked(true);
        }catch (Exception e){
            checkPermission();
            onDisplayPopupPermission();
        }
    }

    public void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("IMPORTANT!!");
                builder.setMessage("your need to grant Permission for\nPermit drawing over other apps");
                builder.setPositiveButton("Grant it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, Req_Code);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }else{
            if (sharedPreferences.getInt("mi",0)!=1) {
                editor.putInt("mi", 1);
                editor.commit();
                onDisplayPopupPermission();
            }
        }
    }

    private void onDisplayPopupPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        }else{
            if (isMIUI()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("IMPORTANT!!");
                builder.setMessage("your are on MIUI Rom! you need to Allow Permission for\nDisplay pop-up window!");
                builder.setPositiveButton("Grant it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            // MIUI 8
                            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                            localIntent.putExtra("extra_pkgname", getPackageName());
                            startActivity(localIntent);
                        } catch (Exception e) {
                            try {
                                // MIUI 5/6/7
                                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                                localIntent.putExtra("extra_pkgname", getPackageName());
                                startActivity(localIntent);
                            } catch (Exception e1) {
                                // Otherwise jump to application details
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }


    private static boolean isMIUI() {
        String device = Build.MANUFACTURER;
        if (device.equals("Xiaomi")) {
            try {
                Properties prop = new Properties();
                prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
                return prop.getProperty("ro.miui.ui.version.code", null) != null
                        || prop.getProperty("ro.miui.ui.version.name", null) != null
                        || prop.getProperty("ro.miui.internal.storage", null) != null;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}
