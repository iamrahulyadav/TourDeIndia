package com.example.sarthak.tourdeindia.managers;

import android.net.Uri;

import com.example.sarthak.tourdeindia.utils.Constants;

import java.io.File;
import java.util.ArrayList;

public class StorageManager {

    private ArrayList<Uri> cityUri = new ArrayList<>();
    private ArrayList<Uri> cityString = new ArrayList<>();

    private File[] cityFile;

    /**
     * PURPOSE: Returns an arraylist of city images stored in directory 'Category'
     * in SD card.
     *
     * @param category is a directory in SD card which may be 'Icons', 'CityImages' or 'FamousPlaces'
     * @return an arraylist of city image Uris
     */

    public ArrayList<Uri> getFromSdcard(String category)
    {
        File file= new File(Constants.INTERNAL_STORAGE_DATA_FOLDER + category);

        if (file.isDirectory())
        {
            cityFile = file.listFiles();

            for (File City : cityFile) {
                cityUri.add(Uri.fromFile(City));
            }
        }

        return cityUri;
    }

    /**
     * PURPOSE: Returns an arraylist of city images stored in directory 'CityName'
     * inside directory 'Category' in SD card.
     *
     * Used to access city images and famous places for each city which are stored in
     * directories named with city names in directory "CityImages" and "FamousPlaces"
     * in SD card respectively.
     *
     * @param category is a directory in SD card which may be 'Icons', 'CityImages' or 'FamousPlaces'
     * @param cityName is the name of the city being referred
     * @return an arraylist of city image Uris
     */

    public ArrayList<Uri> getFromSdcard(String category, String cityName)
    {
        File file= new File(Constants.INTERNAL_STORAGE_DATA_FOLDER + category + "/" + cityName);

        if (file.isDirectory())
        {
            cityFile = file.listFiles();

            for (File City : cityFile) {
                cityString.add(Uri.fromFile(City));
            }
        }

        return cityString;
    }
}
