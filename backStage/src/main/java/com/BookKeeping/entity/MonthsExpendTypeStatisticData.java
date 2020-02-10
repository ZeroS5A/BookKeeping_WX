package com.BookKeeping.entity;

public class MonthsExpendTypeStatisticData {
    private String name;
    private Float data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getData() {
        return data;
    }

    public void setData(Float data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MonthsExpendTypeStatisticData{" +
                "name='" + name + '\'' +
                ", data=" + data +
                '}';
    }
}
