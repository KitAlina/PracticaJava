package com.example.practica;

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

public class EditingSer {

    @FXML
    private ComboBox<String> ServicCom;

    @FXML
    private TextField cost;

    @FXML
    private Button delete;

    @FXML
    private TextField desc;


    @FXML
    private ComboBox<String> nameSpec;

    @FXML
    private Button sohr;

    @FXML
    private TextField titleSer;

    @FXML
    private Button vuhod;

    public int Ser;
    public int idSer;
    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException{
        nameSpec.setStyle("-fx-font: 12px \"Segoe Print\";");

        db = new DB();
        ArrayList<String> selectedItems = new ArrayList<>();
        nameSpec.getItems().addAll(db.getSpec());
        ServicCom.getItems().addAll(db.getService());



        ServicCom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Ser = db.getIDService(ServicCom.getValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                titleSer.setText(db.getOneTitleSer(Ser));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                desc.setText(String.valueOf(db.getOneDesc(Ser)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                cost.setText(String.valueOf(db.getOneCost(Ser)));
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

                try{


                        idSer = db.getIDService(ServicCom.getValue());
                        System.out.println(idSer);
                        db.updateSer(titleSer.getText(), Double.parseDouble(cost.getText()), desc.getText(), Integer.parseInt(String.valueOf(db.getIdSpec(nameSpec.getValue()))), idSer);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        });

        delete.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {

                   int idSer1 = db.getIDService(ServicCom.getValue());
                    db.deleteSer(String.valueOf(idSer1));

                        db.deleteSer_h_DS(String.valueOf(idSer));
                        System.out.println(idSer);


                }catch (SQLException e) {
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


