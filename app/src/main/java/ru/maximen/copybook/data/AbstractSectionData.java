package ru.maximen.copybook.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

public abstract class AbstractSectionData<S> implements SectionData<S> {

    private String title;
    private String subTitle;
    private Collection<S> items;
    private Comparator<S> comparator;

    public AbstractSectionData(String title, String subTitle) {
        this.items = new ArrayList<>();
        this.subTitle = subTitle;
        this.title = title;
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public String subTitle() {
        return this.subTitle;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Collection<S> getItems() {
        return this.items;
    }

    @Override
    public Data addItem(S item) {
        this.items.add(item);
        return this;
    }

    @Override
    public Data setSortStrategy(Comparator<S> comparator) {
        Collection<S> newCollection = new TreeSet<>(comparator);
        newCollection.addAll(getItems());
        this.items = newCollection;
        this.comparator = comparator;
        return this;
    }

    @Override
    public String toString() {
        return "AbstractSectionData{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", items=" + items +
                ", comparator=" + comparator +
                '}';
    }
}
