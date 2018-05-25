package ru.maximen.copybook.service;

import java.util.List;

import ru.maximen.copybook.data.Data;
import ru.maximen.copybook.data.Section;
import ru.maximen.copybook.data.contents.SimpleData;

public interface CopybookService {

    List<Data> getDataList();

    List<DataSection> getSectionData();

    List<Data> getSections();

    void updateData(long id, SimpleData data);

    void updateSection(long id, Section section);

    void updateChildrenSection(long id_parent, long id, Section section);

    long addData(long section_id, SimpleData data);

    long addSection(Section section);

    long addChildrenSection(long parent_id, Section section);

    void addSectionData(long section_id, long data_id, DataSectionType type);
}
