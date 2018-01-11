package com.alexsprod.jsonparserproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexsprod.jsonparserproject.R;

public class AboutFragment extends Fragment {

    TextView tv_about;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_about, container, false);
        setHasOptionsMenu(true);
        tv_about = view.findViewById(R.id.tv_about);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_about.setText("About is here");
    }
}
