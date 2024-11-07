package com.example.practica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;


public class EditingTime {
    @FXML
    private ComboBox<String> day;

    @FXML
    private Button delete;

    @FXML
    private ComboBox<String> dob;

    @FXML
    private Button dobavt;

    @FXML
    private ListView<Time> oclock;

    @FXML
    private Button vuhod;
    public int DocT;
    public int r;
    public int t;
    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        db = new DB();

        dob.setStyle("-fx-font: 12px \"Segoe Print\";");
        day.setStyle("-fx-font: 12px \"Segoe Print\";");

        dob.getItems().addAll(db.getDoctors());
        day.getItems().addAll(db.getDay());

        ObservableList<Time> langs = FXCollections.observableArrayList(db.getOclock());
        oclock.setItems(langs);
        ArrayList<Time> selectedItems = new ArrayList<>();
        dob.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                DocT= db.getIdDoc(dob.getValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        dobavt.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try{
                    Time selectedTime = null;
                    if (oclock.getSelectionModel().getSelectedItem() != null) {
                        selectedTime = oclock.getSelectionModel().getSelectedItem();
                    }
                    r = db.getIdCal(day.getValue(), selectedTime);
                    if( r!= 0) {
                        int n = db.getIdCal(day.getValue(), selectedTime);
                        System.out.println(oclock.getSelectionModel().getSelectedItem());
                        System.out.println(n);
                        db.updateD_has_C(DocT, n);
                    }else if(r == 0 ){
                        db.insertCalendar(day.getValue(),selectedTime);
                        int n = db.getIdCal(day.getValue(),selectedTime );
                        System.out.println(oclock.getSelectionModel().getSelectedItem());
                        System.out.println(n);
                        db.insertD_has_C(db.getIdDoc(dob.getValue()), n);

                    }else {
                        System.out.println("такого времени не существует");
                    }
//
                } catch (Exception e) {
                    String x = e.getMessage();
                    showMessage("Ошибка", "" + x);
                    throw new RuntimeException(e);
                }


            }

        });

        delete.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try{
                    Time selectedTime = null;
                    if (oclock.getSelectionModel().getSelectedItem() != null) {
                        selectedTime = oclock.getSelectionModel().getSelectedItem();
                    }
                    r = db.getIdCal(day.getValue(), selectedTime);
                    t = db.getIdTime(DocT, r);
                    if( t!= 0) {
                        int n = db.getIdCal(day.getValue(), selectedTime);
                        System.out.println(oclock.getSelectionModel().getSelectedItem());
                        System.out.println(n);
                        db.deleteTime(DocT, n);

                    }else {
                        System.out.println("Такой записи не существует, проверьте данные доктора и расписания.");
                    }
//
                } catch (Exception e) {
                    String x = e.getMessage();
                    showMessage("Ошибка", "" + x);
                    throw new RuntimeException(e);
                }


            }

        });
        vuhod.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Получаем ссылку на текущее окно
                Stage currentStage = (Stage) vuhod.getScene().getWindow();

                // Получаем ссылку на предыдущее окно
                FXMLLoader loader = new FXMLLoader(getClass().getResource("helloDoctor.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage previousStage = new Stage();
                previousStage.setScene(new Scene(root));

                // Закрываем текущее окно и открываем предыдущее
                currentStage.close();
                previousStage.show();
            }
        });
    }
    private void showMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        Stage stage = (Stage)
                alert.getDialogPane().getScene().getWindow();
        Image icon = new Image("C:/Users/malin/IdeaProjects/practica/src/main/resources/com/example/practica/photo/pet-house.png");    // Установка иконки для главного окна приложения
        stage.getIcons().add(icon);
        alert.setContentText(content);
        alert.showAndWait();}
}



