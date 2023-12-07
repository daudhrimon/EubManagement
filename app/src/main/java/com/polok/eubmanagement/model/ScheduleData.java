package com.polok.eubmanagement.model;

public class ScheduleData {
    private String day;
    private String course_title;
    private String course_code;
    private String lecturer_name;
    private String start_end_time;
    private String room_no;

    public ScheduleData(String day, String courseTitle, String courseCode, String lecturerName, String startEndTime, String roomNo) {
        this.day = day;
        this.course_title = courseTitle;
        this.course_code = courseCode;
        this.lecturer_name = lecturerName;
        this.start_end_time = startEndTime;
        this.room_no = roomNo;
    }

    public ScheduleData() {}

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCourseTitle() {
        return course_title;
    }

    public void setCourseTitle(String course_title) {
        this.course_title = course_title;
    }

    public String getCourseCode() {
        return course_code;
    }

    public void setCourseCode(String course_code) {
        this.course_code = course_code;
    }

    public String getLecturerName() {
        return lecturer_name;
    }

    public void setLecturerName(String lecturer_name) {
        this.lecturer_name = lecturer_name;
    }

    public String getStartEndTime() {
        return start_end_time;
    }

    public void setStartEndTime(String start_end_time) {
        this.start_end_time = start_end_time;
    }

    public String getRoomNo() {
        return room_no;
    }

    public void setRoomNo(String room_no) {
        this.room_no = room_no;
    }

    public String getNotNullText(String text) {
        if (text != null && !text.isEmpty()) return text;
        else return "N/A";
    }
}
