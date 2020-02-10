package com.BookKeeping.entity;

public class MonthsStatisticData {
    private Integer Year;
    private Integer Month;
    private Float totExpend;
    private Float totIncome;

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public Integer getMonth() {
        return Month;
    }

    public void setMonth(Integer month) {
        Month = month;
    }

    public Float getTotExpend() {
        return totExpend;
    }

    public void setTotExpend(Float totExpend) {
        this.totExpend = totExpend;
    }

    public Float getTotIncome() {
        return totIncome;
    }

    public void setTotIncome(Float totIncome) {
        this.totIncome = totIncome;
    }

    @Override
    public String toString() {
        return "MonthsStatisticData{" +
                "Year=" + Year +
                ", Month=" + Month +
                ", totExpend=" + totExpend +
                ", totIncome=" + totIncome +
                '}';
    }
}
