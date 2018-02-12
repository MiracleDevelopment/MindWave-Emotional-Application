package com.example.palsyeeg;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentTime extends Fragment {
    CountDownTimer cdt;
    ProgressBar mProgressbar;
    TextView timeText;
    int plus = 0;
    private TimeronCllick timeronCllick;

    public static FragmentTime newInstance(String name) {
        FragmentTime fragmentTime = new FragmentTime();
        Bundle bundle = new Bundle();
        bundle.putString("key", name);
        fragmentTime.setArguments(bundle);
        return fragmentTime;
    }

    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = mInflater.inflate(R.layout.activity_fragment_time, container, false);
        initInstance(view);
        Timer();
        return view;
    }

    private void initInstance(View view) {
        mProgressbar = (ProgressBar) view.findViewById(R.id.progressbarTime);
        timeText = (TextView) view.findViewById(R.id.TimeText);
        mProgressbar.setMax(2);


    }

    private void Timer() {
        long count = 120000;

        cdt = new CountDownTimer(count, 1) {
            @Override
            public void onTick(long l) {
                int minute = (int) ((l / 1000) / 60);
                int second = (int) ((l / 1000) % 60);
                int updateProgressbar = (int) ((l / 1000) / 60);
                timeText.setText(String.valueOf(minute) + ":" + String.valueOf(second));

                mProgressbar.setProgress(updateProgressbar);

            }

            @Override
            public void onFinish() {
                cdt.cancel();
                Toast.makeText(getContext(), "Finish", Toast.LENGTH_SHORT).show();
                timeronCllick.onClickTimer();

            }
        }.start();
    }

    public interface TimeronCllick {
        void onClickTimer();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        timeronCllick = (TimeronCllick) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("onStart", "work");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume", "work");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPause", "work");
        cdt.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cdt.cancel();
    }
}
