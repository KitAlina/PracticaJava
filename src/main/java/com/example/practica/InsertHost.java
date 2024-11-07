package com.example.practica;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class InsertHost {

    @FXML
    private TextField data;
    @FXML
    private TextField address;

    @FXML
    private TextField age;

    @FXML
    private TextField fio;

    @FXML
    private ImageView image;

    @FXML
    private TextField log;

    @FXML
    private TextField pass;

    @FXML
    private TextField phone;

    @FXML
    private Button sohr;

    @FXML
    private Button vuhod;

    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();

        Image ima = new Image("C:/Users/malin/IdeaProjects/practica/src/main/resources/com/example/practica/photo/host.png");
        image.setImage(ima);
        sohr.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    System.out.println();
                    db.insertUserH(log.getText(), pass.getText());
                    db.insertH(fio.getText(), Integer.parseInt(age.getText()), data.getText(),  phone.getText(), address.getText() );
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