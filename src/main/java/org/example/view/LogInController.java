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

/**
 * Controller class for handling user login functionality.
 */
public class LogInController extends Controller implements Initializable {
    @FXML
    public ChoiceBox<String> choiceBox = new ChoiceBox<>();

    @FXML
    public TextField userText;

    @FXML
    public TextField passwordText;

    /**
     * Initializer for some aspects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Change the scene to START when the controller is opened.
     */
    @Override
    public void onOpen(Object input) throws IOException {
        choiceBox.getItems().add("Modeler");
        choiceBox.getItems().add("Client");
    }

    /**
     * Do something when the controller is going to close.
     */
    @Override
    public void onClose(Object output) {

    }

    /**
     * Attempts to log in the user based on the provided credentials and user type.
     *
     * @return True if the login was successful, false otherwise.
     * @throws IOException              If an error occurs during view change.
     * @throws NoSuchAlgorithmException If the hashing algorithm is not available.
     */
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

    /**
     * Handles the login action triggered by the user.
     *
     * @throws IOException              If an error occurs during view change.
     * @throws NoSuchAlgorithmException If the hashing algorithm is not available.
     */
    public void onActionLogIn() throws IOException, NoSuchAlgorithmException {
        if (logIn()) {
            if (choiceBox.getValue().equals("Modeler")) {
                App.currentController.changeScene(Scenes.MODELERHOME, null);
            } else if (choiceBox.getValue().equals("Client")) {
                App.currentController.changeScene(Scenes.CLIENTHOME, null);
            }
        }
    }

    /**
     * Navigates back to the start screen.
     *
     * @throws IOException If an error occurs during view change.
     */
    public void goBack() throws IOException {
        App.currentController.changeScene(Scenes.START, null);
    }
}