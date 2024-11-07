package com.example.practica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("author.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 440);
        Image icon = new Image("C:/Users/malin/IdeaProjects/practica/src/main/resources/com/example/practica/photo/pet-house.png");// Установка иконки для главного окна приложения
        stage.getIcons().add(icon);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}