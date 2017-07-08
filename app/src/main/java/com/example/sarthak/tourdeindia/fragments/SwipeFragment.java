package com.example.sarthak.tourdeindia.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.managers.StorageManager;
import com.example.sarthak.tourdeindia.model.CityMap;
import com.example.sarthak.tourdeindia.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SwipeFragment extends Fragment {

    ArrayList<Uri> cityImages;

    public static SwipeFragment newInstance(int position, int cityIndex) {

        SwipeFragment swipeFragment = new SwipeFragment();

        // put city index and image position to bundle
        // city index is used to identify the city to be accessed
        // image position gives the city image at specified position
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_KEY_POSITION, position);
        bundle.putInt(Constants.BUNDLE_KEY_CITY_INDEX, cityIndex);

        // pass city index and image position using bundle
        swipeFragment.setArguments(bundle);

        return swipeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View swipeView = inflater.inflate(R.layout.fragment_swipe, container, false);

        ImageView imageView = (ImageView) swipeView.findViewById(R.id.swipe_imageView);

        // retrieve city index and image position
        int position = getArguments().getInt(Constants.BUNDLE_KEY_POSITION);
        int cityIndex = getArguments().getInt(Constants.BUNDLE_KEY_CITY_INDEX);

        // retrieve arraylist of city images
        cityImages = getCityImages(cityIndex);

        // load city image
        Picasso.with(getActivity())
                .load(cityImages.get(position))
                .into(imageView);

        return swipeView;
    }

    /**
     * PURPOSE: Return an arraylist of city images from SD card based on cityIndex.
     *
     * At first, it maps the cityIndex of the city to the name of the city.
     * This is done because the images are stored in the internal storage
     * in folders named as city names.
     *
     * After fetching the city names, it retrieves the arraylist of city images.
     *
     * @param cityIndex is the index of the city of which the images are to be fetched
     * @return an arraylist of city images from SD card
     */

    private ArrayList<Uri> getCityImages(int cityIndex) {

        ArrayList<Uri> cityImages;

        CityMap cityMap = new CityMap();

        // use a mapping function to map city index to city name.
        String cityName = cityMap.getCityName(cityIndex);

        StorageManager storageManager = new StorageManager();

        // getFromSdcard returns an arraylist of cityImages.
        cityImages = storageManager.getFromSdcard(Constants.INTERNAL_STORAGE_IMAGES_FOLDER, cityName);

        return cityImages;
    }
}
