package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.view.AppController;
import org.example.view.Scenes;
import org.example.view.Controller;
import org.example.view.View;

import java.io.IOException;
import java.util.Objects;
/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static Stage stage;
    public static AppController currentController;

    //este es el primer método que se ejecuta al abrir la primera ventana
    @Override
    public void start(Stage stage) throws IOException {
        //view/layout.fxml
        View view = AppController.loadFXML(Scenes.ROOT);
        scene = new Scene(view.scene, 900, 600);
        currentController = (AppController) view.controller;
        currentController.onOpen(null);
        stage.setTitle("G3D");
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("images/LogoG3DMedio.png"))));
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(900);
        stage.setMaxHeight(600);
        stage.setMaxWidth(900);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        //  scene.setRoot(loadFXML(fxml));
    }


    public static void main(String[] args) {
        launch();
    }

}