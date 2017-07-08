package com.example.sarthak.tourdeindia.fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.activities.CityGalleryActivity;
import com.example.sarthak.tourdeindia.managers.StorageManager;
import com.example.sarthak.tourdeindia.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CityDescriptionFragment extends Fragment implements View.OnClickListener {

    int cityIndex;

    private ImageView cityImage;
    private TextView cityDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_city_description, container,
                false);

        // access city index from CityDetailsActivity via SectionPagerAdapter.
        cityIndex = this.getArguments().getInt(Constants.BUNDLE_KEY_CITY_INDEX);

        cityImage = (ImageView) rootView.findViewById(R.id.image_cityPhoto);
        cityDescription = (TextView) rootView.findViewById(R.id.text_cityDescription);

        // retrieve city description and display image based on city index.
        getCityDetails(cityIndex);

        // handle onClick for image button
        cityImage.setOnClickListener(this);

        return rootView;
    }

    //----------------------------------------------------------------------
    // image button onClick listener
    //----------------------------------------------------------------------
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.image_cityPhoto :
                Intent galleryIntent = new Intent(getActivity(), CityGalleryActivity.class);
                galleryIntent.putExtra(Constants.KEY_CITY_INDEX, cityIndex);
                startActivity(galleryIntent);
                break;
        }
    }

    /**
     * PURPOSE: Retrieves the city description and display image based on city index.
     *
     * City description is retrieved from the resources folder under strings.xml
     *
     * City display images is fetched from SD card using StorageManager class.
     * StorageManager returns an array list of image Uris stored in 'Icons' folder in SD card.
     * Since the images are stored by city name they are stored in arraylist in alphabetical order.
     * Hence, traversing the array list gives the images in correct order.
     *
     * @param cityIndex is the index of the city whose data is to be fetched
     */
    private void getCityDetails(int cityIndex){

        StorageManager storageManager = new StorageManager();

        ArrayList<Uri> covers = storageManager.getFromSdcard(Constants.INTERNAL_STORAGE_ICONS_FOLDER);

        Picasso.with(getActivity())
                .load(covers.get(cityIndex))
                .into(cityImage);

        switch (cityIndex) {
            case 0:
                cityDescription.setText(R.string.ahmedabad_desc);
                break;
            case 1:
                cityDescription.setText(R.string.bangalore_desc);
                break;
            case 2:
                cityDescription.setText(R.string.chennai_desc);
                break;
            case 3:
                cityDescription.setText(R.string.delhi_desc);
                break;
            case 4:
                cityDescription.setText(R.string.hyderabad_desc);
                break;
            case 5:
                cityDescription.setText(R.string.kolkata_desc);
                break;
            case 6:
                cityDescription.setText(R.string.lucknow_desc);
                break;
            case 7:
                cityDescription.setText(R.string.mumbai_desc);
                break;
            default:
                cityDescription.setText("");
        }
    }
}
