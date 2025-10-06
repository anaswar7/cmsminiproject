package com.group10.cms;

import com.group10.cms.faculty.FacultyDashboardController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class studentviewcontroller implements Initializable {
    @FXML private TableView<stud> table;
    @FXML private TableColumn<stud,String> regno;
    @FXML private TableColumn<stud,String> name;
    @FXML private TableColumn<stud,Integer> rollno;
    @FXML private TableColumn<stud,String> course;
    @FXML private TableColumn<stud,String> semester;
    @FXML private TableColumn<stud,String> dob;
    private String admname;
    @FXML private TextField ftext;
    @FXML private MenuItem fname;
    @FXML private MenuItem fcourse;
    private String filter;
    @FXML private CheckBox mc;
    @FXML private Button back;
    private final Image backimg = new Image("file:src/main/resources/com/group10/cms/images/goback.png");
    private final ImageView backimgv = new ImageView(backimg);

    private final ObservableList<stud> list = FXCollections.observableArrayList();
    private String rights;
    private String user;
    @FXML
    private Button attbut;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        regno.setCellValueFactory(new PropertyValueFactory<stud,String>("regno"));
        name.setCellValueFactory(new PropertyValueFactory<stud,String>("name"));
        rollno.setCellValueFactory(new PropertyValueFactory<stud,Integer>("rollno"));
        course.setCellValueFactory(new PropertyValueFactory<stud,String>("course"));
        semester.setCellValueFactory(new PropertyValueFactory<stud,String>("semester"));
        dob.setCellValueFactory(new PropertyValueFactory<stud,String>("dob"));
        admin ad = new admin();
        try {
            ResultSet rs = ad.studentfetch("select * from student;");
            while (rs.next()) {
                list.add(new stud(rs.getString(1), rs.getString(2), rs.getInt(3),
                        rs.getString(4),rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(list);
        table.setFocusTraversable(true);
        table.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    stud selrec = table.getSelectionModel().getSelectedItem();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader root = new FXMLLoader(getClass().getResource("singlestudentview.fxml"));
                    Scene scene = new Scene(root.load());
                    stage.setScene(scene);
                    ssviewcontroller controller = root.getController();
                    controller.initData(admname,selrec,"admin",user);
                    stage.show();
                    stage.centerOnScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        backimgv.getStyleClass().add("image-view");
        back.getStyleClass().add("hover-darken-button");
        backimgv.setFitHeight(40);
        backimgv.setFitWidth(150);
        back.setContentDisplay(ContentDisplay.LEFT);
        back.setGraphic(backimgv);
    }

    public void initData(String admname, String rights, String user) {
        this.admname = admname;
        this.rights = rights;
        this.user = user;
        if (rights.equals("faculty")||rights.equals("admin")) {
            attbut.setVisible(true);
            attbut.setDisable(false);
            TableColumn<stud, CheckBox> present = new TableColumn<>("Present");
            present.setCellValueFactory(new PropertyValueFactory<stud, CheckBox>("present"));
            present.setPrefWidth(100);
            dob.setPrefWidth(90);
            table.getColumns().add(present);
        }
    }

    @FXML
    private void subjectview(ActionEvent event) {
        ObservableList<stud> allStudents = table.getItems();

        List<stud> selectedStudents = allStudents.stream()
                .filter(s -> s.getPresent().isSelected())
                .toList();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("subjectselector.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subjectselcontroller controller = loader.getController();
        admin ad = new admin();

        controller.initData(selectedStudents,table);
        Stage popup = new Stage();
        popup.setScene(new Scene(root));
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();


    }

    @FXML
    private void goback(ActionEvent e) {
        try {
            if ((rights == "admin")&&(user == ""||user==" ")) {
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                FXMLLoader root = new FXMLLoader(getClass().getResource("sessionpage.fxml"));
                Scene scene = new Scene(root.load());
                sessionpagecontroller controller = root.getController();
                controller.initData(this.admname);
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
            }
            else {
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                FXMLLoader root = new FXMLLoader(getClass().getResource("facultyDashBoard.fxml"));
                Scene scene = new Scene(root.load());
                FacultyDashboardController dashboardController = root.getController();
                dashboardController.setFacultyID(user);
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void filter(String filtercol) {
        ObservableList<stud> list2 = FXCollections.observableArrayList();
        ftext.textProperty().addListener((observable,oldValue,newValue)-> {
            admin ad = new admin();
            try {
                ResultSet rs;
                list2.clear();
                if (mc.isSelected()) {
                    rs = ad.studentfetch(String.format("select * from student where %s like '%%%s%%';",filtercol,newValue));
                } else {
                    rs = ad.studentfetch(String.format("select * from student where %s like '%s%%';", filtercol, newValue));
                }
                while (rs.next()) {
                    list2.add(new stud(rs.getString(1), rs.getString(2), rs.getInt(3),
                            rs.getString(4),rs.getString(5), rs.getString(6)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            table.setItems(list2);
        });
    }

    @FXML
    private void filterenter(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            ftext.setDisable(true);
            ftext.setVisible(false);
            mc.setDisable(true);
            mc.setVisible(false);
        }
    }

    @FXML
    private void filterby(ActionEvent e) {
        mc.setDisable(false);
        mc.setVisible(true);
        ftext.setDisable(false);
        ftext.setVisible(true);
        MenuItem but = (MenuItem) e.getSource();
        filter = but.getText();
        filter(filter);
    }

}
