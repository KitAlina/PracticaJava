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

public class UpdateHost {
    @FXML
    private TextField address;

    @FXML
    private TextField age;

    @FXML
    private TextField data;
    @FXML
    private Button delete;

    @FXML
    private TextField fio;

    @FXML
    private ComboBox<String> hosts;

    @FXML
    private ImageView image;

    @FXML
    private TextField log;

    @FXML
    private TextField pass;

    @FXML
    private ListView<String> patiHUp;

    @FXML
    private TextField phone;

    @FXML
    private Button sohr;
    @FXML
    private Button vuhod;
    public int Hos;
    public int usid_Hos;
    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();

        Image ima = new Image("C:/Users/malin/IdeaProjects/practica/src/main/resources/com/example/practica/photo/host.png");
        image.setImage(ima);
        hosts.getItems().addAll(db.getHosts());
        hosts.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Hos = db.getIdHost(hosts.getValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                fio.setText(db.getOneFioHosts(Hos));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                age.setText(String.valueOf(db.getOneAgeHosts(Hos)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                data.setText(String.valueOf(db.getOneDataHosts(Hos)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                address.setText(String.valueOf(db.getOneAddressHosts(Hos)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                phone.setText(db.getOnePhoneHosts(Hos));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                usid_Hos = db.getUsersIDHos(hosts.getValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                log.setText(db.getOnelogUser(usid_Hos));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                pass.setText(db.getOnePassUser(usid_Hos));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });

        ArrayList<String> selectedItems = new ArrayList<>();
        ObservableList<String> langs = FXCollections.observableArrayList(db.getPatients());
        patiHUp.setItems(langs);
        // получаем модель выбора элементов
        MultipleSelectionModel<String> langsSelectionModel = patiHUp.getSelectionModel();
        langsSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        // устанавливаем слушатель для отслеживания изменений
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue) {

                ObservableList<String> selected = langsSelectionModel.getSelectedItems();
                selectedItems.add(selected.toString());
                System.out.println(selectedItems);
            }
        });

        delete.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println();
                int Host_id = 0;
                try {
                    Host_id = db.getIdHost(hosts.getValue());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                int n = Math.toIntExact(patiHUp.getSelectionModel().getSelectedItems().stream().count());

                try {
                    db.deleteHost(String.valueOf(Host_id));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                try {
                    db.deleteUserD(String.valueOf(usid_Hos));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                try {
                    db.geleteHos_H_Pat(Host_id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        sohr.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    System.out.println();
                    int Host_id = db.getIdHost(hosts.getValue());
                    int n = Math.toIntExact(patiHUp.getSelectionModel().getSelectedItems().stream().count());
                    db.updateUser(log.getText(), pass.getText(), usid_Hos);
                    db.updateHosts(fio.getText(), Integer.parseInt(age.getText()), Date.valueOf(data.getText()), phone.getText(), address.getText(), Host_id);


                    for (int i = 0; i < n; i++) {
                        String selected = String.valueOf(patiHUp.getSelectionModel().getSelectedItems().get(i));
                        db.updateHos_H_Pati(Hos, db.idPat(selected));
                        System.out.println(Hos);
                        System.out.println(db.idPat(selected));
                        System.out.println();
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



