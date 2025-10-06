package com.group10.cms;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class admin {
    String url = "jdbc:mysql://localhost:3306/demo";
    String user = "evex";
    String password = "evex07";
    Statement stmt = null;
    Connection connection = null;

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public admin() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.user, this.password);
            this.stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS student ("
                    + "regno VARCHAR(20) PRIMARY KEY,"
                    + "name VARCHAR(50),"
                    + "rollno INT,"
                    + "course VARCHAR(20),"
                    + "semester VARCHAR(2),"
                    + "dob VARCHAR(12),"
                    + "address VARCHAR(100),"
                    + "profpic VARCHAR(300),"
                    + "password VARCHAR(256)"
                    + ")";

            this.stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Subjects ("
                    + "subject_code VARCHAR(20) PRIMARY KEY,"
                    + "subject_name VARCHAR(100),"
                    + "semester VARCHAR(5),"
                    + "course VARCHAR(50),"
                    + "credits INT"
                    + ")";

            this.stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Marks ("
                    + "mark_id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "regno VARCHAR(20),"
                    + "subject_code VARCHAR(20),"
                    + "exam_type ENUM('Internal1','Internal2','External'),"
                    + "marks_obtained DECIMAL(5,2),"
                    + "max_marks DECIMAL(5,2),"
                    + "FOREIGN KEY (regno) REFERENCES student(regno),"
                    + "FOREIGN KEY (subject_code) REFERENCES Subjects(subject_code),"
                    + "UNIQUE (regno, subject_code, exam_type)"
                    + ")";

            this.stmt.executeUpdate(sql);


            sql = "CREATE TABLE IF NOT EXISTS Sessions ("
                    + "attendance_id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "subject_code VARCHAR(20),"
                    + "date DATE,"
                    + "session_no INT,"
                    + "FOREIGN KEY (subject_code) REFERENCES Subjects(subject_code)"
                    + ")";

            this.stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Attendance ("
                    + "record_id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "attendance_id INT,"
                    + "regno VARCHAR(20),"
                    + "status ENUM('P','A','L'),"
                    + "FOREIGN KEY (attendance_id) REFERENCES Sessions(attendance_id),"
                    + "FOREIGN KEY (regno) REFERENCES student(regno)"
                    + ")";

            this.stmt.executeUpdate(sql);


            sql = "SELECT COUNT(*) AS count FROM Subjects";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next() && rs.getInt("count") == 0) {
                System.out.println("Inserting default subjects for B.Tech CSE Semester 3 (2024-2028)...");

                String[][] subjects = {
                        {"GAMAT301", "Mathematics for Information Science-3", "S3", "CSE", "3"},
                        {"PCCST302", "Theory of Computation", "S3", "CSE", "4"},
                        {"PCCST303", "Data Structures and Algorithms", "S3", "CSE", "4"},
                        {"PBCST304", "Object Oriented Programming", "S3", "CSE", "4"},
                        {"GAEST305", "Digital Electronics & Logic Design", "S3", "CSE", "4"},
                        {"UCHUT346", "Economics for Engineers", "S3", "CSE", "2"}
                };

                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO Subjects (subject_code, subject_name, semester, course, credits) VALUES (?, ?, ?, ?, ?)"
                );

                for (String[] sub : subjects) {
                    ps.setString(1, sub[0]);
                    ps.setString(2, sub[1]);
                    ps.setString(3, sub[2]);
                    ps.setString(4, sub[3]);
                    ps.setInt(5, Integer.parseInt(sub[4]));
                    ps.executeUpdate();
                }

                ps.close();
                System.out.println("Subjects inserted successfully!");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public Boolean adminaut(String user,String pass) {
        try {
            Statement stmt = this.stmt;
            ResultSet rs = stmt.executeQuery(String.format("select * from admin where username = '%s' and password = '%s';",user,pass));
            if (!(rs.next())) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String studentadd(String regno,String name,int rollno,String course,String semester,String dob,String address) {
        try {
            Statement stmt = this.stmt;
            int rows = stmt.executeUpdate(String.format("insert into student(regno,name,rollno,course,semester,dob,address) values('%s','%s',%d,'%s','%s','%s','%s');",regno,name,rollno,course,semester,dob,address));
            return "";
        } catch (SQLException e) {
            e.printStackTrace();
            String fullMessage = e.toString();
            int colonIndex = fullMessage.indexOf(": ");
            if (colonIndex != -1 && colonIndex < fullMessage.length() - 2) {
                return fullMessage.substring(colonIndex + 2);
            }
            return fullMessage;
        }
    }

    public String facultyadd(String staffid, String name, String dob, String branch, String subject) {
        try {
            Statement stmt = this.stmt;

            // Insert into faculty table
            int rows1 = stmt.executeUpdate(String.format(
                    "INSERT INTO faculty(staff_id, name, dob, branch, subject) VALUES('%s','%s','%s','%s','%s');",
                    staffid, name, dob, branch, subject));

            // Insert into faculty_credentials table (staff_id used as username, default password set)
            int rows2 = stmt.executeUpdate(String.format(
                    "INSERT INTO faculty_credentials(username, password) VALUES('%s', '%s');",
                    staffid, staffid));   // set staff_id as both username and password

            return "";
        } catch (SQLException e) {
            e.printStackTrace();
            String fullMessage = e.toString();
            int colonIndex = fullMessage.indexOf(": ");
            if (colonIndex != -1 && colonIndex < fullMessage.length() - 2) {
                return fullMessage.substring(colonIndex + 2);
            }
            return fullMessage;
        }
    }

    public String updatestudent(String s) {
        try {
            Statement stmt = this.stmt;
            int rs = stmt.executeUpdate(s);
            return "";
        } catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public ResultSet studentfetch(String s) {
        try {
            Statement stmt = this.stmt;
            ResultSet rs = stmt.executeQuery(s);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Subjects> getAllSubjects() {
        ArrayList<Subjects> subjects = new ArrayList<>();

        String query = "SELECT * FROM subjects ORDER BY subject_name;";
        try (ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                subjects.add(new Subjects(
                        rs.getString("subject_code"),
                        rs.getString("subject_name"),
                        rs.getString("semester"),
                        rs.getString("course"),
                        rs.getInt("credits")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }

    public ArrayList<Subjects> getAllSubjectssem(String semester) {
        ArrayList<Subjects> subjects = new ArrayList<>();

        String query = String.format("SELECT * FROM subjects where semester = '%s' ORDER BY subject_name;",semester);
        try (ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                subjects.add(new Subjects(
                        rs.getString("subject_code"),
                        rs.getString("subject_name"),
                        rs.getString("semester"),
                        rs.getString("course"),
                        rs.getInt("credits")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }

    public int insertSession(String subjectCode, LocalDate date, int sessionNo) {
        String sql = "INSERT INTO sessions (subject_code, date, session_no) VALUES (?, ?, ?)";
        try ( // your DB helper
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, subjectCode);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setInt(3, sessionNo);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void insertAttendance(int attendanceId, String regno, String status) {
        String sql = "INSERT INTO attendance (attendance_id, regno, status) VALUES (?, ?, ?)";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, attendanceId);
            ps.setString(2, regno);
            ps.setString(3, status);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void admain() {
        Scanner sc = new Scanner(System.in);
        admin ad = new admin();
        Boolean bol = ad.adminaut("jibhin","69420");
        if (bol) {
            System.out.println("successful login");
        } else {
            System.out.println("invalid username or password. Try again");
        }
        System.out.println("Would you like to add student? Y/N");
        if (sc.nextLine().charAt(0) == 'Y') {
            ad.studentadd("HGW24CS017","Devadas",17,"CS","S3","01-01-2069","Edakulam P.O Irinjalakuda");
        }
    }
}

/*
// New method to validate faculty login
    public boolean validateFaculty(String username, String password) {
        try {
            String query = "SELECT * FROM faculty_credentials WHERE username=? AND password=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
 */

