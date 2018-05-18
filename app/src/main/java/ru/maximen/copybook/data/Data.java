package ru.maximen.copybook.data;

import ru.maximen.copybook.MainActivity;

public interface Data {

    String title();

    String subTitle();

    Data onClickAction(MainActivity mainActivity);
}
