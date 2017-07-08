package com.example.sarthak.tourdeindia.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class Item {
    private String city_name;
    private Bitmap bitmap;
    private String city_desc;
    private Uri cityImageUri;

    public Item() {
    }

    public Item(String name, Bitmap bitmap) {
        this.city_name = name;
        this.bitmap = bitmap;
    }

    public Item(String name, String city_desc) {
        this.city_name = name;
        this.city_desc = city_desc;
    }

    public Item(String name, Uri uri) {
        this.city_name = name;
        this.cityImageUri = uri;
    }

    public String getName() {
        return city_name;
    }

    public void setName(String name) {
        this.city_name = name;
    }

    public String getCity_desc() {
        return city_desc;
    }

    public void setCity_desc(String city_desc) {
        this.city_desc = city_desc;
    }

    public Uri getCityImageUri() {
        return cityImageUri;
    }
}
