package com.sungcor.baobiao.utils;


import lombok.Data;

@Data
public class ThreadRecord {
    private long startTime;
    private long endTime;

    public ThreadRecord(long st, long et) {
        this.startTime = st;
        this.endTime = et;
    }
}
