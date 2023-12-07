package com.polok.eubmanagement.presentation.assignment.modal;

public class AssignmentData {
    String title;
    String details;
    String created_at;
    String key;

    public AssignmentData(String title, String details, String created_at, String key) {
        this.title = title;
        this.details = details;
        this.created_at = created_at;
        this.key = key;
    }

    public AssignmentData() {}

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

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String date) {
        this.created_at = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNotNullText(String text) {
        if (text != null && !text.isEmpty()) return text;
        else return "N/A";
    }
}
