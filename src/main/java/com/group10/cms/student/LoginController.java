package com.group10.cms.student;


import com.group10.cms.admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;

public class LoginController {

    @FXML private TextField nameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;


    @FXML
    private void handleLogin() {
        String name = nameField.getText();
        String password = passwordField.getText();
        admin ad = new admin();

        if (name.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Name and password cannot be empty.");
            return;
        }

        String query = "SELECT * FROM student WHERE name = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(ad.getUrl(), ad.getUser(), ad.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                Scene scene = new Scene(loader.load());

                // --- FIX: Apply the stylesheet to the new dashboard scene ---
                scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

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
