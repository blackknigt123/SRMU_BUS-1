package com.example.admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class recyclerview_adapter extends RecyclerView.Adapter<recyclerview_adapter.AdminViewHolder> {

    private Context context;
    private List<recyclerview_model> list;

    public recyclerview_adapter(Context context, List<recyclerview_model> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public AdminViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.recycler_view,parent,false);
        return new AdminViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdminViewHolder holder, int position) {

        recyclerview_model items=list.get(position);

        holder.bus_no.setText(items.getBus_no());
        holder.startpoint_details.setText(items.getStartpoint_details());
        holder.current_details.setText(items.getCurrent_details());
        holder.nextstop_details.setText(items.getNextstop_details());
        holder.nextstoptime_detail.setText(String.valueOf(items.getNextstop_details()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder {

        public TextView bus_no,startpoint_details,current_details,nextstop_details,nextstoptime_detail;
        public AdminViewHolder(View itemView) {
            super(itemView);

            bus_no=itemView.findViewById(R.id.bus_no);
            startpoint_details=itemView.findViewById(R.id.startpoint_details);
            current_details=itemView.findViewById(R.id.current_details);
            nextstop_details=itemView.findViewById(R.id.nextstop_details);
            nextstoptime_detail=itemView.findViewById(R.id.nextstoptime_detail);
        }
    }
}
