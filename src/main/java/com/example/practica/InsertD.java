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
import javafx.scene.SnapshotResult;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsertD {

    @FXML
    private TextField address;

    @FXML
    private TextField data;

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
    private ListView<String> service;

    @FXML
    private Button vuhod;
    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();
        Image ima = new Image("C:/Users/malin/IdeaProjects/practica/src/main/resources/com/example/practica/photo/veterinarian.png");
        image.setImage(ima);

        ArrayList<String> selectedItems = new ArrayList<>();
        ObservableList<String> langs = FXCollections.observableArrayList(db.getService());
        service.setItems(langs);
        // получаем модель выбора элементов
        MultipleSelectionModel<String> langsSelectionModel = service.getSelectionModel();
        langsSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        // устанавливаем слушатель для отслеживания изменений
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue) {

                ObservableList<String> selected = langsSelectionModel.getSelectedItems();
                selectedItems.add(selected.toString());
                System.out.println(selectedItems);
            }
        });

        sohr.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    System.out.println();
                    int n = Math.toIntExact(service.getSelectionModel().getSelectedItems().stream().count());
                    db.insertUserD(log.getText(), pass.getText());
                    db.insertD(fio.getText(), address.getText(), data.getText(), phone.getText());


                    for (int i = 0; i < n; i++){
                        String selected = String.valueOf(service.getSelectionModel().getSelectedItems().get(i));
                        db.insertSer_h_Doc(db.getIDService(selected), db.getMaxIdD());
                        System.out.println(db.getMaxIdD());
                        System.out.println(db.getIDService(selected));
                    }
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




