package com.example.sarthak.tourdeindia.utils;

import android.view.View;

/**
 * Interface to handle click events on each item of RecyclerView
 */

public interface RecyclerViewItemClickListener {

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
