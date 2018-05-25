package ru.maximen.copybook.fragments;

import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.data.Section;

public interface CopybookFragment {

    Section section();

    CopybookFragment section(Section section);

    MainActivity mainActivity();

    CopybookFragment mainActivity(MainActivity mainActivity);
}
