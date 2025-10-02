package com.group10.cms.faculty;

import com.group10.cms.FacultyController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class FacultyLoginController {

    @FXML private TextField txtUser;
    @FXML private PasswordField txtPass;
    @FXML private Button btnLogin;
    @FXML private Button btnCancel;
    @FXML private Label lblMessage;

    private FacultyController controller = new FacultyController();

    @FXML
    private void initialize() {
        btnLogin.setOnAction(e -> handleLogin());
        btnCancel.setOnAction(e -> clearFields());
    }

    private void handleLogin() {
        String username = txtUser.getText().trim();
        String password = txtPass.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Please enter both username and password.");
            lblMessage.setStyle("-fx-text-fill: #ffcc00; -fx-font-weight: bold;");
            return;
        }

        if (controller.validateFaculty(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group10/cms/facultyDashboard.fxml"));
                Scene scene = new Scene(loader.load());

                // Pass facultyID to dashboard controller
                FacultyDashboardController dashboardController = loader.getController();
                dashboardController.setFacultyID(username);

                // Get current stage and set new scene
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(scene);

            } catch (IOException ex) {
                ex.printStackTrace();
                lblMessage.setText("Error loading dashboard.");
                lblMessage.setStyle("-fx-text-fill: #ff0000; -fx-font-weight: bold;");
            }
        } else {
            lblMessage.setText("Invalid username or password!");
            lblMessage.setStyle("-fx-text-fill: #ff0000; -fx-font-weight: bold;");
        }
    }


    private void clearFields() {
        txtUser.clear();
        txtPass.clear();
        lblMessage.setText("");
    }
}
