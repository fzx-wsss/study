package com.wsss.frame.json.jackson;

import lombok.Data;

@Data
public class Demo {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}