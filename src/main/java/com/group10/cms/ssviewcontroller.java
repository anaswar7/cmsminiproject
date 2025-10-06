package com.group10.cms;

import com.group10.cms.student.DashboardController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ssviewcontroller {
    @FXML private TextField regnof;
    @FXML private TextField namef;
    @FXML private TextField rollnof;
    @FXML private TextField coursef;
    @FXML private TextField semesterf;
    @FXML private TextField dobf;
    @FXML private Text regno;
    @FXML private Text name;
    @FXML private Text namemain;
    @FXML private Text rollno;
    @FXML private Text course;
    @FXML private Text semester;
    @FXML private Text dob;
    @FXML private Text address;
    @FXML private TextField addressf;
    private Text[] attributes;
    private TextField[] attributefields;
    private ImageView[] attributeimages;
    private String admname;
    private String[] columns;
    @FXML private ImageView regnoi;
    @FXML private ImageView namei;
    @FXML private ImageView rollnoi;
    @FXML private ImageView coursei;
    @FXML private ImageView semesteri;
    @FXML private ImageView dobi;
    @FXML private ImageView addressi;
    @FXML private Button deletebut;
    private String carry;
    @FXML private Circle profpic;

    @FXML private TableView<Marks> table;
    @FXML private TableColumn<Marks,String> subcol;
    @FXML private TableColumn<Marks,String> marcol;
    @FXML private TableColumn<Marks,String> subcodecol;
    @FXML private MenuButton semfilter;
    @FXML private MenuButton examfilter;

    private final ObservableList<Marks> list = FXCollections.observableArrayList();
    private String[] exams;
    private Integer[] examval;
    private int index;
    @FXML private Text sgpa;

    @FXML private LineChart <String,Double> attchart;
    private final String[] months = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};
    private int joinyear;
    private final int currentyear = 2025;
    @FXML private MenuButton attyear;
    private String rights;
    @FXML private Button backbut;
    private String profpicpath;

    public void initialize() {
        columns = new String[]{"regno","name","rollno","course","semester","dob","address"};
        attributes = new Text[]{regno,name,rollno,course,semester,dob,address};
        attributefields = new TextField[]{regnof,namef,rollnof,coursef,semesterf,dobf,addressf};
        attributeimages = new ImageView[]{regnoi,namei,rollnoi,coursei,semesteri,dobi,addressi};
        Image img = new Image(getClass().getResource("/com/group10/cms/edit.png").toExternalForm());
        for (ImageView attributeimage : attributeimages ) {
            attributeimage.setImage(img);
        }
        exams = new String[]{"Internal1","Internal2","External"};
        examval = new Integer[]{0,0,0};
    }


    public void initData(String s,stud student, String rights,String carry) {
        admname = s;
        regno.setText(student.regno);
        name.setText(student.name);
        namemain.setText(student.name);
        rollno.setText(Integer.toString(student.rollno));
        course.setText(student.course);
        semester.setText(student.semester);
        dob.setText(student.dob);
        semfilter.setText(student.semester);
        this.carry = carry;
        this.rights = rights;
        if (rights.equals("student")) {
            for (ImageView attributeimage : attributeimages) {
                attributeimage.setDisable(true);
                attributeimage.setVisible(false);
            }
            deletebut.setDisable(true);
            deletebut.setVisible(false);
        }
        admin ad = new admin();
        try {
            Image img;
            ResultSet rs = ad.studentfetch(String.format("select address,profpic,joining_year from student where name = '%s';", student.name));
            rs.next();
            address.setText(rs.getString(1));
            profpicpath = rs.getString(2);
            if (rs.getString(2) != null && (getClass().getResource(rs.getString(2))!= null)) {
                img = new Image(getClass().getResource(rs.getString(2)).toExternalForm());
            } else {
                img = new Image(getClass().getResource("/com/group10/cms/students/default.jpg").toExternalForm());
            }
            joinyear = rs.getInt(3);
            profpic.setFill(new ImagePattern(img));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Text attribute : attributes) {
            attribute.setVisible(true);
            attribute.setDisable(false);
        }

        subcol.setCellValueFactory(new PropertyValueFactory<Marks,String>("subject"));
        marcol.setCellValueFactory(new PropertyValueFactory<Marks,String>("marks"));
        subcodecol.setCellValueFactory(new PropertyValueFactory<Marks,String>("subcode"));
        attchart.getData().clear();
        attchart.setAnimated(false);
        XYChart.Series<String,Double> attendance = new XYChart.Series<String, Double>();
        try {

            ResultSet rs = ad.studentfetch(String.format("SELECT m.exam_type" +
                    " FROM Marks m" +
                    " JOIN Subjects sub ON m.subject_code = sub.subject_code" +
                    " WHERE m.regno = '%s' AND sub.semester = '%s';",student.regno,student.semester));
            while (rs.next()) {
                for (int i = 0;i<exams.length;i++) {
                    if (rs.getString(1).equals(exams[i])) {
                        examval[i] = 1;
                    }
                }
            }
            index = 0;
            for (int i = 0;i<examval.length;i++) {
                if (examval[i] == 1) {
                    MenuItem item = new MenuItem(exams[i]);
                    int finalI = i;
                    item.setOnAction(event-> {
                        final String temp = exams[finalI];
                        examfilter.setText(temp);
                        manualfilter(item,student);
                    });
                    examfilter.getItems().add(item);
                    index = i;
                }
            }
            examfilter.setText(exams[index]);
            double sgpaval = student.sgpa(student.regno,student.semester);

            if (examval[0]==1 && examval[1]==1 && examval[2]==1) {
                sgpa.setText(String.format("SGPA : %.2f",sgpaval));
            }

            rs = ad.studentfetch(String.format("SELECT sub.subject_name, m.marks_obtained, m.max_marks,sub.subject_code,m.exam_type" +
                    " FROM Marks m" +
                    " JOIN Subjects sub ON m.subject_code = sub.subject_code" +
                    " WHERE m.regno = '%s' AND sub.semester = '%s' AND m.exam_type = '%s';",student.regno,student.semester,exams[index]));
            String m;
            while (rs.next()) {
                m = rs.getInt(2)+"/"+rs.getInt(3);
                list.add(new Marks(rs.getString(1),m,rs.getString(4)));
            }

            int sem = Integer.parseInt(student.semester.substring(1));
            for (int i=1;i<=sem;i++) {
                final String temp;
                temp = "S"+i;
                MenuItem item = new MenuItem(temp);
                item.setOnAction(event-> {
                    semfilter.setText(temp);
                    manualfilter(item,student);
                });
                semfilter.getItems().add(item);
            }

            rs = ad.studentfetch(String.format("SELECT " +
                    "    r.regno, " +
                    "YEAR(s.date) AS year, "+
                    "    MONTH(s.date) AS month, " +
                    "    ROUND(SUM(CASE WHEN r.status='P' THEN 1 ELSE 0 END) / COUNT(*) * 100, 2) AS attendance_percentage " +
                    "FROM Attendance r " +
                    "JOIN Sessions s ON r.attendance_id = s.attendance_id " +
                    "WHERE r.regno = '%s' AND YEAR(s.date) = %s " +
                    "GROUP BY r.regno, YEAR(s.date), MONTH(s.date) " +
                    "ORDER BY r.regno, month;",student.regno,currentyear));

            while (rs.next()) {
                attendance.getData().add(new XYChart.Data<String,Double>(months[rs.getInt(3)-1],rs.getDouble(4)));
            }
            attchart.getData().add(attendance);
            for (int i=joinyear;i<=currentyear;i++) {
                final String temp2;
                temp2 = ""+i;
                MenuItem item = new MenuItem(temp2);
                item.setOnAction(event-> {
                    attyear.setText(temp2);
                    filteratt(item,student,attendance);
                });
                attyear.getItems().add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(list);

    }

    private void manualfilter(MenuItem item,stud student) {
        admin ad = new admin();
        try {
            ResultSet rs;
            if (item.getText().length()==2) {
                rs = ad.studentfetch(String.format("SELECT sub.subject_name, m.marks_obtained, m.max_marks,sub.subject_code" +
                        " FROM Marks m" +
                        " JOIN Subjects sub ON m.subject_code = sub.subject_code" +
                        " WHERE m.regno = '%s' AND sub.semester = '%s' AND m.exam_type = '%s';", regno.getText(), item.getText(), examfilter.getText()));
                double sgpaval = student.sgpa(student.regno,item.getText());

                if (examval[0]==1 && examval[1]==1 && examval[2]==1) {
                    sgpa.setText(String.format("SGPA : %.2f",sgpaval));
                }

            } else {
                rs = ad.studentfetch(String.format("SELECT sub.subject_name, m.marks_obtained, m.max_marks,sub.subject_code" +
                        " FROM Marks m" +
                        " JOIN Subjects sub ON m.subject_code = sub.subject_code" +
                        " WHERE m.regno = '%s' AND sub.semester = '%s' AND m.exam_type = '%s';", regno.getText(), semfilter.getText(), item.getText()));
            }
            String m;
            list.clear();
            while (rs.next()) {
                m = rs.getInt(2)+"/"+rs.getInt(3);
                list.add(new Marks(rs.getString(1),m,rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filteratt(MenuItem item, stud student, XYChart.Series<String, Double> attendance) {
        admin ad = new admin();
        try {
            attendance.getData().clear();
            attchart.getData().clear();
            ResultSet rs;
            rs = ad.studentfetch(String.format("SELECT " +
                    "    r.regno, " +
                    "YEAR(s.date) AS year, "+
                    "    MONTH(s.date) AS month, " +
                    "    ROUND(SUM(CASE WHEN r.status='P' THEN 1 ELSE 0 END) / COUNT(*) * 100, 2) AS attendance_percentage " +
                    "FROM Attendance r " +
                    "JOIN Sessions s ON r.attendance_id = s.attendance_id " +
                    "WHERE r.regno = '%s' AND YEAR(s.date) = %s " +
                    "GROUP BY r.regno, YEAR(s.date), MONTH(s.date) " +
                    "ORDER BY r.regno, month;",student.regno,Integer.parseInt(item.getText())));
            while (rs.next()) {
                attendance.getData().add(new XYChart.Data<String,Double>(months[rs.getInt(3)-1],rs.getDouble(4)));
            }
            attchart.getData().add(attendance);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void edit(MouseEvent e) {
        if (e.getSource() == regnoi) {
            regnof.setVisible(true);
            regnof.setDisable(false);
            regno.setVisible(false);
            regnof.setText(regno.getText());
        }
        else if (e.getSource() == namei) {
            namef.setVisible(true);
            namef.setDisable(false);
            name.setVisible(false);
            namef.setText(name.getText());
        }
        else if (e.getSource() == rollnoi) {
            rollnof.setVisible(true);
            rollnof.setDisable(false);
            rollno.setVisible(false);
            rollnof.setText(rollno.getText());
        }
        else if (e.getSource() == coursei) {
            coursef.setVisible(true);
            coursef.setDisable(false);
            course.setVisible(false);
            coursef.setText(course.getText());
        }
        else if (e.getSource() == semesteri) {
            semesterf.setVisible(true);
            semesterf.setDisable(false);
            semester.setVisible(false);
            semesterf.setText(semester.getText());
        }
        else if (e.getSource() == dobi) {
            dobf.setVisible(true);
            dobf.setDisable(false);
            dob.setVisible(false);
            dobf.setText(dob.getText());
        }
        else if (e.getSource() == addressi) {
            addressf.setVisible(true);
            addressf.setDisable(false);
            address.setVisible(false);
            addressf.setText(address.getText());
        }
        for (ImageView attributeimage : attributeimages ) {
            attributeimage.setDisable(true);
            attributeimage.setVisible(false);
        }
    }

    @FXML
    private void editfin(ActionEvent e) {
        admin ad = new admin();
        String sql;
        int res;

        if (e.getSource() == regnof) {
            sql = "update student set regno = ? where regno = ?;";
            try (PreparedStatement preparedStatement = ad.connection.prepareStatement(sql)) {
                preparedStatement.setString(1,regnof.getText());
                preparedStatement.setString(2,regno.getText());
                res = preparedStatement.executeUpdate();
                if (res > 0) {
                    regno.setVisible(true);
                    regno.setDisable(false);
                    regnof.setDisable(true);
                    regnof.setVisible(false);
                    regno.setText(regnof.getText());
                } else {
                    System.out.println("failed to update : " + res);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } else {
            for (int i = 1; i< columns.length;i++) {
                if (e.getSource() == attributefields[i]) {
                    System.out.println(attributes[i].getText().toLowerCase());
                    sql = "UPDATE student SET "+columns[i]+" = ? WHERE regno = ?;";
                    try (PreparedStatement preparedStatement = ad.connection.prepareStatement(sql)) {
                        if (e.getSource()!=rollnof) {
                            preparedStatement.setString(1, attributefields[i].getText());
                        } else {
                            preparedStatement.setInt(1, Integer.parseInt(attributefields[i].getText()));
                        }
                        preparedStatement.setString(2, regno.getText());
                        res = preparedStatement.executeUpdate();
                        if (res > 0) {
                            attributes[i].setVisible(true);
                            attributes[i].setDisable(false);
                            attributefields[i].setDisable(true);
                            attributefields[i].setVisible(false);
                            attributes[i].setText(attributefields[i].getText());
                        } else {
                            System.out.println("failed to update : " + res);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            }}
        for (ImageView attributeimage : attributeimages ) {
            attributeimage.setDisable(false);
            attributeimage.setVisible(true);
        }
    }

    @FXML
    private void delete(ActionEvent e) {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
        al.setTitle("Confirmation");
        al.setHeaderText("Are you sure you want to proceed with deletion?");
        al.setContentText("This action will delete the record");
        Optional<ButtonType> choice = al.showAndWait();
        if (choice.get() == ButtonType.OK) {
            admin ad = new admin();
            String res = ad.updatestudent(String.format("delete from student where regno = '%s';",regno.getText()));
            try {
                Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                FXMLLoader root = new FXMLLoader(getClass().getResource("studentview.fxml"));
                Scene scene = new Scene(root.load());
                studentviewcontroller controller = root.getController();
                controller.initData(this.admname,"admin","");
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    @FXML
    private void goback(ActionEvent e) {
        try {
            if (rights.equals("admin")) {
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                FXMLLoader root = new FXMLLoader(getClass().getResource("studentview.fxml"));
                Scene scene = new Scene(root.load());
                scene.getStylesheets().add(getClass().getResource("studentview.css").toExternalForm());
                studentviewcontroller controller = root.getController();
                controller.initData(this.admname,"admin",carry);
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
            }
            else {
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                FXMLLoader root = new FXMLLoader(getClass().getResource("student/dashboard.fxml"));
                Scene scene = new Scene(root.load());
                scene.getStylesheets().add(getClass().getResource("student/styles.css").toExternalForm());
                DashboardController controller = root.getController();
                controller.setStudentData(regno.getText(),name.getText(),rollno.getText(),course.getText(),semester.getText(),dob.getText(),
                        address.getText(),profpicpath);
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
