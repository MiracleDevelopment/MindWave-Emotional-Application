package com.example.palsyeeg.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.palsyeeg.FragmentMusicSelection;
import com.example.palsyeeg.FragmentTime;
import com.example.palsyeeg.R;

import java.util.ArrayList;

/**
 * Created by ipati on 4/2/2560.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<String> nameMenuAdapter;
    FragmentManager supportFragment;
    private SetOnStateClickActivite SetOnClickActivite;

    public RecyclerViewAdapter(FragmentManager supportFragmentManager, ArrayList<String> nameMenu) {
        this.nameMenuAdapter = nameMenu;
        this.supportFragment = supportFragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.nameManuVeiwHolder.setText(nameMenuAdapter.get(position));
        holder.setmOnClickRecycler(new onClickRecyclerView() {
            @Override
            public void onClickRecyCler(View view, int position) {
                SetOnClickActivite = (SetOnStateClickActivite) view.getContext();
                if (position == 0) {
                    SetOnClickActivite.OnClickActivate();
                    supportFragment.beginTransaction().replace(R.id.layout_fragment
                            , FragmentMusicSelection.newInstance("Three")).commit();
                } else if (position == 1) {
                    supportFragment.beginTransaction().replace(R.id.layout_fragment
                            , FragmentTime.newInstance("time")).commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameMenuAdapter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameManuVeiwHolder;
        private onClickRecyclerView mOnClickRecycler;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameManuVeiwHolder = (TextView) itemView.findViewById(R.id.nameMenu);
            itemView.setOnClickListener(this);
        }

        public void setmOnClickRecycler(onClickRecyclerView mOnClickRecycler) {
            this.mOnClickRecycler = mOnClickRecycler;
        }

        @Override
        public void onClick(View view) {
            mOnClickRecycler.onClickRecyCler(view, getAdapterPosition());
        }
    }

    public interface SetOnStateClickActivite {
        void OnClickActivate();
    }
}
