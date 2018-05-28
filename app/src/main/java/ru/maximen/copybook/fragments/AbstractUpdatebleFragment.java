package ru.maximen.copybook.fragments;

import android.app.Fragment;

public abstract class AbstractUpdatebleFragment extends Fragment {
    private boolean destroyed = false;

    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyed = true;
    }

    public abstract void onUpdateList();
}
