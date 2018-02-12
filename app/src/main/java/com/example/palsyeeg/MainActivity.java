package com.example.palsyeeg;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.media.MediaPlayer;

import com.neurosky.thinkgear.*;

public class MainActivity extends Activity {
	
	 public void goBack (View v) {
			finish();
		}
	 
	 public void doGuide(View view) {
			Intent itn = new Intent(this, GuideActivity.class);
			startActivity(itn);
			finish();
		}
	 
	BluetoothAdapter bluetoothAdapter;

	TextView tv, PoorSignal, RawData, HeartRate, Attention, Meditation, Blink,
			RawCount, RawMulti;
	Button b;
	ImageButton imgConnect, imgGuild;
	ImageView imgMood;
	MediaPlayer alarm, lowbat;

	TGDevice tgDevice;
	final boolean rawEnabled = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		tv = (TextView) findViewById(R.id.textView1);
		PoorSignal = (TextView) findViewById(R.id.tvPoorSignal);
		RawData = (TextView) findViewById(R.id.tvRawData);
		HeartRate = (TextView) findViewById(R.id.tvHeartRate);
		Attention = (TextView) findViewById(R.id.tvAttention);
		Meditation = (TextView) findViewById(R.id.tvMeditation);
		Blink = (TextView) findViewById(R.id.tvBlink);
		RawCount = (TextView) findViewById(R.id.tvRawCount);
		RawMulti = (TextView) findViewById(R.id.tvRawMulti);

		imgMood = (ImageView) findViewById(R.id.imgMood);
		imgConnect = (ImageButton) findViewById(R.id.imageButton1);
		
		alarm = MediaPlayer.create(this, R.raw.alarm);
		lowbat = MediaPlayer.create(this, R.raw.lowbat);
		
		tv.setText("");
		tv.append("Android version: "
				+ Integer.valueOf(android.os.Build.VERSION.SDK) + "\n");
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter == null) {
			// Alert user that Bluetooth is not available
			Toast.makeText(this, "Bluetooth not available", Toast.LENGTH_LONG)
					.show();
			finish();
			return;
		} else {
			/* create the TGDevice */
			tgDevice = new TGDevice(bluetoothAdapter, handler);
		}
	}

	@Override
	public void onDestroy() {
		tgDevice.close();
		super.onDestroy();
	}

	/**
	 * Handles messages from TGDevice
	 */
	private final Handler handler = new Handler() {
		int _PoorSignal, _RawData, _HeartRate, _Attention, _Meditation, _Blink,
		_RawCount, _RawMultiCH1, _RawMultiCH2;;
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TGDevice.MSG_STATE_CHANGE:

				switch (msg.arg1) {
				case TGDevice.STATE_IDLE:
					break;
				case TGDevice.STATE_CONNECTING:
					tv.append("Connecting...\n");
					break;
				case TGDevice.STATE_CONNECTED:
					tv.append("Connected.\n");
					tgDevice.start();
					Toast.makeText(getApplicationContext(), "Connecting!",
							Toast.LENGTH_SHORT).show();
					imgMood.setImageResource(R.drawable.emoconnecting);
					imgConnect.setImageResource(R.drawable.btnconnected);
					
					break;
				case TGDevice.STATE_NOT_FOUND:
					tv.append("Can't find\n");
					break;
				case TGDevice.STATE_NOT_PAIRED:
					tv.append("not paired\n");
					break;
				case TGDevice.STATE_DISCONNECTED:
					tv.append("Disconnected mang\n");
					imgConnect.setImageResource(R.drawable.btnconnect);
				}
				break;

			case TGDevice.MSG_POOR_SIGNAL:
				// signal = msg.arg1;
				_PoorSignal = msg.arg1;
				PoorSignal.setText("PoorSignal: " + _PoorSignal + "\n");
				break;
			case TGDevice.MSG_RAW_DATA:
				// raw1 = msg.arg1;
				_RawData = msg.arg1;
				RawData.setText("Got raw: " +_RawData + "\n");
				break;
			case TGDevice.MSG_HEART_RATE:
				_HeartRate = msg.arg1;
				HeartRate.setText("Heart rate: " + _HeartRate + "\n");
				break;
			case TGDevice.MSG_ATTENTION:
				// att = msg.arg1;
				_Attention = msg.arg1;
				Attention.setText("Attention: " + _Attention + "\n");
				// Log.v("HelloA", "Attention: " + att + "\n");
				break;
			case TGDevice.MSG_MEDITATION:
				_Meditation = msg.arg1;
				Meditation.setText("Meditation: " + _Meditation + "\n");
				break;
			case TGDevice.MSG_BLINK:
				_Blink = msg.arg1;
				Blink.setText("Blink: " + _Blink + "\n");
				break;
			case TGDevice.MSG_RAW_COUNT:
				_RawCount = msg.arg1;
				RawCount.setText("Raw Count: " + _RawCount + "\n");
				break;
			case TGDevice.MSG_LOW_BATTERY:
				Toast.makeText(getApplicationContext(), "Low battery!",
						Toast.LENGTH_SHORT).show();
				lowbat.start();
				//+Alarm
				break;
			case TGDevice.MSG_RAW_MULTI:
				TGRawMulti rawM = (TGRawMulti) msg.obj;
				_RawMultiCH1 = rawM.ch1;
				_RawMultiCH2 = rawM.ch2;
				RawMulti.setText("Raw1: " + _RawMultiCH1 + "\nRaw2: " + _RawMultiCH2);
			default:
				
				if (_Meditation == 0 && _Attention == 0){
					imgMood.setImageResource(R.drawable.emoconnecting);
					
				}
				else if(_Meditation < 20 && _Attention < 20 && _Blink < 20){
					imgMood.setImageResource(R.drawable.emosleep);
					
				}
				else if (_Meditation < 40 && _Attention < 40){
					imgMood.setImageResource(R.drawable.emoimagin);
					
				}
				else if (_Meditation < 60 && _Attention < 60){
					imgMood.setImageResource(R.drawable.emorelax);
					lowbat.start();
					
					}
				else if (_Meditation < 80 && _Attention < 80){
					imgMood.setImageResource(R.drawable.emofocused);
					lowbat.stop();
					alarm.start();
					}
				else if (_Meditation < 100 && _Attention < 100){
					imgMood.setImageResource(R.drawable.emoalertness);
					
					}
				else
					imgMood.setImageResource(R.drawable.emoserious);
					
				break;
			}
		}
	};

	public void doStuff(View view) {
		if (tgDevice.getState() != TGDevice.STATE_CONNECTING
				&& tgDevice.getState() != TGDevice.STATE_CONNECTED)
			tgDevice.connect(rawEnabled);
		// tgDevice.ena
	}
}