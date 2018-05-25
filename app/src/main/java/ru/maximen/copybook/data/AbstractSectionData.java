package ru.maximen.copybook.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public abstract class AbstractSectionData implements SectionData {
    private long id;
    private Date date;
    private int priority;
    private String title;
    private String subTitle;
    private List<String> tags;
    private List<Data> items = new ArrayList<>();

    @Override
    public long id() {
        return id;
    }

    @Override
    public List<Data> getItems() {
        return items;
    }

    @Override
    public SectionData addItem(Data item) {
        this.items.add(item);
        return this;
    }

    @Override
    public SectionData addAllItems(List<Data> items) {
        this.items.addAll(items);
        return this;
    }

    @Override
    public SectionData setSortStrategy(Comparator<Data> comparator) {
        Collections.sort(getItems(), comparator);
        return this;
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
    public SectionData id(long id) {
        this.id = id;
        return this;
    }

    @Override
    public SectionData date(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public SectionData title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public SectionData priority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public SectionData tags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public SectionData subTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }
}
