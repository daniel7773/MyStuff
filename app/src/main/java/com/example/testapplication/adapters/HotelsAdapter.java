package com.example.testapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;
import com.example.testapplication.models.Hotel;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.functions.Function2;

public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.AbstractViewHolder> {

    public static final String TAG = "HotelsAdapter";

    Context context;
    HotelsAdapter adapter;
    List<ListItem> listItems;

    public HotelsAdapter(Context context) {
        this.context = context;
        listItems = new ArrayList<>();
    }

    public static abstract class AbstractViewHolder extends RecyclerView.ViewHolder {

        protected ListItem listItem;
        protected HotelsAdapter adapter;

        public AbstractViewHolder(View itemView, HotelsAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
        }

        public void onBind(ListItem listItem) {
            this.listItem = listItem;
        }
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemType type = ListItemType.findById(viewType);
        //noinspection ConstantConditions
        return type.funcCreate.invoke(LayoutInflater.from(parent.getContext()).inflate(type.layout, parent, false), this);
    }

    @Override
    public int getItemCount() {
        return  listItems.size();
    }

    @Override
    public void onBindViewHolder(AbstractViewHolder holder, int position) {
        holder.onBind(listItems.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return listItems.get(position).type.ordinal();
    }



    public void setItems(List<Hotel> hotels) {
        for(Hotel hotel: hotels){
            listItems.add(ListItem.createHotelView(hotel));
        }
        notifyDataSetChanged();
    }


    public static class DummyHolder extends AbstractViewHolder {

        TextView hotelName;
        TextView hotelId;
        TextView hotelPrice;

        public DummyHolder(View itemView, HotelsAdapter adapter) {
            super(itemView, adapter);
            hotelName = itemView.findViewById(R.id.hotel_name);
            hotelId = itemView.findViewById(R.id.hotel_id);
            hotelPrice = itemView.findViewById(R.id.hotel_price);
        }

        @Override
        public void onBind(ListItem listItem) {
            super.onBind(listItem);
            hotelName.setText(listItem.hotel.getName());
            hotelId.setText(listItem.hotel.getId());
            hotelPrice.setText(listItem.hotel.getPrice());
        }
    }



    public enum ListItemType {
        HOTEL_HOLDER(R.layout.hotel_li_item, DummyHolder::new);

        public final int id;
        public final int layout;
        public final Function2<View, HotelsAdapter, AbstractViewHolder> funcCreate;

        ListItemType(int layout, Function2<View, HotelsAdapter, AbstractViewHolder> funcCreate) {
            this.layout = layout;
            this.funcCreate = funcCreate;

            id = ListItemType.IdHolder.id++;
        }

        public static ListItemType findById(int id) {
            for (ListItemType m : values()) {
                if (m.id == id) {
                    return m;
                }
            }
            return null;
        }

        private static class IdHolder {
            public static int id;
        }
    }

    public static class ListItem {
        public ListItemType type;
        public Hotel hotel;

        public ListItem() {

        }

        public static ListItem createHotelView(Hotel hotel){
            ListItem listItem = new ListItem();
            listItem.type = ListItemType.HOTEL_HOLDER;
            listItem.hotel = hotel;
            return listItem;
        }

        public ListItem(ListItemType type) {
            this.type = type;
        }

    }
}