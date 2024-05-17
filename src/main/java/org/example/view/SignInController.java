package org.example.view;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.model.dao.UserDAO;
import org.example.model.entity.Client;
import org.example.model.entity.Modeler;
import org.example.model.singleton.clientSingleton;
import org.example.model.singleton.modelerSingleton;
import org.example.model.utils.JavaFXUtils;

public class SignInController extends Controller implements Initializable {

    @FXML
    private TextField nameText;

    @FXML
    private TextField surnamesText;

    @FXML
    private TextField userText;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField confirmPasswordText;

    @FXML
    private TextField emailText;

    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();

    @FXML
    private DatePicker bornDatePicker;


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
    public void saveUser() throws IOException, NoSuchAlgorithmException {
        if (choiceBox.getValue().equals("Modeler")) {
            if (passwordText.getText().length() < 16){
                if (passwordText.getText().equals(confirmPasswordText.getText())) {
                    Modeler modelerToSignIn = new Modeler(userText.getText(), JavaFXUtils.hashPassword(passwordText.getText()), nameText.getText(), surnamesText.getText(), emailText.getText(), bornDatePicker.getValue(), "images/user.jpg");
                    if (UserDAO.buildModeler().findUserByUser(modelerToSignIn.getUser()) == null) {
                        if (JavaFXUtils.validateEmail(emailText.getText())){
                            if (UserDAO.buildModeler().findUserByEmail(modelerToSignIn.getEmail()) == null){
                                UserDAO.buildModeler().save(modelerToSignIn);
                                modelerSingleton.getInstance(modelerToSignIn);
                                App.currentController.changeScene(Scenes.START, null);
                            }else{
                                JavaFXUtils.showErrorAlert("ERROR CREATING MODELER", "Modeler not created because Email is in use");
                            }
                        }else{
                            JavaFXUtils.showErrorAlert("ERROR CREATING MODELER", "Invalid email");
                        }
                    }else {
                        JavaFXUtils.showErrorAlert("ERROR CREATING MODELER", "Modeler not created because User is in use");
                    }
                }else{
                    JavaFXUtils.showErrorAlert("ERROR CREATING MODELER", "Password is not the same in both cases");
                }
            }else {
                JavaFXUtils.showErrorAlert("ERROR CREATING MODELER", "Password is longer than 16 characters");
            }
        } else if (choiceBox.getValue().equals("Client")) {
            if (passwordText.getText().length() < 16){
                if (passwordText.getText().equals(confirmPasswordText.getText())) {
                    Client clientToSignIn = new Client(userText.getText(), JavaFXUtils.hashPassword(passwordText.getText()), nameText.getText(), surnamesText.getText(), emailText.getText(), bornDatePicker.getValue(), "images/user.jpg");
                    if (UserDAO.buildClient().findUserByUser(clientToSignIn.getUser()) == null) {
                        if (JavaFXUtils.validateEmail(emailText.getText())){
                            if (UserDAO.buildClient().findUserByEmail(clientToSignIn.getEmail()) == null){
                                UserDAO.buildClient().save(clientToSignIn);
                                clientSingleton.getInstance(clientToSignIn);
                                App.currentController.changeScene(Scenes.START, null);
                            }else{
                                JavaFXUtils.showErrorAlert("ERROR CREATING CLIENT", "Client not created because Email is in use");
                            }
                        }else{
                            JavaFXUtils.showErrorAlert("ERROR CREATING CLIENT", "Invalid email");
                        }
                    }else {
                        JavaFXUtils.showErrorAlert("ERROR CREATING CLIENT", "Client not created because User is in use");
                    }
                }else{
                    JavaFXUtils.showErrorAlert("ERROR CREATING CLIENT", "Password is not the same in both cases");
                }
            }else {
                JavaFXUtils.showErrorAlert("ERROR CREATING CLIENT", "Password is longer than 16 characters");
            }
        }
    }
}