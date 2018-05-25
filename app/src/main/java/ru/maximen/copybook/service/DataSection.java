package ru.maximen.copybook.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DataSection {

    private long sectionId;
    private long dataId;
    private DataSectionType type;
}
