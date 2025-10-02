package com.group10.cms;

import com.group10.cms.FacultyController;
import com.group10.cms.faculty.FacultyDashboardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyEditController {

    @FXML private Label lblStaffID;
    @FXML private TextField txtName;
    @FXML private TextField txtDOB;
    @FXML private TextField txtBranch;
    @FXML private TextField txtSubject;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

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
                txtName.setText(rs.getString("name"));
                txtDOB.setText(rs.getString("dob"));
                txtBranch.setText(rs.getString("branch"));
                txtSubject.setText(rs.getString("subject"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        btnSave.setOnAction(e -> {
            try {
                String updateQuery = "UPDATE faculty SET name=?, dob=?, branch=?, subject=? WHERE staff_id=?";
                PreparedStatement ps = controller.getAdminDb().connection.prepareStatement(updateQuery);
                ps.setString(1, txtName.getText().trim());
                ps.setString(2, txtDOB.getText().trim());
                ps.setString(3, txtBranch.getText().trim());
                ps.setString(4, txtSubject.getText().trim());
                ps.setString(5, facultyID);
                ps.executeUpdate();

                if (!txtPassword.getText().trim().isEmpty()) {
                    String passQuery = "UPDATE faculty_credentials SET password=? WHERE username=?";
                    PreparedStatement ps2 = controller.getAdminDb().connection.prepareStatement(passQuery);
                    ps2.setString(1, txtPassword.getText().trim());
                    ps2.setString(2, facultyID);
                    ps2.executeUpdate();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully!");
                alert.showAndWait();
            } catch (SQLException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error updating profile!").showAndWait();
            }
        });

        btnCancel.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group10/cms/facultyDashboard.fxml"));
                Stage stage = (Stage) btnCancel.getScene().getWindow();
                Scene scene = new Scene(loader.load());

                FacultyDashboardController dashController = loader.getController();
                dashController.setFacultyID(facultyID);

                stage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
