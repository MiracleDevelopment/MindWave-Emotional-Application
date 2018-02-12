package com.example.palsyeeg.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.palsyeeg.R;

import java.util.ArrayList;

/**
 * Created by ipati on 6/2/2560.
 */

public class AdapterMixFragment extends RecyclerView.Adapter<AdapterMixFragment.MyViewHolder> {

    ArrayList<Fragment> fragments;
    FragmentManager manager;

    public AdapterMixFragment(FragmentManager fragmentManager, ArrayList<Fragment> fragment) {
        this.fragments = fragment;
        this.manager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mixlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position == 0) {
            manager.beginTransaction().add(R.id.fragmentMixFragment, fragments.get(position)).commit();
        } else if (position == 1) {
            manager.beginTransaction().add(R.id.fragmentMixFragment, fragments.get(position)).commit();
        }
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
