package ru.maximen.copybook.data;

import java.util.Collection;
import java.util.Comparator;

public interface SectionData<S> extends Data {

    int getCount();

    Data addItem(S item);

    Collection<S> getItems();

    Data setSortStrategy(Comparator<S> comparator);
}
