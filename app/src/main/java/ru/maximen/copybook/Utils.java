package ru.maximen.copybook;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import ru.maximen.copybook.data.Data;
import ru.maximen.copybook.data.Section;
import ru.maximen.copybook.service.CopybookService;
import ru.maximen.copybook.service.DataSection;
import ru.maximen.copybook.service.DataSectionType;

public class Utils {

    public static boolean isParent(Data data, List<DataSection> dataSections, DataSectionType type) {
        for (DataSection dataSection : dataSections) {
            if (dataSection.getType() == type && dataSection.getDataId() == data.id()) {
                return false;
            }
        }
        return true;
    }

    public static Data getDataById(List<Data> dataList, long id) {
        for (Data data : dataList) {
            if (data.id() == id) {
                return data;
            }
        }
        return null;
    }

    public static List<Data> getMainSectionList(CopybookService service) {
        List<Data> sectionDataList = new ArrayList<>();
        List<DataSection> dataSections = service.getSectionData();
        List<Data> dataList = service.getDataList();
        List<Data> sections = service.getSections();
        for (DataSection dataSection : dataSections) {
            long sectionId = dataSection.getSectionId();
            long dataId = dataSection.getDataId();
            switch (dataSection.getType()) {
                case DATA: {
                    Section section = (Section) getDataById(sections, sectionId);
                    Data data = getDataById(dataList, dataId);
                    if (section != null && data != null) {
                        section.addItem(data);
                    }
                    break;
                }
                case SECTION: {
                    Section section = (Section) getDataById(sections, sectionId);
                    Data data = getDataById(sections, dataId);
                    if (section != null && data != null) {
                        section.addItem(data);
                    }
                    break;
                }
            }
        }
        for (Data section : sections) {
            if (isParent(section, dataSections, DataSectionType.SECTION)) {
                sectionDataList.add(section);
            }
        }
        for (Data data : dataList) {
            if (isParent(data, dataSections, DataSectionType.DATA)) {
                sectionDataList.add(data);
            }
        }
        return sectionDataList;
    }

    public static String formatDateTime(Context context, String timeToFormat) {

        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Date date;
        if (timeToFormat != null) {
            try {
                date = iso8601Format.parse(timeToFormat);
            } catch (ParseException e) {
                date = null;
            }

            if (date != null) {
                long when = date.getTime();
                int flags = 0;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

                finalDateTime = android.text.format.DateUtils.formatDateTime(context,
                        when + TimeZone.getDefault().getOffset(when), flags);
            }
        }
        return finalDateTime;
    }

    public static Date formatDateTime(String timeToFormat) {
        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Date date = null;
        if (timeToFormat != null) {
            try {
                date = iso8601Format.parse(timeToFormat);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return null;

    }

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
