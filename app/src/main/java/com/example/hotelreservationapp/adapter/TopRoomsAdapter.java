package com.example.hotelreservationapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelreservationapp.DetailsActivity;
import com.example.hotelreservationapp.GlobalData;
import com.example.hotelreservationapp.R;
import com.example.hotelreservationapp.model.Habitacion;
import com.example.hotelreservationapp.model.TopRoomsData;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TopRoomsAdapter extends RecyclerView.Adapter<TopRoomsAdapter.TopRoomsViewHolder> {

    Context context;
    List<Habitacion> habitacionList;
    GlobalData data;

    public TopRoomsAdapter(Context context, List<Habitacion> habitacionList) {
        this.context = context;
        this.habitacionList = habitacionList;
    }

    @NonNull
    @Override
    public TopRoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_room_row_item,parent, false);
        return new TopRoomsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TopRoomsAdapter.TopRoomsViewHolder holder, int position) {

        holder.bedroomNumber.setText(Integer.toString(habitacionList.get(position).getBedroomNumber()));
        holder.bedroomName.setText(habitacionList.get(position).getBedroomName());
        holder.price.setText(Double.toString(habitacionList.get(position).getPrice()));
        Picasso.get().load(habitacionList.get(position).getImageUrl()).into(holder.placeImage);

        data = (GlobalData) context.getApplicationContext();

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailsActivity.class);
                if(holder.getAdapterPosition() != -1)
                    data.setIdSeleccionado(holder.getAdapterPosition());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return habitacionList.size();
    }


    public static final class TopRoomsViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage;
        TextView bedroomName, bedroomNumber, price;

        public TopRoomsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            bedroomName = itemView.findViewById(R.id.bedroom_name);
            bedroomNumber = itemView.findViewById(R.id.bedroom_number);
            price = itemView.findViewById(R.id.price);
        }
    }
}
