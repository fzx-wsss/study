package com.wsss.frame.disruptor.base;

import lombok.Data;

@Data
public class MyData {
    private int i;
    private byte[] data = new byte[1024];

    public MyData(int i) {
        this.i = i;
    }
}
