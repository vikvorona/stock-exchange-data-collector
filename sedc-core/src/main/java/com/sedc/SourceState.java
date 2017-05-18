package com.sedc;

public enum SourceState {

    INITIAL(0),
    SYMBOLS_READ(5),
    RESOURCES_GENERATED(10),
    SAVED_TO_STAGE(20),
    FILTERED(30),
    SAVED_TO_SNAPSHOT(40),
    FAILED(100);

    private Integer code;

    SourceState(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
