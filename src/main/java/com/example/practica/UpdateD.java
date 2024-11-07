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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateD {

    @FXML
    private TextField address;

    @FXML
    private TextField data;

    @FXML
    private ComboBox<String> doctors;

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
    private ListView<String> service;

    @FXML
    private Button sohr;

    @FXML
    private Button vuhod;

    @FXML
    private Button delete;
    public int usid_doc;
    public int Doc;

    ArrayList<Integer> initiallySelectedItems = new ArrayList<>();
    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();
        Image ima = new Image("C:/Users/malin/IdeaProjects/practica/src/main/resources/com/example/practica/photo/veterinarian.png");
        image.setImage(ima);

        doctors.getItems().addAll(db.getDoctors());

        doctors.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Doc = db.getIdDoc(doctors.getValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                fio.setText(db.getOneFioDoctor(Doc));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                data.setText(String.valueOf(db.getOneDataDoctor(Doc)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                address.setText(String.valueOf(db.getOneAddressDoctor(Doc)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                phone.setText(db.getOnePhoneDoctor(Doc));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                usid_doc = db.getUsersIDDoc(doctors.getValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                log.setText(db.getOnelogUser(usid_doc));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                pass.setText(db.getOnePassUser(usid_doc));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });

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
                    int idDoc = db.getIdDoc(doctors.getValue());
                    int n = Math.toIntExact(service.getSelectionModel().getSelectedItems().stream().count());
                    db.updateUser(log.getText(), pass.getText(), usid_doc);
                    db.updateDoctors(fio.getText(), address.getText(), Date.valueOf(data.getText()), phone.getText(), idDoc);



                    for (int i = 0; i < n; i++) {
                        String selected = String.valueOf(service.getSelectionModel().getSelectedItems().get(i));
                        db.updateSer_h_Doc(db.getIDService(selected), Doc);
                        System.out.println(Doc);
                        System.out.println(db.getIDService(selected));
                    }
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
                    System.out.println();
                    int idDoc = db.getIdDoc(doctors.getValue());
                    System.out.println(idDoc);
                    if (Doc == idDoc) {

                        db.deleteUserD(String.valueOf(usid_doc));
                        db.deleteDoc(String.valueOf(idDoc));

                        db.deleteSer_h_D(String.valueOf(Doc));
                        System.out.println(Doc);

                    }


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



