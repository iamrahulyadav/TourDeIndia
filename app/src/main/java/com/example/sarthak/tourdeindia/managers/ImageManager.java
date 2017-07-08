package com.example.sarthak.tourdeindia.managers;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.utils.Constants;
import com.example.sarthak.tourdeindia.model.Item;

public class ImageManager {

    private Context mContext;

    public ImageManager(Context context) {

        this.mContext =  context;
    }

    /**
     * PURPOSE: Stores the image captured by the user in SD card
     *
     * Initially, an input dialog accepts city name and description from the user.
     *
     * These details are stored in SQLite database. Then a camera intent is launched
     * which stores the captured image in SD card with tha name same as city name.
     *
     * @param cityDatabase is the SQLite database
     */
    public void getImageFromCamera(final DatabaseHandler cityDatabase) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        // set view group as null
        final ViewGroup nullParent = null;

        View dialogView = inflater.inflate(R.layout.dialog_enter_city_details, nullParent);
        alertDialogBuilder.setView(dialogView);

        final EditText mCityName = (EditText) dialogView.findViewById(R.id.editTextDialogUserInput);
        final EditText mCityDescription = (EditText) dialogView.findViewById(R.id.editTextDialogUserInput2);

        // setup alert dialog
        alertDialogBuilder
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                String name = String.valueOf(mCityName.getText());
                                String desc = String.valueOf(mCityDescription.getText());

                                // add city name and description to SQLite database
                                cityDatabase.addCity(new Item(name, desc));

                                // launch camera intent
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                if (takePicture.resolveActivity(mContext.getPackageManager()) != null) {
                                    ((Activity) mContext).startActivityForResult(takePicture, Constants.CAMERA_REQUEST);
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert dialog
        alertDialog.show();
    }

    /**
     * PURPOSE: Stores the image selected by the user from allery in SD card
     *
     * Initially, an input dialog accepts city name and description from the user.
     *
     * These details are stored in SQLite database. Then a gallery intent is launched
     * which stores the selected image in SD card with tha name same as city name.
     *
     * @param cityDatabase is the SQLite database
     */
    public void getImageFromGallery(final DatabaseHandler cityDatabase) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        // set view group as null
        final ViewGroup nullParent = null;

        View dialogView = inflater.inflate(R.layout.dialog_enter_city_details, nullParent);
        alertDialogBuilder.setView(dialogView);

        final EditText mCityName = (EditText) dialogView.findViewById(R.id.editTextDialogUserInput);
        final EditText mCityDescription = (EditText) dialogView.findViewById(R.id.editTextDialogUserInput2);

        // setup alert dialog
        alertDialogBuilder
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                String name = String.valueOf(mCityName.getText());
                                String desc = String.valueOf(mCityDescription.getText());

                                cityDatabase.addCity(new Item(name, desc));

                                // launch implicit intent to access gallery
                                // 'image/*' specifies images of all types
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);

                                ((Activity) mContext).startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.PICK_IMAGE);

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert dialog
        alertDialog.show();
    }
}
