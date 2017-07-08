package com.example.sarthak.tourdeindia.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.adapters.FamousRecyclerAdapter;
import com.example.sarthak.tourdeindia.utils.Constants;

public class CityFamousFragment extends Fragment {

    private int cityIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_city_famous, container, false);

        // access city index from CityDetailsActivity via SectionPagerAdapter.
        cityIndex = this.getArguments().getInt(Constants.BUNDLE_KEY_CITY_INDEX);

        // pass city index to recycler adapter
        FamousRecyclerAdapter famousRecyclerAdapter = new FamousRecyclerAdapter(getActivity(), cityIndex);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.famous_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(famousRecyclerAdapter);

        return rootView;
    }
}
