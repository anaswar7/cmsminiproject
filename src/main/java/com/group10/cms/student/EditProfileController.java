package com.group10.cms.student;


import com.group10.cms.admin;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditProfileController {

    // These connect to the controls in your editProfile.fxml file
    @FXML private TextField addressField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    // This will store the unique ID of the logged-in student
    private String currentUserRegno;

    // --- Database Connection Details ---

    /**
     * This method is called by the DashboardController to pass in the
     * unique registration number of the logged-in student.
     */
    public void initData(String regno) {
        this.currentUserRegno = regno;
        // You could also add code here to load the student's current address
        // and display it in the addressField.
    }

    /**
     * This method is called when the "Save Changes" button is clicked.
     */
    @FXML
    private void handleSaveChanges() {
        String newAddress = addressField.getText();
        String newPassword = passwordField.getText();

        String sqlQuery;

        // Check if the user entered a new password.
        // If the password field is empty, we only update the address.
        if (newPassword == null || newPassword.isEmpty()) {
            sqlQuery = "UPDATE student SET address = ? WHERE regno = ?";
        } else {
            // If the password field has text, we update both address and password.
            sqlQuery = "UPDATE student SET address = ?, password = ? WHERE regno = ?";
        }
        admin ad = new admin();
        try (Connection conn = DriverManager.getConnection(ad.getUrl(), ad.getUser(), ad.getPassword());
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            // Set the value for the first parameter (address)
            stmt.setString(1, newAddress);

            // Set the remaining parameters based on which query we're using
            if (newPassword == null || newPassword.isEmpty()) {
                stmt.setString(2, currentUserRegno);
            } else {
                stmt.setString(2, newPassword);
                stmt.setString(3, currentUserRegno);
            }

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                messageLabel.setText("Profile updated successfully!");
                messageLabel.setTextFill(Color.GREEN);
            } else {
                messageLabel.setText("Update failed. User not found.");
                messageLabel.setTextFill(Color.RED);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Database error during update.");
            messageLabel.setTextFill(Color.RED);
        }
    }

    /**
     * This method closes the "Edit Profile" window.
     */
    @FXML
    private void handleCancel() {
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.close();
    }
}