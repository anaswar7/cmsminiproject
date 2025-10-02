package com.group10.cms;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyController {

    private admin adminDb;

    public FacultyController() {
        adminDb = new admin(); // Uses your existing DB connection
    }

    public admin getAdminDb() {
        return adminDb;
    }

    public boolean validateFaculty(String username, String password) {
        try {
            String query = "SELECT * FROM faculty_credentials WHERE username=? AND password=?";
            PreparedStatement ps = adminDb.connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getFacultyDetails(String username) {
        try {
            String query = "SELECT * FROM faculty WHERE staff_id=?";
            PreparedStatement ps = adminDb.connection.prepareStatement(query);
            ps.setString(1, username);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
