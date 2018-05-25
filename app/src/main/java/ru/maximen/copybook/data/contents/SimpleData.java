package ru.maximen.copybook.data.contents;

import ru.maximen.copybook.data.AbstractData;

public class SimpleData extends AbstractData {
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
