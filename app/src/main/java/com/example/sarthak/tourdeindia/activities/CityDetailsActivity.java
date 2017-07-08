package com.example.sarthak.tourdeindia.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.adapters.SectionPagerAdapter;
import com.example.sarthak.tourdeindia.model.CityMap;
import com.example.sarthak.tourdeindia.utils.Constants;

public class CityDetailsActivity extends AppCompatActivity {

    int cityIndex;
    String cityName;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        CityMap cityMap = new CityMap();

        // access city index from HomeScreenActivity
        cityIndex = getIntent().getIntExtra(Constants.KEY_CITY_INDEX, -1);

        // use a mapping function to map city index to city name
        cityName = cityMap.getCityName(cityIndex);

        // set up toolbar
        setUpToolbar();

        // set up tabbed layout
        setUpTabs();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // launch animation
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
    }

    /**
     * Set up activity toolbar
     */
    private void setUpToolbar() {

        if (getSupportActionBar() != null) {

            // set toolbar title as city name.
            getSupportActionBar().setTitle(cityName);
            // set UP/Back navigation icon.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Pass city index to SectionPagerAdapter and set up tabbed layout for corresponding city
     */
    private void setUpTabs() {

        // put city index to bundle
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_KEY_CITY_INDEX, cityIndex);

        // create the adapter that will return a fragment for two tabs, namely, City details and Famous places
        // pass cityIndex to the fragments using bundle to identify the city to be displayed
        SectionPagerAdapter pagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(), CityDetailsActivity.this, bundle);

        // pass fragment pager adapter to view pager
        if (mViewPager != null) {
            mViewPager.setAdapter(pagerAdapter);
        }

        // set up tab layout with view pager
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }
}
