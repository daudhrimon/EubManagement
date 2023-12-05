package com.polok.eubmanagement.presentation.notice.model;

public class NoticeData {
    String title;
    String details;
    String date;
    String key;

    public NoticeData(String title, String details, String date, String key) {
        this.title = title;
        this.details = details;
        this.date = date;
        this.key = key;
    }

    public NoticeData() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



}
