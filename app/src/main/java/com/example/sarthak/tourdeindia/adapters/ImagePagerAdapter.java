package com.example.sarthak.tourdeindia.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sarthak.tourdeindia.fragments.SwipeFragment;
import com.example.sarthak.tourdeindia.managers.StorageManager;
import com.example.sarthak.tourdeindia.model.CityMap;
import com.example.sarthak.tourdeindia.utils.Constants;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the city images.
 */

public class ImagePagerAdapter extends FragmentPagerAdapter{

    private int cityIndex;
    private int cityImagesCount;

    public ImagePagerAdapter(FragmentManager fm, int cityIndex) {

        super(fm);
        this.cityIndex = cityIndex;
        this.cityImagesCount = getCityImagesCount(cityIndex);
    }

    /**
     * getItem instantiates the fragment and returns it to the adapter.
     * Here, it returns a SwipeFragment.
     */
    @Override
    public Fragment getItem(int position) {

        return SwipeFragment.newInstance(position, cityIndex);
    }

    // returns total number of pages
    @Override
    public int getCount() {
        return cityImagesCount;
    }

    /**
     * PURPOSE: Returns the image count for a particular city based on cityIndex.
     *
     * At first, it maps the cityIndex of the city to the name of the city.
     * This is done because the images are stored in the internal storage
     * in folders named as city names.
     *
     * After fetching the city names, it retrieves the arraylist of city images
     * and size of this arraylist is returned to the user.
     *
     * @param cityIndex is the index of the city of which the images are to be fetched
     * @return an integer value indicating total number of images for the city
     */

    private int getCityImagesCount(int cityIndex) {

        int imageCount;

        CityMap cityMap = new CityMap();

        // use a mapping function to map city index to city name.
        String city_name = cityMap.getCityName(cityIndex);

        StorageManager storageManager = new StorageManager();

        // getFromSdcard returns an arraylist of cityImages.
        // size() method is used to get the number of items in arraylist.
        imageCount = storageManager.getFromSdcard(Constants.INTERNAL_STORAGE_IMAGES_FOLDER, city_name).size();

        return imageCount;
    }
}
