package com.app.beanvalidation.container;

import jakarta.validation.constraints.Min;

public class DataInteger {
    @Min(value = 10)
    private Integer data;

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }
}