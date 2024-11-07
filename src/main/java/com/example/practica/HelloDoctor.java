package com.example.practica;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloDoctor {

    @FXML
    private Button dobDoct;

    @FXML
    private Button dobPat;

    @FXML
    private Button dobSer;

    @FXML
    private Button dobSpes;

    @FXML
    private Button dobUs;

    @FXML
    private Button zap;

    @FXML
    private Button DobTime;

    @FXML
    private Button vuhod;
    @FXML
    private Button updateDoc;
    @FXML
    private ImageView image;

    @FXML
    private Button editingHos;

    @FXML
    private Button editingSer;

    @FXML
    private Button editingTime;
    @FXML
    private Button editingPat;
    DB db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        Image ima = new Image("C:/Users/malin/IdeaProjects/practica/src/main/resources/com/example/practica/photo/stethoscope.png");
        image.setImage(ima);
//
        // Инициируем объект
        db = new DB();



        zap.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("zapisForD.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 700, 500);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Добавить запись на услугу");
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
        dobDoct.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("insertD.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Добавить доктора");
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

        editingSer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editingSer.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Редактирование услуг");
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

        editingPat.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editingPatients.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Редактирование данных о пациентах");
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

        editingTime.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editingTime.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Редактирование расписания");
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

        editingHos.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateHost.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Редактирование данные о клиентах");
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


       updateDoc.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateD.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Редактирование докторов");
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

        dobUs.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("insertHost.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Добавить клиента");
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


        dobSer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("insertSer.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Добавить услугу");
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


        dobPat.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("insertPatients.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Добавить пациента");
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

        dobSpes.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("insertSpec.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Добавить специализацию");
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

        DobTime.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    // Загрузка нового FXML-файла "author.fxml"
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("insertTime.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 663, 454);
                    Image icon = new Image("C:/Users/malin/IdeaProjects/vetCl/src/pet-house.png");// Установка иконки для главного окна приложения
                    stage.getIcons().add(icon);
                    stage.setTitle("Добавить расписание");
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("author.fxml"));
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



