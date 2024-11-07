package com.example.practica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class OkazSerD {
    @FXML
    private TableView<SomeObject> OkazSer;

    @FXML
    private TableColumn<SomeObject, Double> costColumn;

    @FXML
    private TextField idpatCard;


    @FXML
    private Button pos;

    @FXML
    private TableColumn<SomeObject, String> serviceColumn;

    @FXML
    private Button vuhod;


    DB db = null;


    ArrayList<String> SerP = new ArrayList<>();
    ArrayList<Double> Cost = new ArrayList<>();
    ObservableList<SomeObject> data = FXCollections.observableArrayList();


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DB();


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

        pos.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                OkazSer.getItems().clear(); // очищаем TableView от предыдущих данных
                data.clear(); // очищаем массив данных


                    ArrayList<Integer> serId = null;
                    try {
                        serId = db.getidSer(Integer.parseInt(idpatCard.getText()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("serId" + serId);

                    for (int i = 0; i < serId.size(); i++) {
                        String x = null;
                        try {
                            x = String.valueOf(db.getServiceP(serId.get(i)));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("x" + x);
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
                        System.out.println(x1);
                        Cost.add((x1));
                    }

                // затем добавляем новые данные в массив и отображаем их в TableView
                for (int i = 0; i < SerP.size(); i++) {
                    data.add(new SomeObject(SerP.get(i), Cost.get(i)));
                }


                serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
                    costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

                OkazSer.setItems(data);


            }


        });



    }
}
