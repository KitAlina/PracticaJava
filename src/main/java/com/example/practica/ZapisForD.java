package com.example.practica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class ZapisForD {
    @FXML
    private Button OkazSer;

    @FXML
    private TableColumn<SomeObject, Double> costColumn;


    @FXML
    private ComboBox<String> day;


    @FXML
    private ComboBox<String> patients;

    @FXML
    private Button pos;

    @FXML
    private TableColumn<SomeObject, String> serviceColumn;

    @FXML
    private ComboBox<String> services;


    @FXML
    private ComboBox<Time> time;

    @FXML
    private Button vuhod;

    @FXML
    private Button zapisDob;

    @FXML
    private TableView<SomeObject> zapisSer;

    DB db = null;
    ArrayList<Integer> idCalendar1 = new ArrayList<>();
    ArrayList<String> dayS1 = new ArrayList<>();
    ArrayList<Time> timeA1 = new ArrayList<>();

    public String selectedObject1;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();

        services.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedObject1 = String.valueOf(services.getSelectionModel().getSelectedItem());
            int idSZ = 0;
            try {
                idSZ = db.IdSerZ(String.valueOf(selectedObject1));
                System.out.println(selectedObject1);
                System.out.println(db.IdSerZ(String.valueOf(selectedObject1)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println("idsz" + idSZ);

            ArrayList<Integer> idDZ = new ArrayList<>();
            try {
                idDZ = db.idDocZ(idSZ);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < idDZ.size(); i++) {
                String idC = "";
                try {
                    idC = String.valueOf(db.idcalendar(idDZ.get(i)));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                idC = idC.substring(1, idC.length() - 1);
                String[] idArray = idC.split(", ");
                System.out.println("dhtvz" + idArray);
                for (String id : idArray) {
                    idCalendar1.add(Integer.valueOf(id));
                }
            }

            System.out.println("idcalen" + idCalendar1);

            for (int i = 0; i < idCalendar1.size(); i++) {
                String day = "";
                try {
                    day = String.valueOf(db.idday(idCalendar1.get(i)));
                    System.out.println("day" + day);
                    if (!dayS1.contains(day)) {
                        dayS1.add(day);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            if (day.getItems().isEmpty()) {
                day.getItems().addAll(dayS1);
            }

            for (int i = 0; i < idCalendar1.size(); i++) {
                String time = "";
                try {
                    time = String.valueOf(db.idtime(idCalendar1.get(i)));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(time);
                timeA1.add(Time.valueOf(time));
            }

            if (time.getItems().isEmpty()) {
                time.getItems().addAll(timeA1);
            }
        });

        patients.getItems().addAll(db.NamePat());

        patients.setStyle("-fx-font: 12px \"Segoe Print\";");
        services.setStyle("-fx-font: 12px \"Segoe Print\";");

        services.getItems().addAll(db.getService());

        ArrayList<String> ser = db.getService();
        ArrayList<Double> cost = db.getCost();
        ObservableList<SomeObject> data = FXCollections.observableArrayList();

        for (int i = 0; i < ser.size(); i++) {
            data.add(new SomeObject(ser.get(i), cost.get(i)));
        }

        serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        zapisSer.setItems(data);

        services.getItems().addAll(ser);

        pos.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (services.getValue() != null) {
                    zapisSer.getItems().clear();
                    ArrayList<String> ser1 = null;
                    try {
                        ser1 = db.getService_V(services.getValue());

                        ArrayList<Double> cost1 = db.getCost_V(services.getValue());
                        ObservableList<SomeObject> data1 = FXCollections.observableArrayList();

                        for (int i = 0; i < ser1.size(); i++) {
                            data1.add(new SomeObject(ser1.get(i), cost1.get(i)));
                        }

                        serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
                        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

                        zapisSer.setItems(data1);

                        SomeObject selectedObject = zapisSer.getSelectionModel().getSelectedItem();
                        if (selectedObject != null) {
                            System.out.println(selectedObject.getId());
                        } else {
                            System.out.println("No item selected");
                        }


                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        zapisDob.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    String p = patients.getValue();
//                 db.getUsersID(logS, SpassF);
                    int SC = db.IdSerZ(services.getValue());
                    int SO = db.IdSerZ(selectedObject1);

                    System.out.println("Sc" + SC);
                    System.out.println("So" + SO);
                    System.out.println(db.getIdCal(day.getValue(), time.getValue()));
                    if (p != null) {
                        if (SC == SO) {
                            db.insertS_h_P(Integer.parseInt(String.valueOf(db.idPat(patients.getValue()))), SC, db.getIdCal(day.getValue(), time.getValue()));
                        } else if (SC == 0) {
                            System.out.println("No item selected cb");
                        }
                    } else {
                        System.out.println("Не выбран питомец!");
                    }
//


                } catch (Exception e) {
                    String x = e.getMessage();
                    showMessage("Ошибка", "" + x);
                    throw new RuntimeException(e);
                }
            }
        });


        OkazSer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("okazSerD.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 700, 500);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/practica/src/main/resources/com/example/practica/photo/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("История оказанных услуг");
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
        Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");    // Установка иконки для главного окна приложения
        stage.getIcons().add(icon);
        alert.setContentText(content);
        alert.showAndWait();
    }
}



