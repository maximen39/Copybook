package ru.maximen.copybook.data;

import java.util.Comparator;
import java.util.List;

public interface SectionData extends Data {

    List<Data> getItems();

    SectionData addItem(Data item);

    SectionData addAllItems(List<Data> items);

    SectionData setSortStrategy(Comparator<Data> comparator);
}
