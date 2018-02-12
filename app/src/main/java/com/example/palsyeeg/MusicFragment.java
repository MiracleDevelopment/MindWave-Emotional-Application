package com.example.palsyeeg;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palsyeeg.adapter.OnChangeValueParameter;

import java.util.ArrayList;

public class MusicFragment extends Fragment implements View.OnClickListener {
    ImageView album, playBt, privBt, nextBt, loopBt, shuffleBt, pauseBt;
    MediaPlayer mediaPlayer;
    public static SeekBar mProgress;
    public static TextView emoTextStatusLevel;


    public static MusicFragment newInstance(int name) {
        MusicFragment musicFragment = new MusicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key", name);
        musicFragment.setArguments(bundle);
        return musicFragment;
    }

    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = mInflater.inflate(R.layout.activity_music_fragment, container, false);
        initInstance(view);
        return view;
    }

    private void initInstance(View view) {
        mProgress = (SeekBar) view.findViewById(R.id.emotionGate);
        emoTextStatusLevel = (TextView) view.findViewById(R.id.status_emotionlevel);
        album = (ImageView) view.findViewById(R.id.album);
        playBt = (ImageView) view.findViewById(R.id.play_Bt);
        pauseBt = (ImageView) view.findViewById(R.id.pause_Bt);
        privBt = (ImageView) view.findViewById(R.id.back_bt);
        nextBt = (ImageView) view.findViewById(R.id.next_Bt);
        loopBt = (ImageView) view.findViewById(R.id.loop_Bt);
        shuffleBt = (ImageView) view.findViewById(R.id.shuffle_bt);
        mProgress.setMax(100);
        pauseBt.setVisibility(View.GONE);
        InfoMusicPlayerSetUp();
        playBt.setOnClickListener(this);
        pauseBt.setOnClickListener(this);

    }

    private void InfoMusicPlayerSetUp() {

//        Field[] fields = R.raw.class.getFields();
//        for (int i = 0; i < fields.length; i++) {
//            Log.d("Music", fields[i].getName());
//        }

//        mediaMetadataRetriever = new MediaMetadataRetriever();
//        mediaMetadataRetriever.setDataSource(getContext(), Uri.parse(String.valueOf(R.raw.boostcreativity)));
//        byte[] image = mediaMetadataRetriever.getEmbeddedPicture();
//        album.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
//        mediaMetadataRetriever.release();

    }


    private void MusicSetUp() {
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.boostcreativity);
        mediaPlayer.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pause_Bt:
                pauseBt.setVisibility(View.GONE);
                playBt.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                break;
            case R.id.play_Bt:
                pauseBt.setVisibility(View.VISIBLE);
                playBt.setVisibility(View.GONE);
                MusicSetUp();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
