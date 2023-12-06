package com.polok.eubmanagement.data.model;

public class UserProfileData {
    private String studentId;
    private String fullName;
    private String mobileNumber;
    private String email;
    private String gender;
    private String section;
    private String bloodGroup;
    private Boolean isAdmin;

    public UserProfileData(
            String studentId,
            String fullName,
            String mobileNumber,
            String email,
            String gender,
            String section,
            String bloodGroup,
            Boolean isAdmin
    ) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.gender = gender;
        this.section = section;
        this.bloodGroup = bloodGroup;
        this.isAdmin = isAdmin;
    }

    public UserProfileData() {}

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getNotNullText(String text) {
        if (text != null && !text.isEmpty()) return text;
        else return "N/A";
    }
}
