package ru.maximen.copybook.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.maximen.copybook.Utils;
import ru.maximen.copybook.data.Data;
import ru.maximen.copybook.data.Section;
import ru.maximen.copybook.data.contents.SimpleData;

public class DatabaseCopybookService extends SQLiteOpenHelper implements CopybookService {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "copybook.db";

    public DatabaseCopybookService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS sections (" +
                "id_section INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
                "title TEXT (32) NOT NULL, " +
                "subtitle TEXT (32) NOT NULL, " +
                "priority INTEGER NOT NULL DEFAULT 0, " +
                "date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP);");
        db.execSQL("CREATE TABLE datas (" +
                "id_data INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
                "title TEXT (32) NOT NULL, " +
                "subtitle TEXT (32) NOT NULL, " +
                "content  TEXT, " +
                "priority INTEGER NOT NULL DEFAULT 0, " +
                "date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP);");
        db.execSQL("CREATE TABLE section_data (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
                "id_section INTEGER NOT NULL REFERENCES sections (id_section), " +
                "id_data INTEGER NOT NULL REFERENCES datas (id_data), " +
                "type TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS section_data");
        db.execSQL("DROP TABLE IF EXISTS sections");
        db.execSQL("DROP TABLE IF EXISTS datas");

        onCreate(db);
    }

    @Override
    public List<Data> getDataList() {
        List<Data> dataList = new ArrayList<>();
        Cursor cursor = getSQLiteDatabase().query("datas",
                new String[]{"id_data", "title", "subtitle", "priority", "content", "date"},
                null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int id_data = cursor.getInt(cursor.getColumnIndex("id_data"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String subtitle = cursor.getString(cursor.getColumnIndex("subtitle"));
                    int priority = cursor.getInt(cursor.getColumnIndex("priority"));
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    Date date = Utils.formatDateTime(cursor.getString(cursor.getColumnIndex("date")));
                    SimpleData simpleData = new SimpleData();
                    simpleData.id(id_data)
                            .title(title)
                            .subTitle(subtitle)
                            .priority(priority)
                            .date(date);

                    simpleData.setContent(content);

                    dataList.add(simpleData);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        close();
        return dataList;
    }

    @Override
    public List<DataSection> getSectionData() {
        List<DataSection> dataSections = new ArrayList<>();
        Cursor cursor = getSQLiteDatabase().query("section_data",
                new String[]{"id_section", "id_data", "type"},
                null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int id_section = cursor.getInt(cursor.getColumnIndex("id_section"));
                    int id_data = cursor.getInt(cursor.getColumnIndex("id_data"));
                    String type = cursor.getString(cursor.getColumnIndex("type"));
                    dataSections.add(new DataSection(id_section, id_data, DataSectionType.valueOf(type.toUpperCase())));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        close();
        return dataSections;
    }

    @Override
    public List<Data> getSections() {
        List<Data> sections = new ArrayList<>();
        Cursor cursor = getSQLiteDatabase().query("sections",
                new String[]{"id_section", "title", "subtitle", "priority", "date"},
                null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int id_data = cursor.getInt(cursor.getColumnIndex("id_section"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String subtitle = cursor.getString(cursor.getColumnIndex("subtitle"));
                    int priority = cursor.getInt(cursor.getColumnIndex("priority"));
                    Date date = Utils.formatDateTime(cursor.getString(cursor.getColumnIndex("date")));
                    Section section = new Section();
                    section.id(id_data).title(title).subTitle(subtitle).priority(priority).date(date);
                    sections.add(section);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        close();
        return sections;
    }

    @Override
    public void updateData(long id, SimpleData simpleData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", simpleData.title());
        contentValues.put("subtitle", simpleData.subTitle());
        contentValues.put("priority", simpleData.priority());
        contentValues.put("content", simpleData.getContent());
        contentValues.put("date", Utils.getDateTime());

        getSQLiteDatabase().update("datas", contentValues,
                "id_data = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public void updateSection(long id, Section section) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", section.title());
        contentValues.put("subtitle", section.subTitle());
        contentValues.put("priority", section.priority());
        contentValues.put("date", Utils.getDateTime());

        getSQLiteDatabase().update("sections", contentValues,
                "id_section = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public void updateChildrenSection(long id_parent, long id, Section section) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", section.title());
        contentValues.put("subtitle", section.subTitle());
        contentValues.put("priority", section.priority());
        contentValues.put("date", Utils.getDateTime());

        getSQLiteDatabase().update("sections", contentValues,
                "id_section = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public long addData(long section_id, SimpleData simpleData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", simpleData.title());
        contentValues.put("subtitle", simpleData.subTitle());
        contentValues.put("priority", simpleData.priority());
        contentValues.put("content", simpleData.getContent());
        contentValues.put("date", Utils.getDateTime());
        long data_id = getSQLiteDatabase().insert("datas", null, contentValues);
        simpleData.id(data_id);

        addSectionData(section_id, data_id, DataSectionType.DATA);
        return data_id;
    }

    @Override
    public long addSection(Section section) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", section.title());
        contentValues.put("subtitle", section.subTitle());
        contentValues.put("priority", section.priority());
        contentValues.put("date", Utils.getDateTime());
        long section_id = getSQLiteDatabase().insert("sections", null, contentValues);
        section.id(section_id);
        return section_id;
    }

    @Override
    public long addChildrenSection(long parent_id, Section section) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", section.title());
        contentValues.put("subtitle", section.subTitle());
        contentValues.put("priority", section.priority());
        contentValues.put("date", Utils.getDateTime());
        long section_id = getSQLiteDatabase().insert("sections", null, contentValues);
        section.id(section_id);

        addSectionData(parent_id, section_id, DataSectionType.SECTION);
        return section_id;
    }

    @Override
    public void addSectionData(long section_id, long data_id, DataSectionType type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_section", section_id);
        contentValues.put("id_data", data_id);
        contentValues.put("type", type.toString());

        getSQLiteDatabase().insert("section_data", null, contentValues);
    }

    private SQLiteDatabase getSQLiteDatabase() {
        SQLiteDatabase db;
        try {
            db = this.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = this.getReadableDatabase();
        }
        return db;
    }
}
