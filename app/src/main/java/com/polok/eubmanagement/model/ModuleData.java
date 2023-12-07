package com.polok.eubmanagement.model;

public class ModuleData {
    String title;
    String link;
    String created_at;
    String key;

    public ModuleData(String title, String link, String created_at, String key) {
        this.title = title;
        this.link = link;
        this.created_at = created_at;
        this.key = key;
    }

    public ModuleData() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
