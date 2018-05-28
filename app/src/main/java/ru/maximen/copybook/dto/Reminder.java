package ru.maximen.copybook.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Reminder {

    private int reminderId;
    private Date remindDate;
    private String remindStreet;
}
