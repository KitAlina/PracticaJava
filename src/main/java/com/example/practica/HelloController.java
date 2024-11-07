package com.example.practica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

// окно для клиентов
public class HelloController {

    @FXML
    private GridPane Ser;


    @FXML
    private BorderPane idBordP;

    @FXML
    private Button oNas;

    @FXML
    private Button vuhod;

    @FXML
    private Button zapis;


    @FXML
    private TextFlow TDiag;

    @FXML
    private TextFlow TLech;

    @FXML
    private TextFlow TNabl;

    @FXML
    private TextFlow Tosm;

    DB db = null;


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();
//      Заполним текстом поля с окзнакомительной информацией
        Text text1 = new Text("Ветеринар осматривает животное и оценивает его здоровье. Владельцу задаются вопросы о здоровье. Доктор определяет проблему и формирует тактику.");
        text1.setFill(Color.BLACK);
        text1.setFont(Font.font("Segoe Print", FontPosture.REGULAR, 9));
        Tosm.getChildren().add(text1);

        Text textD = new Text("Если необходимо ветврач берет анализы крови, направляет на УЗИ или другие исследования, которые нужны для постановки точного диагноза.");
        textD.setFill(Color.BLACK);
        textD.setFont(Font.font("Segoe Print", FontPosture.REGULAR, 9));
        TDiag.getChildren().add(textD);


        Text textL = new Text("Врач прописывает лечение или рекомендации по профилактике. Назначает время следующего приема, чтобы отследить динамику лечения животного.");
        textL.setFill(Color.BLACK);
        textL.setFont(Font.font("Segoe Print", FontPosture.REGULAR, 9));
        TLech.getChildren().add(textL);


        Text textN = new Text("После выздоровления продолжает наблюдать за питомцем и общаться с владельцем. Интересуется здоровьем в дальнейшем.");
        textN.setFill(Color.BLACK);
        textN.setFont(Font.font("Segoe Print", FontPosture.REGULAR, 9));
        TNabl.getChildren().add(textN);


//        GridPane
        ArrayList<String> ser = db.getService();
        ArrayList<String> des = db.getDes();
        ArrayList<Double> cost = db.getCost();
        ObservableList<String> data = FXCollections.observableArrayList(ser);

        Ser.getColumnConstraints().add(new ColumnConstraints(200));

        // Создаем первый столбец
        ColumnConstraints column1 = new ColumnConstraints();
        // column1.setPercentWidth(100);
        column1.setPercentWidth(60);
        // Установите нужную ширину столбца (в процентах)
        Ser.getColumnConstraints().add(column1);

        Ser.setStyle("-fx-font: 10px \"Segoe Print\";");
// Создаем второй столбец
        ColumnConstraints column2 = new ColumnConstraints();
//        column2.setMaxWidth(250);
        column2.setPercentWidth(30); // Установите нужную ширину столбца (в процентах)
        Ser.getColumnConstraints().add(column2);

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(25); // Установите нужную ширину столбца (в процентах)
        Ser.getColumnConstraints().add(column3);


// Заполняем первый столбец Услугами
        for (int i = 0; i < ser.size(); i++) {
            Label label = new Label(ser.get(i));
            Ser.add(label, 0, i + 1);
        }

// Заполняем второй столбец описания
        for (int i = 0; i < des.size(); i++) {
            Label label = new Label(des.get(i));
            Ser.add(label, 1, i + 1); // Добавляем изображение во второй столбец и i-тую строку
        }

        // Заполняем 3 столбец Ценой
        for (int i = 0; i < cost.size(); i++) {
            Label label = new Label(cost.get(i).toString());
            Ser.add(label, 2, i + 1); // Добавляем изображение во второй столбец и i-тую строку
        }

        idBordP.setLeft(new ScrollPane(Ser));


        oNas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "oNas.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("oNas.fxml"));
                    Stage stage = new Stage();
                    Image icon = new Image("C:/Users/malin/IdeaProjects/practica/src/main/resources/com/example/practica/photo/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    stage.setTitle("О нас");
                    stage.setScene(scene);
                    stage.show();

                    // Закрытие текущего окна
                    Stage currentStage = (Stage) vuhod.getScene().getWindow();
                    currentStage.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });


        vuhod.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                close();
            }
        });


        zapis.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "oNas.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("zapis.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Добавить запись на процедуру");
                    stage.setScene(scene);
                    stage.show();

                    // Закрытие текущего окна
                    Stage currentStage = (Stage) vuhod.getScene().getWindow();
                    currentStage.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });

    }


    @FXML
    public void close() {
        Stage stage = (Stage) vuhod.getScene().getWindow();
        stage.close();
    }

}