package ru.maximen.copybook.service;

public enum DataSectionType {
    DATA("data"),
    SECTION("section"),
    UNKNOWN("unknown");

    private String name;

    DataSectionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
