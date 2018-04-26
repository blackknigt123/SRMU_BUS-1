package com.example.vaibhav.srmu_bus;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class recyclerview_adapter extends RecyclerView.Adapter<recyclerview_adapter.AdminViewHolder> {

    private Context context;
    private List<recyclerview_model> list;

    public recyclerview_adapter(Context context, List<recyclerview_model> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public recyclerview_adapter.AdminViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View v= inflater.inflate(R.layout.recycler_view,parent,false);
        return new AdminViewHolder(v);
    }

    @Override
    public void onBindViewHolder(recyclerview_adapter.AdminViewHolder holder, int position) {

        final recyclerview_model items=list.get(position);

        holder.bus_no.setText(items.getBus_no());
        holder.startpoint_details.setText(items.getStartpoint_details());
        holder.imageView.setImageDrawable(context.getResources().getDrawable(items.getImageView()));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You clicked", Toast.LENGTH_SHORT).show();


                Intent intentExtra = new Intent(context,way_point_list.class);
                intentExtra.putExtra("busNo",items.getBus_no());
                context.startActivity(intentExtra);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public class AdminViewHolder extends RecyclerView.ViewHolder {

        public TextView bus_no,startpoint_details;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        public AdminViewHolder(View itemView) {
            super(itemView);

            bus_no=itemView.findViewById(R.id.bus_no);
            startpoint_details=itemView.findViewById(R.id.startpoint_details);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
        }
    }
}
