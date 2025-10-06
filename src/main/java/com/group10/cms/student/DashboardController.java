package com.group10.cms.student;


import com.group10.cms.admin;
import com.group10.cms.ssviewcontroller;
import com.group10.cms.stud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    // Helper method to safely load a default image
    private void loadDefaultImage() {
        URL defaultImageUrl = getClass().getResource("images/default-profile.png");
        if (defaultImageUrl != null) {
            profileImage.setImage(new Image(defaultImageUrl.toExternalForm()));
        } else {
            System.err.println("Default profile image could not be found.");
        }
    }

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
            Image image = new Image(getClass().getResource(profpicPath).toExternalForm());
            if (!image.isError()) {
                profileImage.setImage(image);
            } else {
                System.err.println("Could not load image: " + profpicPath);
                loadDefaultImage();
            }
        } else {
            loadDefaultImage();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            Stage currentStage = (Stage) regnoLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(loader.load());

            URL cssUrl = getClass().getResource("styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            Stage loginStage = new Stage();
            loginStage.setTitle("Student Login");
            loginStage.setScene(scene);
            loginStage.show();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editProfile.fxml"));
            Scene scene = new Scene(loader.load());

            URL cssUrl = getClass().getResource("styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            Stage stage = new Stage();
            stage.setTitle("Edit Profile");
            stage.setScene(scene);

            EditProfileController controller = loader.getController();
            controller.initData(regnoLabel.getText());

            stage.showAndWait();
            refreshData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshData() {
        String currentUserRegno = regnoLabel.getText();
        admin ad = new admin();
        String query = "SELECT * FROM student WHERE regno = ?";

        try (Connection conn = DriverManager.getConnection(ad.getUrl(), ad.getUser(), ad.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, currentUserRegno);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                setStudentData(
                        rs.getString("regno"),
                        rs.getString("name"),
                        rs.getString("rollno"),
                        rs.getString("course"),
                        rs.getString("semester"),
                        rs.getString("dob"),
                        rs.getString("address"),
                        rs.getString("profpic")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void detailedv(ActionEvent event) {
        try {
            stud selrec = new stud(regnoLabel.getText(),nameLabel.getText(),Integer.parseInt(rollnoLabel.getText()),
                    courseLabel.getText(),semesterLabel.getText(),dobLabel.getText());
            String admname = " ";
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader root = new FXMLLoader(getClass().getResource("/com/group10/cms/singlestudentview.fxml"));
            Scene scene = new Scene(root.load());
            stage.setScene(scene);
            ssviewcontroller controller = root.getController();
            controller.initData(admname,selrec,"student","");
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

