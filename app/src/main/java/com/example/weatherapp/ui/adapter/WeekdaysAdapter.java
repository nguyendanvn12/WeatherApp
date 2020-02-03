package com.example.weatherapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

import java.util.ArrayList;

public class WeekdaysAdapter extends RecyclerView.Adapter<WeekdaysAdapter.DayViewHolder> {
    private ArrayList<String> days;
 private Onclick onclick;
    public WeekdaysAdapter(ArrayList<String> days, Onclick onclick) {
        this.days = days;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day,parent,false);
        return new DayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.textView.setText(days.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.setOnclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public DayViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

}