package ru.maximen.copybook.data;

public class Section extends AbstractSectionData {

    public void setItem(int i, Data item){
        getItems().set(i, item);
    }
}
