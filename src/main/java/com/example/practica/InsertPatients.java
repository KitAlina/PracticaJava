package com.example.practica;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsertPatients {

    @FXML
    private TextField age;

    @FXML
    private TextField breed;

    @FXML
    private TextField fio;

    @FXML
    private ComboBox<String> hosts;

    @FXML
    private ImageView image;

    @FXML
    private Button sohr;

    @FXML
    private TextField type_of_animal;

    @FXML
    private Button vuhod;


    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();
//        Image ima = new Image("C:/Users/malin/IdeaProjects/vetCl/src/veterinarian.png");
//        image.setImage(ima);
        hosts.setStyle("-fx-font: 12px \"Segoe Print\";");

        db = new DB();
        ArrayList<String> selectedItems = new ArrayList<>();
        hosts.getItems().addAll(db.getHosts());


        sohr.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {

                    // Метод, что будет срабатывать
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            db.insertPat_Procedura(db.getMaxPat1(),fio.getText(), Integer.parseInt(age.getText()), type_of_animal.getText(), breed.getText());
                            System.out.println(db.getMaxPat1());
                            db.insertHosts_has_Pat(Integer.parseInt(String.valueOf(db.getIdHost(hosts.getValue()))), db.getMaxPat());
                            System.out.println(db.getIdHost(hosts.getValue()));

                        } catch (SQLException e) {
                            String x = e.getMessage();
                            showMessage("Ошибка", "" + x);
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
    private void showMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        Stage stage = (Stage)
                alert.getDialogPane().getScene().getWindow();
        Image icon = new Image("C:/Users/malin/IdeaProjects/practica/src/main/resources/com/example/practica/photo/pet-house.png");    // Установка иконки для главного окна приложения
        stage.getIcons().add(icon);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

