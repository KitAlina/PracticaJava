package com.example.practica;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class editingPatients {

    @FXML
    private TextField age;

    @FXML
    private TextField breed;

    @FXML
    private Button delete;

    @FXML
    private TextField fio;

    @FXML
    private ComboBox<String> hosts;

    @FXML
    private ComboBox<String> idPatienC;

    @FXML
    private ImageView image;

    @FXML
    private Button sohr;

    @FXML
    private TextField type_of_animal;
    @FXML
    private Button vuhod;
    public int idPat;
    public int idHostP;
    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();
        Image ima = new Image("C:/Users/malin/IdeaProjects/practica/src/main/resources/com/example/practica/photo/veterinarian.png");
        image.setImage(ima);
        idPatienC.setStyle("-fx-font: 12px \"Segoe Print\";");
        idHostP = db.getidPatInt(idPat);
        idPatienC.getItems().addAll(db.getPatients());
        hosts.getItems().addAll(db.getHosts());

        idPatienC.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                idPat = db.idPat(idPatienC.getValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                fio.setText(db.getOneName(idPat));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                age.setText(String.valueOf(db.getOneAge(idPat)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                type_of_animal.setText(String.valueOf(db.getOneType_of_animal(idPat)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                breed.setText(String.valueOf(db.getOneBreed(idPat)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        sohr.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {

                        System.out.println();
                        int Pat_id = db.idPat(idPatienC.getValue());
                        db.updatePat(fio.getText(), Integer.parseInt(age.getText()), type_of_animal.getText(), breed.getText(), Pat_id);

                            db.updateHos_H_Pati(db.getIdHost(hosts.getValue()), Pat_id);



                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        delete.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {

                    int Pat_id = db.idPat(idPatienC.getValue());
                    System.out.println(db.getidPatInt(idPat));
                    db.deleteHos_H_Pat(idPat);
                    System.out.println(idPat);
                    db.deletePatients(String.valueOf(idPat));

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
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