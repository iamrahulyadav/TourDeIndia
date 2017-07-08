package com.example.sarthak.tourdeindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.model.Item;
import com.example.sarthak.tourdeindia.utils.RecyclerViewItemClickListener;

import java.util.List;

/**
 * Home Screen RecyclerView Adapter
 *
 * @author Sarthak Grover
 */

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityViewHolder> {

    private Context mContext;

    private LayoutInflater mInflater;

    private List<Item> cityList;

    private RecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public CityRecyclerAdapter(Context context, List<Item> cityList) {

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.mContext = context;
        this.cityList = cityList;
    }

    public void setOnRecyclerViewItemClickListener(RecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.cardview_home_screen, parent, false);

        return new CityViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, int position) {

        final Item city = cityList.get(position);

        // bind data to view holder
        holder.bindData(city);

        //----------------------------------------------------------------------------------------
        // itemView click listeners
        //----------------------------------------------------------------------------------------
        // single click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onRecyclerViewItemClickListener.onClick(view, holder.getAdapterPosition());
            }
        });

        // long click listener
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (holder.getAdapterPosition() > 7) {

                    onRecyclerViewItemClickListener.onLongClick(view, holder.getAdapterPosition());
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {

        return cityList.size();
    }
}
