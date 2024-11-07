package com.example.practica;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ONas {
    @FXML
    private TextFlow OnasText;

    @FXML
    private Button vuhod;

    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
//        Image iman = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");
//        image.setImage(iman);

        //      Заполним текстом поля с окзнакомительной информацией
        Text text1 = new Text("Сеть ветеринарных клиник ВетЛюкс уже готова открыть свои двери для вас и ваших четвероногих друзей. Для вашего удобства у нас есть два филиала - в нижней и верхней частях Нижнего Новгорода.\n" +
                "\n" +
                "Ветеринарный центр предоставляет полный спектр ветеринарных услуг для всех видов мелких домашних животных.\n" +
                "\n" +
                "Официальный сайт ветклиники ВетЛюкс информирует клиентов не только об услугах и ценах, но и о новостях и акциях клиники.\n" +
                "\n" +
                "Мы любим животных и выступаем за гуманное отношение к ним! Давайте заботиться об их здоровье вместе!");
        text1.setFill(Color.BLACK);
        text1.setFont(Font.font("Segoe Print", FontPosture.REGULAR, 9));
        OnasText.getChildren().add(text1);
        // Инициируем объект
        db = new DB();
        vuhod.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Получаем ссылку на текущее окно
                Stage currentStage = (Stage) vuhod.getScene().getWindow();

                // Получаем ссылку на предыдущее окно
                FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
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


