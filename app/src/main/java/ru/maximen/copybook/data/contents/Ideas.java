package ru.maximen.copybook.data.contents;

import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.data.AbstractData;
import ru.maximen.copybook.data.Data;

public class Ideas extends AbstractData {
    public Ideas(String title, String subTitle) {
        super(title, subTitle);
    }

    @Override
    public Data onClickAction(MainActivity activity) {
        return null;
    }
}