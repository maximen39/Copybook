package ru.maximen.copybook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.maximen.copybook.R;

public class FragmentTag  extends AbstractUpdatebleFragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, null);
        return view;
    }

    @Override
    public void onUpdateList() {

    }
}