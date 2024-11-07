package com.example.practica;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class InsertTime {

    @FXML
    private ComboBox<String> day;

    @FXML
    private ComboBox<String> dob;

    @FXML
    private Button dobavt;

    @FXML
    private ListView<Time> oclock;

    @FXML
    private Button vuhod;
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

        dobavt.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try{

                    Time selectedTime = null;
                    if (oclock.getSelectionModel().getSelectedItem() != null) {
                        selectedTime = oclock.getSelectionModel().getSelectedItem();
                    }
                    db.insertCalendar(day.getValue(),selectedTime);
                    int n = db.getIdCal(day.getValue(),selectedTime );
                    System.out.println(oclock.getSelectionModel().getSelectedItem());


                    System.out.println(n);


                    db.insertD_has_C(db.getIdDoc(dob.getValue()), n);
            } catch (Exception e) {
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

    }

