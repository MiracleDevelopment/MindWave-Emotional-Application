package com.example.palsyeeg;

import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.palsyeeg.adapter.OnChangeValueParameter;
import com.example.palsyeeg.adapter.RecyclerViewAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGRawMulti;

import java.util.ArrayList;

public class MainWindowPager extends AppCompatActivity implements Home.HomeFragmentListener, FragmentTime.TimeronCllick, RecyclerViewAdapter.SetOnStateClickActivite {
    Toolbar mToolbar;
    BluetoothAdapter bluetoothAdapter;
    TGDevice tgDevice;
    ImageView statusImage;
    RecyclerView mRecyclerView;
    ArrayList<String> nameMenu = new ArrayList<>();
    ActionBarDrawerToggle mActionBarDrawerToggle;
    DrawerLayout mDrawerlayout;
    final boolean rawEnabled = false;
    private MusicFragment musicFragment;
    int Meta;
    boolean stateUpdateValue = false;
    boolean stateClickMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window_pager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer_mainwindow);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMenu);
        statusImage = (ImageView) findViewById(R.id.status_connect);
//        musicFragment = MusicFragment.newInstance(1);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Alert user that Bluetooth is not available
            Toast.makeText(getApplicationContext(), "Bluetooth not available", Toast.LENGTH_LONG)
                    .show();
            finish();
            return;
        } else {
            /* create the TGDevice */
            tgDevice = new TGDevice(bluetoothAdapter, handler);
        }


        setUpDrawerBar();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_fragment, Home.newInstance("One")).commit();

    }

    private void setUpDrawerBar() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerlayout, mToolbar, R.string.open, R.string.close);
        mDrawerlayout.setDrawerListener(mActionBarDrawerToggle);
        setUpMenuToolbar();
    }

    private void setUpMenuToolbar() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        nameMenu.add(0, "MUSIC THERAPY");
        nameMenu.add(1, "ตรวจวัดคลื่นสมอง");
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getSupportFragmentManager(), nameMenu);
        mRecyclerView.setAdapter(adapter);

    }


    //Todo:Reader
    private final Handler handler = new Handler() {
        int _PoorSignal, _RawData, _HeartRate, _Attention, _Meditation, _Blink,
                _RawCount, _RawMultiCH1, _RawMultiCH2;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TGDevice.MSG_STATE_CHANGE:
                    switch (msg.arg1) {
                        case TGDevice.STATE_CONNECTING:
                            //Todo:Connecting
                            Toast.makeText(getApplicationContext(), "Connecting", Toast.LENGTH_SHORT).show();
                            break;
                        case TGDevice.STATE_CONNECTED:
                            //Todo:Connected
                            tgDevice.start();
                            Toast.makeText(getApplicationContext(), "Connection", Toast.LENGTH_SHORT).show();
                            statusImage.setImageResource(R.drawable.cicle_status_connect);
                            getSupportFragmentManager().beginTransaction().add(R.id.layout_fragment
                                    , FragmentTime.newInstance("Time")).commit();
                            stateUpdateValue = false;
                            break;
                        case TGDevice.STATE_NOT_FOUND:
                            Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_SHORT).show();
                            break;
                        case TGDevice.STATE_NOT_PAIRED:
                            Toast.makeText(getApplicationContext(), "not Pair", Toast.LENGTH_SHORT).show();
                            break;
                        case TGDevice.STATE_DISCONNECTED:
                            Toast.makeText(getApplicationContext(), "DisConnection", Toast.LENGTH_SHORT).show();
                            statusImage.setImageResource(R.drawable.circle_status_disconnect);
                            getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment
                                    , Home.newInstance("One")).commit();
                            stateUpdateValue = false;
                    }
                    break;

                case TGDevice.MSG_POOR_SIGNAL:
                    // signal = msg.arg1;
                    _PoorSignal = msg.arg1;
                    break;
                case TGDevice.MSG_RAW_DATA:
                    // raw1 = msg.arg1;
                    _RawData = msg.arg1;
                    break;
                case TGDevice.MSG_HEART_RATE:
                    _HeartRate = msg.arg1;
                    break;
                case TGDevice.MSG_ATTENTION:
                    // att = msg.arg1;
                    _Attention = msg.arg1;
                    // Log.v("HelloA", "Attention: " + att + "\n");
                    break;
                case TGDevice.MSG_MEDITATION:
                    _Meditation = msg.arg1;
                    break;
                case TGDevice.MSG_BLINK:
                    _Blink = msg.arg1;
                    break;
                case TGDevice.MSG_RAW_COUNT:
                    _RawCount = msg.arg1;
                    break;
                case TGDevice.MSG_LOW_BATTERY:
                    Toast.makeText(getApplicationContext(), "Low battery!",
                            Toast.LENGTH_SHORT).show();

                    break;
                case TGDevice.MSG_RAW_MULTI:
                    TGRawMulti rawM = (TGRawMulti) msg.obj;
                    _RawMultiCH1 = rawM.ch1;
                    _RawMultiCH2 = rawM.ch2;

                default:
                    if (stateUpdateValue) {
                        if (_Meditation == 0 && _Attention == 0) {
//                            MusicFragment.mProgress.setProgress(_Meditation);
                        } else if (_Meditation < 20 && _Attention < 20 && _Blink < 20) {

                            MusicFragment.mProgress.setProgress(_Meditation);
                            MusicFragment.emoTextStatusLevel.setText("3");
                            MusicFragment.emoTextStatusLevel.setBackgroundColor(Color.parseColor("#fbaf5d"));


                        } else if (_Meditation < 40 && _Attention < 40) {
//                        imgMood.setImageResource(R.drawable.emoimagin);
                            MusicFragment.mProgress.setProgress(_Meditation);
//                            musicFragment.onChageValue(_Meditation, _Attention);

                        } else if (_Meditation < 60 && _Attention < 60) {
//                        imgMood.setImageResource(R.drawable.emorelax);
//                        lowbat.start();
                            MusicFragment.mProgress.setProgress(_Meditation);
                            MusicFragment.emoTextStatusLevel.setText("2");
                            MusicFragment.emoTextStatusLevel.setBackgroundColor(Color.parseColor("#fff568"));
//                            musicFragment.onChageValue(_Meditation, _Attention);

                        } else if (_Meditation < 80 && _Attention < 80) {
                            MusicFragment.mProgress.setProgress(_Meditation);
                            MusicFragment.emoTextStatusLevel.setText("2");
                            MusicFragment.emoTextStatusLevel.setBackgroundColor(Color.parseColor("#fff568"));
//
                        } else if (_Meditation < 100 && _Attention < 100) {

                            MusicFragment.mProgress.setProgress(_Meditation);
                            MusicFragment.emoTextStatusLevel.setText("1");
                            MusicFragment.emoTextStatusLevel.setBackgroundColor(Color.parseColor("#82ca9c"));
//                            musicFragment.onChageValue(_Meditation, _Attention);
//                        imgMood.setImageResource(R.drawable.emoalertness);

                        } else
                            MusicFragment.mProgress.setProgress(_Meditation);
//                        musicFragment.onChageValue(_Meditation, _Attention);
//                        imgMood.setImageResource(R.drawable.emoserious);

                        break;
                    } else if (stateClickMenu) {
                        if (_Meditation == 0 && _Attention == 0) {
                            FragmentMusicSelection.mSeekbar.setProgress(_Meditation);
                        } else if (_Meditation < 20 && _Attention < 20 && _Blink < 20) {
                            FragmentMusicSelection.mSeekbar.setProgress(_Meditation);
                        } else if (_Meditation < 40 && _Attention < 40) {
                            FragmentMusicSelection.mSeekbar.setProgress(_Meditation);
                        } else if (_Meditation < 60 && _Attention < 60) {
                            FragmentMusicSelection.mSeekbar.setProgress(_Meditation);
                        } else if (_Meditation < 80 && _Attention < 80) {
                            FragmentMusicSelection.mSeekbar.setProgress(_Meditation);
                        } else if (_Meditation < 100 && _Attention < 100) {
                            FragmentMusicSelection.mSeekbar.setProgress(_Meditation);
                        } else

                            break;
                    }
            }
        }
    };


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onClickButtonListener() {
        if (tgDevice.getState() != TGDevice.STATE_CONNECTING
                && tgDevice.getState() != TGDevice.STATE_CONNECTED)
            tgDevice.connect(rawEnabled);
    }

    @Override
    public void onClickTimer() {
        stateUpdateValue = true;
        stateClickMenu = false;
        getFragmentManager().beginTransaction().replace(R.id.layout_fragment
                , MusicFragment.newInstance(1)).commit();
    }


    @Override
    public void OnClickActivate() {
        stateUpdateValue = false;
        stateClickMenu = true;

    }

}
