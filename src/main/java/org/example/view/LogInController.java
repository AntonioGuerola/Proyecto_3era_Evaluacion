package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.model.dao.UserDAO;
import org.example.model.entity.Client;
import org.example.model.entity.Modeler;
import org.example.model.entity.User;
import org.example.model.singleton.clientSingleton;
import org.example.model.singleton.modelerSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class LogInController extends Controller implements Initializable {

    @FXML
    public ChoiceBox<String> choiceBox = new ChoiceBox<>();

    @FXML
    public TextField userText;

    @FXML
    public TextField passwordText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
        choiceBox.getItems().add("Modeler");
        choiceBox.getItems().add("Client");
    }

    @Override
    public void onClose(Object output) {

    }

    @FXML
    public boolean logIn() throws IOException, NoSuchAlgorithmException {
        boolean result = false;
        User userToLogin = new User(userText.getText(), JavaFXUtils.hashPassword(passwordText.getText()), "", "", "", null, null);
        if (choiceBox.getValue().equals("Modeler")) {
            Modeler modelerFromDataBase = (Modeler) UserDAO.buildModeler().findUserByUser(userToLogin.getUser());
            if (modelerFromDataBase != null) {
                if (modelerFromDataBase.getUser().equals(userToLogin.getUser())) {
                    if (modelerFromDataBase.getPassword() != null && modelerFromDataBase.getPassword().equals(userToLogin.getPassword())) {
                        modelerSingleton.getInstance(modelerFromDataBase);
                        result = true;
                    } else {
                        JavaFXUtils.showErrorAlert("FAILED TO LOGIN", "Password incorrect");
                    }
                }
            } else {
                JavaFXUtils.showErrorAlert("FAILED TO LOGIN", "There is any modeler with this username");
            }
        } else if (choiceBox.getValue().equals("Client")) {
            Client clientFromDataBase = (Client) UserDAO.buildClient().findUserByUser(userToLogin.getUser());
            if (clientFromDataBase != null) {
                if (clientFromDataBase.getUser().equals(userToLogin.getUser())) {
                    if (clientFromDataBase.getPassword() != null && clientFromDataBase.getPassword().equals(userToLogin.getPassword())) {
                        clientSingleton.getInstance(clientFromDataBase);
                        result = true;
                    } else {
                        JavaFXUtils.showErrorAlert("FAILED TO LOGIN", "Password incorrect");
                    }
                }
            } else {
                JavaFXUtils.showErrorAlert("FAILED TO LOGIN", "There is any client with this username");
            }
        }
        return result;
    }

    public void onActionLogIn() throws IOException, NoSuchAlgorithmException {
        if (logIn()) {
            if (choiceBox.getValue().equals("Modeler")) {
                App.currentController.changeScene(Scenes.MODELERHOME, null);
            } else if (choiceBox.getValue().equals("Client")) {
                App.currentController.changeScene(Scenes.CLIENTHOME, null);
            }
        }
    }

    public void goBack() throws IOException {
        App.currentController.changeScene(Scenes.START, null);
    }
}