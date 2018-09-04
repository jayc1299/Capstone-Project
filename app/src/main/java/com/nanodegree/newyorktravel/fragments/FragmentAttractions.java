package com.nanodegree.newyorktravel.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanodegree.newyorktravel.R;

public class FragmentAttractions extends Fragment{

    public static final String TAG_NAME = "tag_name";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attractions, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String name = "";
        if (getArguments() != null) {
            name = getArguments().getString(TAG_NAME);
        }

        ((TextView) getView().findViewById(R.id.frag_attractions_hello)).setText(name);
    }
}