package com.group10.cms.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;

public class LoginController {

    @FXML private TextField nameField; // Changed to nameField
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    // Defines the connection details for your MySQL database.
    private static final String DB_URL = "jdbc:mysql://localhost:3306/demo";
    private static final String DB_USERNAME = "evex";
    private static final String DB_PASSWORD = "evex07";

    // ... your DB connection strings ...

    @FXML
    private void handleLogin() {
        String name = nameField.getText(); // Get text from nameField
        String password = passwordField.getText();

        if (name.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Name and password cannot be empty.");
            return;
        }

        // CHANGE THIS: The query now checks the 'name' column
        String query = "SELECT * FROM student WHERE name = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Login successful, load dashboard
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                Scene scene = new Scene(loader.load());

                DashboardController controller = loader.getController();
                controller.setStudentData(
                        rs.getString("regno"),
                        rs.getString("name"),
                        rs.getString("rollno"),
                        rs.getString("course"),
                        rs.getString("semester"),
                        rs.getString("dob"),
                        rs.getString("address"),
                        rs.getString("profpic")
                );

                Stage stage = (Stage) nameField.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Student Dashboard");
            } else {
                errorLabel.setText("Invalid name or password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Database error.");
        }
    }
}