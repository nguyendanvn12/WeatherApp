package com.example.weatherapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;

import java.util.ArrayList;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder> {
    private Context context;
    private ArrayList<String> locations;
    private Onclick onclick;

    public LocalAdapter(Context context, ArrayList<String> locations, Onclick onclick) {
        this.context = context;
        this.locations = locations;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.item_location,parent,false);
        return new LocalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder holder, int position) {
        holder.textView.setText(locations.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.setOnclick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class LocalViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public LocalViewHolder( TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }
}
