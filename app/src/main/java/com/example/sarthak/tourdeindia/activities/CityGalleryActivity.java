package com.example.sarthak.tourdeindia.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sarthak.tourdeindia.adapters.ImagePagerAdapter;
import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.utils.Constants;

public class CityGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_gallery);

        // access city index from CityDescriptionFragment
        int cityIndex = getIntent().getIntExtra(Constants.KEY_CITY_INDEX, -1);

        // create fragment pager adapter that will call SwipeFragment to display city images
        // cityIndex is used as a parameter to identify city whose images are to be displayed
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), cityIndex);

        // pass fragment pager adapter to view pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.cityGalleryViewPager);
        if (viewPager != null) {
            viewPager.setAdapter(imagePagerAdapter);
        }
    }
}
