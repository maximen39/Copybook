package ru.maximen.copybook.data;

import java.util.Date;
import java.util.List;

public abstract class AbstractData implements Data {
    private long id;
    private Date date;
    private int priority;
    private String title;
    private String subTitle;
    private List<String> tags;

    @Override
    public long id() {
        return id;
    }

    @Override
    public Date date() {
        return date;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String subTitle() {
        return subTitle;
    }

    @Override
    public List<String> tags() {
        return tags;
    }

    @Override
    public Data id(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Data date(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public Data title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public Data priority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public Data tags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public Data subTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }
}
