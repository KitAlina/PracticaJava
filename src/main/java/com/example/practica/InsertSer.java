package com.example.practica;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsertSer {

    @FXML
    private TextField cost;

    @FXML
    private TextField descr;


    @FXML
    private ImageView image;

    @FXML
    private ComboBox<String> nameSpec;

    @FXML
    private Button sohr;

    @FXML
    private TextField titleSer;
    @FXML
    private Button vuhod;


    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException{
        nameSpec.setStyle("-fx-font: 12px \"Segoe Print\";");

        db = new DB();
        ArrayList<String> selectedItems = new ArrayList<>();
        nameSpec.getItems().addAll(db.getSpec());


        sohr.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try{

                    db.insertSer(titleSer.getText(), Double.parseDouble(cost.getText()), descr.getText(), Integer.parseInt(String.valueOf(db.getIdSpec(nameSpec.getValue()))));
                    System.out.println(db.getMaxSer());

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
