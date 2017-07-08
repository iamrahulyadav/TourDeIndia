package com.example.sarthak.tourdeindia.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.activities.MapsActivity;
import com.example.sarthak.tourdeindia.utils.Constants;

/**
 * Famous Places RecyclerView Adapter
 *
 * @author Sarthak Grover
 */

public class FamousRecyclerAdapter extends RecyclerView.Adapter<FamousViewHolder> {

    private Context mContext;

    private LayoutInflater mInflater;

    public int cityIndex;

    public FamousRecyclerAdapter(Context context, int city) {

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.mContext = context;
        this.cityIndex = city;
    }

    @Override
    public FamousViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.cardview_city_famous, parent, false);

        return new FamousViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FamousViewHolder holder, final int position) {

        // bind data to view holder
        holder.bindData(mContext, cityIndex, position);

        // handle fab onClick listener
        holder.fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // launch MapsActivity
                Intent locationIntent = new Intent(mContext, MapsActivity.class);

                // pass city index and cardview position to retrieve famous place
                locationIntent.putExtra(Constants.KEY_CITY_INDEX, cityIndex);
                locationIntent.putExtra(Constants.KEY_CARD_POSITION, holder.getAdapterPosition());

                // start activity
                mContext.startActivity(locationIntent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return 3;
    }
}
