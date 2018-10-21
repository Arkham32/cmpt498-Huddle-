package com.example.raahymasif.cmpt498;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FindMatchFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_findmatch, container, false);
        View view = inflater.inflate(R.layout.fragment_findmatch, container, false);

        Intent findMatch = new Intent(getActivity(), FindMatchActivity.class);
        startActivity(findMatch);

        return view;
    }
}
