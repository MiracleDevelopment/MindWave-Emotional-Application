package com.example.palsyeeg;

import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.palsyeeg.adapter.RecyclerAdapterMenuMusic;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class FragmentMusicSelection extends Fragment implements View.OnClickListener {
    public static ImageView playBt, pauseBt, priveBt, nextBt, loopBt, shuffleBt;
    RecyclerView mRecylcerView;
    ArrayList<String> Music;
    public static SeekBar mSeekbar;
    MediaPlayer mediaPlayer;
    Field[] field;
    int[] resId = {R.raw.boostcreativity, R.raw.relaxationdeeper, R.raw.endorphin, R.raw.instantstressrelief, R.raw.relaxationduaiinduction, R.raw.serenade, R.raw.thefastway};
    ArrayList<Integer> redId = new ArrayList<Integer>(resId.length);

    public static FragmentMusicSelection newInstance(String name) {
        FragmentMusicSelection fragmentMusicSelection = new FragmentMusicSelection();
        Bundle bundle = new Bundle();
        bundle.putString("key", name);
        fragmentMusicSelection.setArguments(bundle);
        return fragmentMusicSelection;
    }

    @Override
    public View onCreateView(LayoutInflater mInlflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = mInlflater.inflate(R.layout.activity_fragment_music_selection, container, false);
        initInstance(view);
        return view;
    }

    private void initInstance(View view) {
        playBt = (ImageView) view.findViewById(R.id.play_music_bt);
        pauseBt = (ImageView) view.findViewById(R.id.pause_music_bt);
        priveBt = (ImageView) view.findViewById(R.id.priv_music_bt);
        nextBt = (ImageView) view.findViewById(R.id.next_music_bt);
        loopBt = (ImageView) view.findViewById(R.id.looping_music_select);
        shuffleBt = (ImageView) view.findViewById(R.id.shuffle_music_select);
        mSeekbar = (SeekBar) view.findViewById(R.id.emotionMusic);
        mRecylcerView = (RecyclerView) view.findViewById(R.id.RecyclerMenuMusic);
        mRecylcerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecylcerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecylcerView.setItemAnimator(new DefaultItemAnimator());
        pauseBt.setVisibility(View.GONE);
        pauseBt.setOnClickListener(this);
        playBt.setOnClickListener(this);
        mSeekbar.setMax(100);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Music = new ArrayList<>();
        field = R.raw.class.getFields();

        for (int i = 0; i < field.length; i++) {
            Music.add(field[i].getName());
        }

        RecyclerAdapterMenuMusic adapter = new RecyclerAdapterMenuMusic(Music, resId);
        mRecylcerView.setAdapter(adapter);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_Bt:
                pauseBt.setVisibility(View.VISIBLE);
                playBt.setVisibility(View.GONE);
                mediaPlayer.start();
                break;
            case R.id.pause_Bt:
                playBt.setVisibility(View.VISIBLE);
                pauseBt.setVisibility(View.GONE);
                mediaPlayer.pause();
                break;
        }
    }

}
