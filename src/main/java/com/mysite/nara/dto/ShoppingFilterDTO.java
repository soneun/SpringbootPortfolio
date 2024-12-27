package com.mysite.nara.dto;

public class ShoppingFilterDTO {

    private String keyword;

    private String startDate;

    private String endDate;

    public ShoppingFilterDTO() {
    }

    public ShoppingFilterDTO(String startDate, String endDate, String keyword) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyword = keyword;
    }

    public ShoppingFilterDTO(String endDate, String startDate) {
        this.endDate = endDate;
        this.startDate = startDate;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ShoppingFilterDTO{" +
                "keyword='" + keyword + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
