package com.group10.cms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class subjectselcontroller {
    @FXML
    private ListView<Subjects> subjectlist;
    private List<Subjects> selected = new ArrayList<>();

    private List<stud> selectedStudents;
    private TableView<stud> table;
    int sessionNo;

    public void initialize() {
        admin ad = new admin();
        subjectlist.getItems().addAll(ad.getAllSubjects());
        subjectlist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void initData(List<stud> selected, TableView<stud> table) {
        selectedStudents = selected;
        this.table = table;
        sessionNo = 1;
        admin ad = new admin();
        try {
            ResultSet rs = ad.studentfetch("select max(session_no) as last_session from sessions where date = CURDATE()");
            if (rs.next()) {
                int lastSession = rs.getInt("last_session");
                if (!rs.wasNull()) {
                    sessionNo = lastSession + 1;
                } else {
                    sessionNo = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void filter(ActionEvent event) {
        admin ad = new admin();
        MenuItem item = (MenuItem) event.getSource();
        List<Subjects> filsubjects = ad.getAllSubjectssem(item.getText());
        subjectlist.getItems().clear();
        subjectlist.getItems().addAll(filsubjects);
    }



    @FXML
    private void onConfirm() {
        admin ad = new admin();
        selected.addAll(subjectlist.getSelectionModel().getSelectedItems());
        LocalDate today = LocalDate.now();

        for (Subjects subject : selected) {
            int attendanceId = ad.insertSession(subject.getCode(), today, sessionNo);
            sessionNo++;

            for (stud student : selectedStudents) {
                for (stud s : table.getItems()) {
                    String status = s.getPresent().isSelected() ? "P" : "A";
                    ad.insertAttendance(attendanceId, s.getRegno(), status);
                }
            }
        }
        ((Stage) subjectlist.getScene().getWindow()).close();
    }
}
