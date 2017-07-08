package com.example.sarthak.tourdeindia.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.managers.StorageManager;
import com.example.sarthak.tourdeindia.model.CityMap;
import com.example.sarthak.tourdeindia.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * View holder class for Famous Places RecyclerAdapter. This class represents single row of RecyclerView
 *
 * @author Sarthak Grover
 */

class FamousViewHolder extends RecyclerView.ViewHolder {

    public ImageView famousPlaceImage;
    private TextView famousPlaceLabel, famousPlaceDesc;
    public FloatingActionButton fabLocation;

    public String[] famousPlaces;

    ArrayList<Uri> cityImage;
    String cityName;

    FamousViewHolder(View itemView) {

        super(itemView);

        famousPlaceImage = (ImageView) itemView.findViewById(R.id.image_famousImage);
        famousPlaceLabel = (TextView) itemView.findViewById(R.id.text_famousTitle);
        famousPlaceDesc = (TextView) itemView.findViewById(R.id.text_famousDescription);
        fabLocation = (FloatingActionButton) itemView.findViewById(R.id.fab_location);
    }
    
    void bindData(Context context, int cityIndex, int position) {

        CityMap cityMap = new CityMap();

        // map city index to city name
        cityName = cityMap.getCityName(cityIndex);

        // get arraylist of city image Uris from city index
        cityImage = getCityImages(cityIndex);

        // set city image
        Picasso.with(context)
                .load(cityImage.get(position))
                .into(famousPlaceImage);

        // get famous places title arraylist
        famousPlaces = context.getResources().getStringArray(R.array.famousPlaces);

        // add city data to view components
        setUpData(position);
    }

    /**
     * Returns an arraylist of city image Uris based on city index.
     *
     * @return an arraylist of city image Uri
     */
    private ArrayList<Uri> getCityImages(int cityIndex) {

        CityMap cityMap = new CityMap();
        String city_name = cityMap.getCityName(cityIndex);

        StorageManager storageManager = new StorageManager();

        return storageManager.getFromSdcard(Constants.INTERNAL_STORAGE_FAMOUS_FOLDER, city_name);
    }

    /**
     * Displays city data in view components
     *
     * @param position is the index of the cardView in recycler view
     */
    private void setUpData(int position) {

        if (position == 0){
            switch (cityName) {
                case Constants.CITY_AHMEDABAD:
                    famousPlaceLabel.setText(famousPlaces[0]);
                    famousPlaceDesc.setText(R.string.ahmedabad_fam_1);
                    break;
                case Constants.CITY_BANGALORE:
                    famousPlaceLabel.setText(famousPlaces[1]);
                    famousPlaceDesc.setText(R.string.bangalore_fam_1);
                    break;
                case Constants.CITY_CHENNAI:
                    famousPlaceLabel.setText(famousPlaces[2]);
                    famousPlaceDesc.setText(R.string.chennai_fam_1);
                    break;
                case Constants.CITY_DELHI:
                    famousPlaceLabel.setText(famousPlaces[3]);
                    famousPlaceDesc.setText(R.string.delhi_fam_1);
                    break;
                case Constants.CITY_HYDERABAD:
                    famousPlaceLabel.setText(famousPlaces[4]);
                    famousPlaceDesc.setText(R.string.hyderabad_fam_1);
                    break;
                case Constants.CITY_KOLKATA:
                    famousPlaceLabel.setText(famousPlaces[5]);
                    famousPlaceDesc.setText(R.string.kolkata_fam_1);
                    break;
                case Constants.CITY_LUCKNOW:
                    famousPlaceLabel.setText(famousPlaces[6]);
                    famousPlaceDesc.setText(R.string.lucknow_fam_1);
                    break;
                case Constants.CITY_MUMBAI:
                    famousPlaceLabel.setText(famousPlaces[7]);
                    famousPlaceDesc.setText(R.string.mumbai_fam__1);
                    break;
            }

        }

        else if (position == 1){
            switch (cityName) {
                case Constants.CITY_AHMEDABAD:
                    famousPlaceLabel.setText(famousPlaces[8]);
                    famousPlaceDesc.setText(R.string.ahmedabad_fam_2);
                    break;
                case Constants.CITY_BANGALORE:
                    famousPlaceLabel.setText(famousPlaces[9]);
                    famousPlaceDesc.setText(R.string.bangalore_fam_2);
                    break;
                case Constants.CITY_CHENNAI:
                    famousPlaceLabel.setText(famousPlaces[10]);
                    famousPlaceDesc.setText(R.string.chennai_fam_2);
                    break;
                case Constants.CITY_DELHI:
                    famousPlaceLabel.setText(famousPlaces[11]);
                    famousPlaceDesc.setText(R.string.delhi_fam_2);
                    break;
                case Constants.CITY_HYDERABAD:
                    famousPlaceLabel.setText(famousPlaces[12]);
                    famousPlaceDesc.setText(R.string.hyderabad_fam_2);
                    break;
                case Constants.CITY_KOLKATA:
                    famousPlaceLabel.setText(famousPlaces[13]);
                    famousPlaceDesc.setText(R.string.kolkata_fam_2);
                    break;
                case Constants.CITY_LUCKNOW:
                    famousPlaceLabel.setText(famousPlaces[14]);
                    famousPlaceDesc.setText(R.string.lucknow_fam_2);
                    break;
                case Constants.CITY_MUMBAI:
                    famousPlaceLabel.setText(famousPlaces[15]);
                    famousPlaceDesc.setText(R.string.mumbai_fam__2);
                    break;
            }
        }

        else if (position == 2){
            switch (cityName) {
                case Constants.CITY_AHMEDABAD:
                    famousPlaceLabel.setText(famousPlaces[16]);
                    famousPlaceDesc.setText(R.string.ahmedabad_fam_3);
                    break;
                case Constants.CITY_BANGALORE:
                    famousPlaceLabel.setText(famousPlaces[17]);
                    famousPlaceDesc.setText(R.string.bangalore_fam_3);
                    break;
                case Constants.CITY_CHENNAI:
                    famousPlaceLabel.setText(famousPlaces[18]);
                    famousPlaceDesc.setText(R.string.chennai_fam_3);
                    break;
                case Constants.CITY_DELHI:
                    famousPlaceLabel.setText(famousPlaces[19]);
                    famousPlaceDesc.setText(R.string.delhi_fam_3);
                    break;
                case Constants.CITY_HYDERABAD:
                    famousPlaceLabel.setText(famousPlaces[20]);
                    famousPlaceDesc.setText(R.string.hyderabad_fam_3);
                    break;
                case Constants.CITY_KOLKATA:
                    famousPlaceLabel.setText(famousPlaces[21]);
                    famousPlaceDesc.setText(R.string.kolkata_fam_3);
                    break;
                case Constants.CITY_LUCKNOW:
                    famousPlaceLabel.setText(famousPlaces[22]);
                    famousPlaceDesc.setText(R.string.lucknow_fam_3);
                    break;
                case Constants.CITY_MUMBAI:
                    famousPlaceLabel.setText(famousPlaces[23]);
                    famousPlaceDesc.setText(R.string.mumbai_fam__3);
                    break;
            }
        }
    }
}
