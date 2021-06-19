package com.example.hotelreservationapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelreservationapp.R;
import com.example.hotelreservationapp.model.TopRoomsData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TopRoomsAdapter extends RecyclerView.Adapter<TopRoomsAdapter.TopRoomsViewHolder> {

    Context context;
    List<TopRoomsData> TopRoomsDataList;

    public TopRoomsAdapter(Context context, List<TopRoomsData> recentsDataList) {
        this.context = context;
        this.TopRoomsDataList = recentsDataList;
    }

    @NonNull
    @Override
    public TopRoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_room_row_item,parent, false);
        return new TopRoomsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TopRoomsAdapter.TopRoomsViewHolder holder, int position) {

        holder.bedroomNumber.setText(TopRoomsDataList.get(position).getBedroomNumber());
        holder.bedroomName.setText(TopRoomsDataList.get(position).getBedroomName());
        holder.price.setText(TopRoomsDataList.get(position).getPrice());
        holder.placeImage.setImageResource(TopRoomsDataList.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() {
        return TopRoomsDataList.size();
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
