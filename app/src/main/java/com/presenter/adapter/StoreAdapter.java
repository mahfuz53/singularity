package com.presenter.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.Singularity.store.R;
import com.skydoves.elasticviews.ElasticAnimation;
import com.skydoves.elasticviews.ElasticFinishListener;

import java.util.ArrayList;
import java.util.HashMap;


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {

    private Context mContext;
    private RecycleViewClickListener clickListener;
    private ArrayList<HashMap<String,String>> allData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView StoreName;

        public LinearLayout StoreLayout;

        public MyViewHolder(View view) {
            super(view);
            StoreName =  view.findViewById(R.id.StoreName);
            StoreLayout =  view.findViewById(R.id.StoreLayout);
        }
    }


    public StoreAdapter(Context mContext, ArrayList<HashMap<String,String>> list) {
        this.mContext = mContext;
        this.allData  = list;

    }
    public void noifyRecycleView(int pos)
    {
        notifyDataSetChanged();
    }

    public void setClickListener(RecycleViewClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.StoreName.setText(allData.get(position).get("name"));

        holder.StoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new ElasticAnimation(view).setScaleX(0.9f).setScaleY(0.9f).setDuration(400)
                        .setOnFinishListener(new ElasticFinishListener() {
                            @Override
                            public void onFinished() {
                                if (clickListener != null) {
                                    clickListener.onClick( position);
                                }

                            }
                        }).doAction();

            }
        });
    }

    @Override
    public int getItemCount() {
        return allData.size();
    }



}