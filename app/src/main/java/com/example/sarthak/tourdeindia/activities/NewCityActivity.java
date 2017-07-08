package com.example.sarthak.tourdeindia.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarthak.tourdeindia.managers.DatabaseHandler;
import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;

public class NewCityActivity extends AppCompatActivity {

    int cityIndex;

    private String cityName;
    private String cityDescription;

    private ImageView mCityImage;
    private TextView mCityDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_city);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolBar);
        setSupportActionBar(mToolbar);

        mCityImage = (ImageView) findViewById(R.id.image_cityImage);
        mCityDetails = (TextView) findViewById(R.id.text_cityDescription);

        // create a database object to access SQLite database
        DatabaseHandler cityDatabase = new DatabaseHandler(this);

        // access city index from HomeScreenActivity
        cityIndex = getIntent().getIntExtra(Constants.KEY_CITY_INDEX, 0);

        // get city details from database with cityIndex as parameter
        cityName = cityDatabase.getName(cityIndex);
        cityDescription = cityDatabase.getCityDesc(cityIndex);

        // set up toolbar
        setUpToolbar();

        // set up activity data
        setUpCity();

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

            // set UP/Back navigation icon.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // set toolbar title as city name.
            getSupportActionBar().setTitle(cityName);
        }
    }

    /**
     * Set up activity with city data
     */
    private void setUpCity() {

        // load city image from internal storage.
        File imageFile = new File(Constants.INTERNAL_STORAGE_DATA_FOLDER + cityName + ".png");
        Uri imageUri = Uri.fromFile(imageFile);

        // set city image
        if (mCityImage != null)
            Picasso.with(NewCityActivity.this)
                    .load(imageUri)
                    .into(mCityImage);

        // set city details
        if (mCityDetails != null)
            mCityDetails.setText(cityDescription);

    }
}
