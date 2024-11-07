package com.example.practica;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class Author {

     @FXML
     private CheckBox check;

     @FXML
     private TextField log;

     @FXML
     private TextField pass;

     @FXML
     private PasswordField passF;

     @FXML
     private ImageView image;
     @FXML
     private Button vhod;
     private int a;
     DB db = null;

    String logS;
    String SpassF;
     @FXML
     void initialize() throws SQLException, ClassNotFoundException {
//          подгружаем фото логотип клиники
          Image ima = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");
          image.setImage(ima);
          // Инициируем объект

          db = new DB();


     vhod.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
          // Метод, что будет срабатывать
          @Override
          public void handle(MouseEvent mouseEvent) {
               try {
                    logS = log.getText();
                    SpassF = passF.getText();

                    if (check.isSelected()) {
                         passF.setText(pass.getText());
                    }
                    if (!log.getText().trim().equals("") & !passF.getText().trim().equals("")) {
                         int n = db.getUsersC(log.getText(), passF.getText());
                         if(n!=0){
                              if (n == 1) {
                                   System.out.println("Вы ввели верный пароль");
                                   FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml")); //для клиентов и менеджера
                                   Scene scene = new Scene(fxmlLoader.load(), 1278, 479);
                                   Stage stage = new Stage();
                                   Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                                   stage.getIcons().add(icon);
                                   stage.setTitle("Личный кабинет клиента");
                                   stage.setScene(scene);
                                   stage.show();

                         } else if (n == 2) {
                                   System.out.println("Вы ввели верный пароль");
                                   FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("helloDoctor.fxml")); //для клиентов и менеджера
                                   Scene scene = new Scene(fxmlLoader.load(), 650, 400);
                                   Stage stage = new Stage();
                                   Image icon1 = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                                   stage.getIcons().add(icon1);
                                   stage.setTitle("Личный кабинет доктора");
                                   stage.setScene(scene);
                                   stage.show();
                              }
                         } else if (n == 0) {
                              System.out.println("Вы ввели не верный пароль");
                        }
                    }
               } catch (Exception e) {
                    e.printStackTrace();
               }
          }
     });


}


     @FXML
     void chenge(){
          if(check.isSelected()){
               pass.setText(passF.getText());
               pass.setVisible(true);
               passF.setVisible(false);
               return;
          }
          passF.setText(pass.getText());
          pass.setVisible(false);
          passF.setVisible(true);
     }
}

