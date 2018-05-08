package com.example.vaibhav.srmu_bus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class trackbus_adapter extends RecyclerView.Adapter<trackbus_adapter.TrackViewHolder> {

    private Context context;
    private List<trackbus_model>list;

    public trackbus_adapter(Context context, List<trackbus_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public trackbus_adapter.TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.trackbus,parent,false);
        return new TrackViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull trackbus_adapter.TrackViewHolder holder, int position) {

        final trackbus_model items= list.get(position);

        holder.number.setText(String.valueOf(items.getNumber()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class TrackViewHolder extends RecyclerView.ViewHolder {


        public TextView number;

        public TrackViewHolder(View itemView) {
            super(itemView);

            number=itemView.findViewById(R.id.number);

        }
    }
}
