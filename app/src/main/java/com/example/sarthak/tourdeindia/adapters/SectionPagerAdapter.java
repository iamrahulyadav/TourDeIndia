package com.example.sarthak.tourdeindia.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.fragments.CityDescriptionFragment;
import com.example.sarthak.tourdeindia.fragments.CityFamousFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the tabs.
 */

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    private final Bundle fragmentBundle;

    public SectionPagerAdapter(FragmentManager fm, Context context, Bundle data) {

        super(fm);
        this.mContext = context;
        this.fragmentBundle = data;
    }

    /**
     * getItem instantiates the fragment and returns it to the adapter.
     * Here, it may return CityDescriptionFragment or CityFamousFragment based on position.
     */
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0 :
                CityDescriptionFragment cityDescriptionFragment = new CityDescriptionFragment();
                cityDescriptionFragment.setArguments(this.fragmentBundle);
                return cityDescriptionFragment;

            case 1 :
                CityFamousFragment cityFamousFragment = new CityFamousFragment();
                cityFamousFragment.setArguments(this.fragmentBundle);
                return cityFamousFragment;

            default:
                return null;
        }
    }

    // returns total number of pages
    @Override
    public int getCount() {

        return 2;
    }

    // returns the page title for the tab indicator
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0 :
                return mContext.getString(R.string.about);
            case  1 :
                return mContext.getString(R.string.famous_places);
            default :
                return null;
        }
    }
}

