package com.group10.cms.student;



import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Stage;
import java.io.File;
import java.sql.*;

public class DashboardController {

    @FXML private Label regnoLabel;
    @FXML private Label nameLabel;
    @FXML private Label rollnoLabel;
    @FXML private Label courseLabel;
    @FXML private Label semesterLabel;
    @FXML private Label dobLabel;
    @FXML private Label addressLabel;
    @FXML private ImageView profileImage;

    public void setStudentData(String regno, String name, String rollno, String course,
                               String semester, String dob, String address, String profpicPath) {
        regnoLabel.setText(regno);
        nameLabel.setText(name);
        rollnoLabel.setText(rollno);
        courseLabel.setText(course);
        semesterLabel.setText(semester);
        dobLabel.setText(dob);
        addressLabel.setText(address);

        if (profpicPath != null && !profpicPath.isEmpty()) {
            File file = new File(profpicPath);
            if (file.exists()) {
                profileImage.setImage(new Image(file.toURI().toString()));
            } else {
                System.out.println("Image file not found at: " + profpicPath);
            }
        }
    }

    private void refreshData() {
        String currentUserRegno = regnoLabel.getText();

        String dbUrl = "jdbc:mysql://localhost:3306/demo";
        String dbUsername = "root";
        String dbPassword = "mysqlrootpassword1";
        String query = "SELECT * FROM student WHERE regno = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, currentUserRegno);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nameLabel.setText(rs.getString("name"));
                rollnoLabel.setText(rs.getString("rollno"));
                courseLabel.setText(rs.getString("course"));
                semesterLabel.setText(rs.getString("semester"));
                dobLabel.setText(rs.getString("dob"));
                addressLabel.setText(rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleEditProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editProfile.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Edit Profile");
            stage.setScene(new Scene(loader.load()));

            EditProfileController controller = loader.getController();
            controller.initData(regnoLabel.getText());

            // This line pauses the code until the edit window is closed
            stage.showAndWait();

            // AFTER the window is closed, refresh the dashboard data
            refreshData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML

    private void handleLogout() {
        try {
            // Get the current stage (the dashboard window)
            Stage currentStage = (Stage) regnoLabel.getScene().getWindow();

            // Load the login page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));

            // Create a new stage for the login page
            Stage loginStage = new Stage();
            loginStage.setTitle("Student Login");
            loginStage.setScene(new Scene(loader.load()));

            // Show the new login window
            loginStage.show();

            // Close the current dashboard window
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}