package ru.maximen.copybook.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.maximen.copybook.R;

public class NoteUtil {

    public static List<Integer> colorIds = new ArrayList<>();

    static {
        colorIds.addAll(Arrays.asList(R.color.colorW,
                R.color.colorR, R.color.colorO, R.color.colorY,
                R.color.colorG, R.color.colorP, R.color.colorB,
                R.color.colorBG, R.color.colorDB, R.color.colorPB
        ));
    }

    public static Integer getColorByNumber(int value) {
        return colorIds.get(value % colorIds.size());
    }
}
