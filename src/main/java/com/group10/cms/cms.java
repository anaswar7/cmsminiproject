package com.group10.cms;


import java.sql.*;
import java.util.Scanner;


class admin {
    String url = "jdbc:mysql://localhost:3306/demo";
    String user = "evex";
    String password = "evex07";
    Statement stmt = null;
    Connection connection = null;
    public admin() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.user, this.password);
            this.stmt = connection.createStatement();
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
            int rows = stmt.executeUpdate(String.format("insert into student values('%s','%s',%d,'%s','%s','%s','%s');",regno,name,rollno,course,semester,dob,address));
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

