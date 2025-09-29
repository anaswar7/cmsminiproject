package com.group10.cms.faculty;

import com.group10.cms.FacultyController;
import com.group10.cms.FacultyEditController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyDashboardController {

    @FXML private Label lblStaffID;
    @FXML private Label lblName;
    @FXML private Label lblDOB;
    @FXML private Label lblBranch;
    @FXML private Label lblSubject;

    @FXML private Button btnEditProfile;
    @FXML private Button btnLogout;
    @FXML private Button btnViewStudents;

    private FacultyController controller = new FacultyController();
    private String facultyID;

    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
        loadFacultyDetails();
    }

    private void loadFacultyDetails() {
        try {
            ResultSet rs = controller.getFacultyDetails(facultyID);
            if (rs != null && rs.next()) {
                lblStaffID.setText(rs.getString("staff_id"));
                lblName.setText(rs.getString("name"));
                lblDOB.setText(rs.getString("dob"));
                lblBranch.setText(rs.getString("branch"));
                lblSubject.setText(rs.getString("subject"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // Edit Profile Button
        btnEditProfile.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group10/cms/facultyEdit.fxml"));
                Stage stage = (Stage) btnEditProfile.getScene().getWindow();
                Scene scene = new Scene(loader.load());

                FacultyEditController editController = loader.getController();
                editController.setFacultyID(facultyID);

                stage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Logout Button
        btnLogout.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group10/cms/facultyLogin.fxml"));
                Stage stage = (Stage) btnLogout.getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // View Students Button
        btnViewStudents.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group10/cms/studentView.fxml"));
                Stage stage = (Stage) btnViewStudents.getScene().getWindow();
                Scene scene = new Scene(loader.load());

                // Optional: you can pass facultyID if needed for filtering students
                // StudentViewController studentController = loader.getController();
                // studentController.setFacultyID(facultyID);

                stage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
