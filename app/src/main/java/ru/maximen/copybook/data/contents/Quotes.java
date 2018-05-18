package ru.maximen.copybook.data.contents;

import ru.maximen.copybook.MainActivity;
import ru.maximen.copybook.data.AbstractData;
import ru.maximen.copybook.data.Data;

public class Quotes extends AbstractData {
    public Quotes(String title, String subTitle) {
        super(title, subTitle);
    }

    @Override
    public Data onClickAction(MainActivity activity) {
        return null;
    }
}