package com.tac.springweather.model.error;

import lombok.ToString;

@ToString
public class ErrorMessage {

    private String key;
    private String msg;

    public ErrorMessage(String msg) {
        this(null, msg);
    }

    public ErrorMessage(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getKey() {
        return key;
    }
}
