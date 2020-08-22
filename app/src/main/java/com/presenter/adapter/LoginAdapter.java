/*
package com.presenter.adapter;



import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Singularity.store.R;
import com.libizo.CustomEditText;

import java.util.ArrayList;
import java.util.HashMap;


public class LoginAdapter extends RecyclerView.Adapter<LoginAdapter.MyViewHolder> {

    private Context mContext;
    private RecycleViewClickListener clickListener;
    private ArrayList<HashMap<String,String>> allData;
    private int curPos = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView MeasurementTypeID,MeasurementTypeName;
        public CustomEditText insertData;
        public LinearLayout defectLayout;

        public MyViewHolder(View view) {
            super(view);

            MeasurementTypeID =  view.findViewById(R.id.MeasurementTypeID);
            MeasurementTypeName =  view.findViewById(R.id.MeasurementTypeName);
            insertData =  view.findViewById(R.id.insertData);

            defectLayout =  view.findViewById(R.id.defectLayout);

        }
    }


    public LoginAdapter(Context mContext, ArrayList<HashMap<String,String>> list) {
        this.mContext = mContext;
        this.allData  = list;

    }
    public void noifyRecycleView(int pos)
    {
        curPos = pos;
        notifyDataSetChanged();
    }

    public void setClickListener(RecycleViewClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dfu_measurement_listview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.MeasurementTypeID.setText(allData.get(position).get("MeasurementTypeID"));
        holder.MeasurementTypeName.setText(allData.get(position).get("MeasurementTypeName"));

        holder.insertData.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                //print.message("mahfuz..afterTextChanged..."+s.toString()+"...."+holder.insertData.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                //print.message("mahfuz.. beforeTextChanged..."+holder.insertData.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                allData.get(position).put("InsertData", ""+s.toString());
                //print.message("mahfuz..onTextChanged..."+s.toString()+"...."+holder.insertData.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return allData.size();
    }


}*/
