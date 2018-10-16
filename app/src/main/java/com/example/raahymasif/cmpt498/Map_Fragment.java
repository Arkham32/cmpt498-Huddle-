package com.example.raahymasif.cmpt498;

import com.google.android.gms.maps.SupportMapFragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Map_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_maps, container, false);
    }
}
