package com.example.sarthak.tourdeindia.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.sarthak.tourdeindia.managers.DatabaseHandler;
import com.example.sarthak.tourdeindia.managers.ImageManager;
import com.example.sarthak.tourdeindia.managers.StorageManager;
import com.example.sarthak.tourdeindia.utils.Constants;
import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.adapters.CityRecyclerAdapter;
import com.example.sarthak.tourdeindia.model.Item;
import com.example.sarthak.tourdeindia.utils.RecyclerViewItemClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewItemClickListener {

    boolean isFABOpen = false;
    String cityName;

    private List<Item> cityList = new ArrayList<>();

    final Context mContext = this;

    DatabaseHandler cityDatabase;

    private FloatingActionButton fabAddCity,fabCamera,fabGallery;
    private AlertDialog alertDialog;

    private RecyclerView recyclerView;
    private CityRecyclerAdapter cityRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        fabAddCity = (FloatingActionButton) findViewById(R.id.fabAddCity);
        fabCamera = (FloatingActionButton) findViewById(R.id.fabCamera);
        fabGallery = (FloatingActionButton) findViewById(R.id.fabGallery);

        // add default cities' title and image as an 'Item' to cityList
        prepareItems();

        recyclerView = (RecyclerView) findViewById(R.id.cityRecyclerView);

        // pass cityList(which contains city title and image) to recycler adapter
        cityRecyclerAdapter = new CityRecyclerAdapter(this, cityList);
        cityRecyclerAdapter.setOnRecyclerViewItemClickListener(this);

        // set grid layout with 2 columns
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cityRecyclerAdapter);

        // call SQLite database which maintains newly added cities
        cityDatabase = new DatabaseHandler(mContext);

        // fetch database for any newly added city and add to recycler view
        addCity(cityList.size());

        //------------------------------------------------------------------
        // onClick listeners for Floating Buttons
        //------------------------------------------------------------------
        fabAddCity.setOnClickListener(this);
        fabCamera.setOnClickListener(this);
        fabGallery.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // launch animation
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
    }

    @Override
    public void onBackPressed() {
        if(!isFABOpen){
            super.onBackPressed();
        }

        // call closeFABMenu() to hide floating action buttons, if visible.
        else{
            closeFABMenu();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == Constants.PICK_IMAGE && null != data) {

                try {

                    Bitmap cityBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    saveCityImage(cityBitmap);
                } catch (IOException e) {

                    e.printStackTrace();
                }

            } else if (requestCode == Constants.CAMERA_REQUEST) {

                Bitmap cityBitmap = (Bitmap) data.getExtras().get("data");
                saveCityImage(cityBitmap);
            }
        }
    }

    //-----------------------------------------------------------------------------
    // recycler adapter itemCLick listeners
    //-----------------------------------------------------------------------------
    @Override
    public void onClick(View view, int position) {
        viewCity(position);
    }

    @Override
    public void onLongClick(View view, int position) {
        deleteCity(position);
    }

    //-----------------------------------------------------------------------------
    // floating action button onCLick listener
    //-----------------------------------------------------------------------------
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fabAddCity :
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
                break;

            case R.id.fabCamera :
                new ImageManager(HomeScreenActivity.this).getImageFromCamera(cityDatabase);
                break;

            case R.id.fabGallery :
                new ImageManager(HomeScreenActivity.this).getImageFromGallery(cityDatabase);
                break;
        }
    }

    //-------------------------------------------------------------------------
    // floating action button animations
    //-------------------------------------------------------------------------
    private void showFABMenu(){
        isFABOpen=true;
        fabCamera.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabGallery.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabCamera.animate().translationY(0);
        fabGallery.animate().translationY(0);
    }

    /**
     * Saves bitmap image of city to internal storage with name same as city name.
     * Adds newly added city to recycler view.
     *
     * @param cityBitmap is the image of the newly added city
     */
    private void saveCityImage(Bitmap cityBitmap) {

        // access newly added city name
        // cityDatabase.getCityCount() gives the index of the newly added city
        cityName = cityDatabase.getName(cityDatabase.getCityCount());

        // saves image of newly added city as city name itself
        File imageFile = new File(Constants.INTERNAL_STORAGE_DATA_FOLDER + cityName + ".png");

        FileOutputStream outStream;

        // compress city image and save to internal storage
        try {

            outStream = new FileOutputStream(imageFile);
            if (cityBitmap != null) {
                // 100 means it preserves entire quality of the image
                cityBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            }
            outStream.flush();
            outStream.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

        // add newly added city to the recycler view
        cityList.add(new Item(cityName, Uri.fromFile(imageFile)));

        cityRecyclerAdapter.notifyItemInserted(cityList.size());
        recyclerView.smoothScrollToPosition(cityRecyclerAdapter.getItemCount() - 1);
    }

    /**
     * Removes city from recycler view and SQLite database.
     *
     * @param position is the index of city in recycler view
     */
    private void deleteCity(final int position) {

        // cityIndex gives the index of the city as in database
        // since there are 8 cities, index is obtained by subtracting 7.
        final int cityIndex = position - 7;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        // setup alert dialog
        alertDialogBuilder
                .setMessage(getString(R.string.alert_delete_city))
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String deletedCityName = cityDatabase.getName(cityIndex);

                                // remove from database
                                cityDatabase.deleteCity(cityIndex);
                                // remove from recycler view
                                cityList.remove(position);
                                cityRecyclerAdapter.notifyItemRemoved(position);
                                cityRecyclerAdapter.notifyItemRangeChanged(position,cityList.size());

                                Toast.makeText(getApplicationContext(), deletedCityName + " deleted.", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        alertDialog = alertDialogBuilder.create();
        // show alert dialog
        alertDialog.show();
    }

    /**
     * PURPOSE: Launches an intent to view city details.
     *
     * For default cities, city index will be from 0 to 7.
     * CityDetailsActivity displays the city details for these cities.
     *
     * However, for newly added cities, there is no city gallery or famous places.
     * Hence, a rather simple layout with imageview and textview is displayed.
     * NewCityActivity is called for this purpose.
     *
     * @param position is the index of city in recycler view
     */
    private void viewCity(int position) {

        // intent for default cities
        if (position < 8) {
            Intent intent = new Intent(HomeScreenActivity.this, CityDetailsActivity.class);
            intent.putExtra(Constants.KEY_CITY_INDEX, position);
            startActivity(intent);
        }

        // intent for added cities, since they do not have any gallery or famous places.
        else {
            Intent intent = new Intent(HomeScreenActivity.this, NewCityActivity.class);
            intent.putExtra(Constants.KEY_CITY_INDEX, position - 7);
            startActivity(intent);
        }
    }

    /**
     * Fetches user added cities from database and adds them to recycler view.
     */
    private void addCity(int cityCount) {

        if (cityDatabase.getCityCount() != 0) {
            List<Item> city = cityDatabase.getAll();

            for (Item cn : city) {
                File f = new File(Constants.INTERNAL_STORAGE_DATA_FOLDER + cn.getName() + ".png");
                Uri uri = Uri.fromFile(f);

                cityList.add(new Item(cn.getName(), uri));
                cityRecyclerAdapter.notifyItemInserted(cityCount);
            }
        }
    }

    /**
     * Adds 'Item' to the arraylist which stores city title and images.
     * This arraylist is passed to recycler adapter to display city in recycler view.
     */
    private void prepareItems() {

        StorageManager storageManager = new StorageManager();

        ArrayList<Uri> covers = storageManager.getFromSdcard(Constants.INTERNAL_STORAGE_ICONS_FOLDER);

        Item a = new Item(getString(R.string.ahmedabad), covers.get(0));
        cityList.add(a);

        a = new Item(getString(R.string.bangalore), covers.get(1));
        cityList.add(a);

        a = new Item(getString(R.string.chennai), covers.get(2));
        cityList.add(a);

        a = new Item(getString(R.string.delhi), covers.get(3));
        cityList.add(a);

        a = new Item(getString(R.string.hyderabad), covers.get(4));
        cityList.add(a);

        a = new Item(getString(R.string.kolkata), covers.get(5));
        cityList.add(a);

        a = new Item(getString(R.string.lucknow), covers.get(6));
        cityList.add(a);

        a = new Item(getString(R.string.mumbai), covers.get(7));
        cityList.add(a);
    }
}
