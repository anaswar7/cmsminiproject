package com.group10.cms;


public class Faculty{
    String staffId;
    String name;
    String branch;
    String subject;
    String dob;


    public Faculty(String staffId, String name,String dob,String branch,String subject) {
        this.staffId = staffId;
        this.name = name;
        this.branch = branch;
        this.subject = subject;
        this.dob = dob;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getName() {
        return name;
    }


    public String getBranch() {
        return branch;
    }

    public String getSubject() {
        return subject;
    }

    public String getDob() {
        return dob;
    }
}
