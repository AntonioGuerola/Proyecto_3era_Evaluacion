package org.example.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.example.App;

public class StartController extends Controller implements Initializable {
    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void toSignIn() throws IOException {
        App.currentController.changeScene(Scenes.SIGNIN, null);
    }

    @FXML
    private void toLogIn() throws IOException {
        App.currentController.changeScene(Scenes.LOGIN, null);
    }

    @FXML
    private void close() throws IOException {
        System.exit(0);
    }
}