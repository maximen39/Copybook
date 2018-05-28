package ru.maximen.copybook.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto<O> {

    private int status;
    private String message;
    private O response;
}
