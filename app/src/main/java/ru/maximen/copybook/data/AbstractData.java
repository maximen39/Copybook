package ru.maximen.copybook.data;

public abstract class AbstractData implements Data {

    private String title;
    private String subTitle;

    public AbstractData(String title, String subTitle) {
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
    public String toString() {
        return "AbstractData{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                '}';
    }
}
