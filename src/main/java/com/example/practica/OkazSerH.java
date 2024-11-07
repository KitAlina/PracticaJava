package com.example.practica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class OkazSerH {

    @FXML
    private ComboBox<String> patients;

    @FXML
    private Button pos;

    @FXML
    private TableView<SomeObject> OkazSer;
    @FXML
    private TableColumn<SomeObject, Double> costColumn;
    @FXML
    private TableColumn<SomeObject, String> serviceColumn;



    @FXML
    private Button vuhod;


    DB db = null;
    ArrayList<String> NamePat = new ArrayList<>();

    ArrayList<String> SerP = new ArrayList<>();
    ArrayList<Double> Cost = new ArrayList<>();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();


        System.out.println(db.getID_Us_for_Host());
        int idUser = db.getID_Us_for_Host();

        int idHost = db.getIDHostZ(idUser);
        System.out.println(db.getIDHostZ(idUser));

        ArrayList<Integer> idPat = db.getidPat(idHost);
        System.out.println(idPat);

        for(int i=0;i<idPat.size();i++){
            String x = String.valueOf(db.getName_Pat(idPat.get(i)));
            System.out.println(x);
            x=x.substring(1, x.length()-1);
            NamePat.add(x);
        }

        patients.getItems().addAll(NamePat);

        patients.setStyle("-fx-font: 12px \"Segoe Print\";");

       ArrayList<String> ser = db.getService();

pos.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent mouseEvent) {
        ObservableList<SomeObject> data = FXCollections.observableArrayList();

        int Pat = 0;
        try {
            Pat = db.idPat(patients.getValue());
            System.out.println("Pat"+Pat);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //по айди питомца находим айди услуги
        ArrayList<Integer> serId = null;
        try {
            serId = db.getidSer(Pat);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("serId"+serId);
//проходимся по массиву с айди услуг и получаем названия
        for (int i = 0; i < serId.size(); i++) {
            String x = null;
            try {
                x = String.valueOf(db.getServiceP(serId.get(i)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            //записываем в новый массив
            System.out.println("x"+x);
            x = x.substring(1, x.length() - 1);
            SerP.add(x);
        }

        for (int i = 0; i < serId.size(); i++) {
            Double x1 = null;
            try {
                x1 = Double.valueOf(String.valueOf(db.getCostH(serId.get(i))).replace("[", "").replace("]", ""));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            //записываем полученный массив в новый
            System.out.println(x1);
            Cost.add((x1));
        }
        //проходимся по массиву с названиями услуг и записваем данные из массивов с услугами и ценой в data, который передаем в TableView

        for (int i = 0; i < SerP.size(); i++) {
            data.add(new SomeObject(SerP.get(i), Cost.get(i)));
        }

        serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        OkazSer.setItems(data);



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
    }






