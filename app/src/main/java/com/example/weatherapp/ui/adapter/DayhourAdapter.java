package com.example.weatherapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

import java.util.ArrayList;

public class DayhourAdapter extends RecyclerView.Adapter<DayhourAdapter.HourViewHolder> {
    private ArrayList<String> hours;
    Onclick onclick;

    public DayhourAdapter(ArrayList<String> hours,Onclick onclick) {
        this.hours = hours;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hour, parent, false);
        return new HourViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HourViewHolder holder, int position) {
        holder.textView.setText(hours.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.setOnclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Log.d("getItemCount", hours == null ? "null" : hours.size() + "");
        return hours.size();
    }

    public static class HourViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;

        public HourViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

}