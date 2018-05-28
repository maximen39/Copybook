package ru.maximen.copybook.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ResponseDto<O> {

    private int status;
    private String message;
    private O response;
}
