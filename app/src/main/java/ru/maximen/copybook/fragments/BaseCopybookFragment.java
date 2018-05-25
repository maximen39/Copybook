package ru.maximen.copybook.fragments;

import android.app.Fragment;

import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.data.Section;

public abstract class BaseCopybookFragment extends Fragment implements CopybookFragment {

    private Section section;
    private MainActivity mainActivity;

    @Override
    public Section section() {
        return section;
    }

    @Override
    public CopybookFragment section(Section section) {
        this.section = section;
        return this;
    }

    @Override
    public MainActivity mainActivity() {
        return mainActivity;
    }

    @Override
    public CopybookFragment mainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        return this;
    }
}
