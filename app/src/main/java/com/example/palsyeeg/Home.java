package com.example.palsyeeg;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Home extends Fragment implements View.OnClickListener {
    TextView connectingBt;
    private HomeFragmentListener listener;

    public static Home newInstance(String name) {
        Home home = new Home();
        Bundle bundle = new Bundle();
        bundle.putString("key", name);
        home.setArguments(bundle);
        return home;
    }

    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = mInflater.inflate(R.layout.activity_home, container, false);
        iniInstance(view);
        return view;
    }

    private void iniInstance(View view) {
        connectingBt = (TextView) view.findViewById(R.id.connecting_bt);
        connectingBt.setOnClickListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (HomeFragmentListener) getActivity();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.connecting_bt:
                listener.onClickButtonListener();
                break;
        }
    }

    public interface HomeFragmentListener {
        public void onClickButtonListener();
    }


}
