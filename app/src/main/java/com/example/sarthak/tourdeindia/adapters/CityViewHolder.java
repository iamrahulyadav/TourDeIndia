package com.example.sarthak.tourdeindia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.model.Item;
import com.squareup.picasso.Picasso;

/**
 * View holder class for Home Screen RecyclerAdapter.
 *
 * @author Sarthak Grover
 */

class CityViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;

    View mItemView;

    private TextView cityTitle;
    private ImageView cityThumbnail;

    public CityViewHolder(View itemView, Context context) {

        super(itemView);

        this.mItemView = itemView;
        this.mContext = context;
        this.cityTitle = (TextView) itemView.findViewById(R.id.text_cityTitle);
        this.cityThumbnail = (ImageView) itemView.findViewById(R.id.image_cityPicture);
    }

    void bindData(Item city) {

        // set city title
        cityTitle.setText(city.getName());

        // load city image
        Picasso.with(mContext)
                .load(city.getCityImageUri())
                .into(cityThumbnail);
    }
}
