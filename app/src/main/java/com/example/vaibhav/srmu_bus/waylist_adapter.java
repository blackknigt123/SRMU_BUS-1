package com.example.vaibhav.srmu_bus;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.disklrucache.DiskLruCache;

import java.util.List;

public class waylist_adapter extends RecyclerView.Adapter<waylist_adapter.ViewHolder> {

    private Context context;
    private List<waylist_model> list;

    public waylist_adapter(Context context, List<waylist_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public waylist_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.waylist,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull waylist_adapter.ViewHolder holder, int position) {

        final waylist_model items= list.get(position);

        holder.bus_stop.setText(items.getBus_stop());
        holder.stop_no.setText(String.valueOf(items.getStop_no()));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentExtra = new Intent(context,duration.class);
               // intentExtra.putExtra("busNo",items.getBus_no());
                context.startActivity(intentExtra);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView bus_stop;
        public TextView stop_no;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            bus_stop=itemView.findViewById(R.id.bus_stop);
            stop_no=itemView.findViewById(R.id.stop_no);
            relativeLayout=itemView.findViewById(R.id.relative);
        }
    }
}
