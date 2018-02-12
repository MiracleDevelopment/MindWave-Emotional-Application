package com.example.palsyeeg;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.palsyeeg.adapter.AdapterMixFragment;
import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;

public class MixFragment extends Fragment {
    RecyclerView mRecylcerView;
    ArrayList<Fragment> fragment;


    public static MixFragment newInstance(String name) {
        MixFragment mix = new MixFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", name);
        mix.setArguments(bundle);
        return mix;
    }

    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = mInflater.inflate(R.layout.activity_mix_fragment, container, false);
        initInstance(view);
        return view;
    }

    private void initInstance(View view) {
        mRecylcerView = (RecyclerView) view.findViewById(R.id.RecyclerMixLayout);
        mRecylcerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecylcerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecylcerView.setItemAnimator(new DefaultItemAnimator());
        AdapterMixFragment adapter = new AdapterMixFragment(getFragmentManager(), fragment);
        mRecylcerView.setAdapter(adapter);

    }

}
