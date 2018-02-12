package com.example.palsyeeg.adapter;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.palsyeeg.FragmentMusicSelection;
import com.example.palsyeeg.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ipati on 5/2/2560.
 */

public class RecyclerAdapterMenuMusic extends RecyclerView.Adapter<RecyclerAdapterMenuMusic.MyViewHolder> {
    private ArrayList<String> MusicAdapter;
    private int[] MusicId;
    private MediaPlayer mediaPlayer;

    public RecyclerAdapterMenuMusic(ArrayList<String> music, int[] resId) {
        this.MusicAdapter = music;
        this.MusicId = resId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutmenu_music, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.nameMusic.setText(MusicAdapter.get(position));
        holder.setOnClickSelectionMusic(new onClickSelectionMusic() {
            @Override
            public void onClickMusicSelect(View view, final int position) {
                mediaPlayer = MediaPlayer.create(view.getContext(), MusicId[position]);
                FragmentMusicSelection.playBt.setVisibility(View.GONE);
                FragmentMusicSelection.pauseBt.setVisibility(View.VISIBLE);
                mediaPlayer.start();

                FragmentMusicSelection.playBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentMusicSelection.playBt.setVisibility(View.GONE);
                        FragmentMusicSelection.pauseBt.setVisibility(View.VISIBLE);
                        mediaPlayer.start();
                    }
                });

                FragmentMusicSelection.pauseBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentMusicSelection.playBt.setVisibility(View.VISIBLE);
                        FragmentMusicSelection.pauseBt.setVisibility(View.GONE);
                        mediaPlayer.pause();
                    }
                });

                FragmentMusicSelection.nextBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });

                FragmentMusicSelection.priveBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return MusicAdapter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView album;
        TextView nameMusic;
        private onClickSelectionMusic setonClickSelectionMusic;

        public MyViewHolder(View itemView) {
            super(itemView);
            album = (ImageView) itemView.findViewById(R.id.albummenu);
            nameMusic = (TextView) itemView.findViewById(R.id.nameMuic);
            itemView.setOnClickListener(this);
        }

        public void setOnClickSelectionMusic(com.example.palsyeeg.adapter.onClickSelectionMusic onClickSelectionMusic) {
            this.setonClickSelectionMusic = onClickSelectionMusic;
        }

        @Override
        public void onClick(View view) {
            setonClickSelectionMusic.onClickMusicSelect(view, getAdapterPosition());
        }
    }
}
