package ru.maximen.copybook.data;

import java.util.Date;
import java.util.List;

public interface Data {

    long id();

    Date date();

    int priority();

    String title();

    String subTitle();

    List<String> tags();

    Data id(long id);

    Data date(Date date);

    Data title(String title);

    Data priority(int priority);

    Data tags(List<String> tags);

    Data subTitle(String subTitle);
}
