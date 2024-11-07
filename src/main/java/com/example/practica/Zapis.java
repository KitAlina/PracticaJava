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
import java.time.LocalDate;
import java.util.ArrayList;

public class Zapis {

    @FXML
    private Button OkazSer;

    @FXML
    private ComboBox<String> patients;

    @FXML
    private ComboBox<String> services;

    @FXML
    private Button pos;

    @FXML
    private Button vuhod;

    @FXML
    private Button zapisDob;

    @FXML
    private ComboBox<String> day;
    @FXML
    private ComboBox<Time> time;

    @FXML
    private TableView<SomeObject> zapisSer;

    @FXML
    private TableColumn<SomeObject, String> serviceColumn;

    @FXML
    private TableColumn<SomeObject, Double> costColumn;

    DB db = null;
    ArrayList<String> NamePat = new ArrayList<>();
    ArrayList<Integer> idCalendar = new ArrayList<>();
    ArrayList<String> dayS = new ArrayList<>();
    ArrayList<Time> timeA = new ArrayList<>();
    public String selectedObject;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();
//устанавливаем слушатель на услги
        services.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedObject = String.valueOf(services.getSelectionModel().getSelectedItem());
            //обявляем переменную в которую будем записывать id услуги
            int idSZ = 0;
            try {
                idSZ = db.IdSerZ(String.valueOf(selectedObject));
                System.out.println(selectedObject);
                System.out.println(db.IdSerZ(String.valueOf(selectedObject)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println("idsz" + idSZ);
            //обявляем массив, в котором будут содержаться id докторов, которые проводят выбранную услуг
            ArrayList<Integer> idDZ = new ArrayList<>();
            try {
                idDZ = db.idDocZ(idSZ);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            //проходимся по массиву id докторов и находим id календаря с нужными докторами
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
                    //записываем в массив idCalendar
                    idCalendar.add(Integer.valueOf(id));
                }
            }

            System.out.println("idcalen" + idCalendar);
//проходим по массиву idCalendar и находим даты подходящие по id календаря, выбираяем только уникальные значения
            for (int i = 0; i < idCalendar.size(); i++) {
                String day = "";
                try {
                    day = db.idday(idCalendar.get(i));
                    System.out.println("day" + day);
                    if (!dayS.contains(day)) {
                        //записываем в массив dayS
                        dayS.add(day);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
//передаем полученные значения в comboBox
            if (day.getItems().isEmpty()) {
                day.getItems().addAll(dayS);
            }
//проходим по массиву idCalendar и находим время подходящие по id календаря, выбираяем только уникальные значения
            for (int i = 0; i < idCalendar.size(); i++) {
                String time = "";
                try {
                    time = String.valueOf(db.idtime(idCalendar.get(i)));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                //записываем в массив timeA
                System.out.println(time);
                timeA.add(Time.valueOf(time));
            }
//передаем полученные значения в comboBox
            if (time.getItems().isEmpty()) {
                time.getItems().addAll(timeA);
            }
        });


        System.out.println(db.getID_Us_for_Host());
        int idUser = db.getID_Us_for_Host();

        int idHost = db.getIDHostZ(idUser);
        System.out.println(db.getIDHostZ(idUser));

        ArrayList<Integer> idPat = db.getidPat(idHost);
        System.out.println(idPat);

        for (int i = 0; i < idPat.size(); i++) {
            String x = String.valueOf(db.getName_Pat(idPat.get(i)));
            System.out.println(x);
            x = x.substring(1, x.length() - 1);
            NamePat.add(x);
        }
//        ArrayList<String> NamePat = db.getName_Pat(idPat);

        patients.getItems().addAll(NamePat);

        db.getIDHostZ(idUser);

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
                    int SO = db.IdSerZ(selectedObject);

                    System.out.println("Sc" + SC);
                    System.out.println("So" + SO);

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


                } catch (SQLException e) {
                    String x = e.getMessage();
                    showMessage("Ошибка", "" + x);
                } catch (ClassNotFoundException e) {
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
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("okazSerH.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 700, 500);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
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


    private void showMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        Stage stage = (Stage)
                alert.getDialogPane().getScene().getWindow();
        Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");    // Установка иконки для главного окна приложения
        stage.getIcons().add(icon);
        alert.setContentText(content);
        alert.showAndWait();}
    }


