package com.group10.cms;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Marks {
    String subject;
    String marks;
    String subcode;

    public Marks(String subject, String marks, String subcode) {
        this.subject = subject;
        this.marks = marks;
        this.subcode = subcode;
    }

    public String getSubject() {
        return subject;
    }

    public String getMarks() {
        return marks;
    }

    public String getSubcode() {
        return subcode;
    }

}
