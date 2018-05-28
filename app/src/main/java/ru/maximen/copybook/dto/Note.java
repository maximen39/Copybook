package ru.maximen.copybook.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Note {

    private int noteId;
    private boolean archive;
    private int color;
    private String content;
    private Date date_create;
    private boolean fix;
    private String title;
    private boolean trash;
    private Reminder reminder;
    private List<Tag> tags;
}
