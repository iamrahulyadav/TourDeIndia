package com.example.sarthak.tourdeindia.model;

import com.example.sarthak.tourdeindia.utils.Constants;

public class CityMap {

    private String city_name = "";

    public String getCityName(int city) {

        switch (city) {
            case 0:
                city_name = Constants.CITY_AHMEDABAD;
                break;
            case 1:
                city_name = Constants.CITY_BANGALORE;
                break;
            case 2:
                city_name = Constants.CITY_CHENNAI;
                break;
            case 3:
                city_name = Constants.CITY_DELHI;
                break;
            case 4:
                city_name = Constants.CITY_HYDERABAD;
                break;
            case 5:
                city_name = Constants.CITY_KOLKATA;
                break;
            case 6:
                city_name = Constants.CITY_LUCKNOW;
                break;
            case 7:
                city_name = Constants.CITY_MUMBAI;
                break;
        }

        return city_name;
    }
}
