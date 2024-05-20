package org.example.view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.App;
import org.example.model.dao.UserDAO;
import org.example.model.entity.Client;
import org.example.model.entity.Modeler;
import org.example.model.singleton.clientSingleton;
import org.example.model.singleton.modelerSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.*;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class UserSettingsController extends Controller implements Initializable {

    @FXML
    private TextField userText;

    @FXML
    private TextField passwordText;

    @FXML
    private TextField emailText;

    @FXML
    private DatePicker bornDatePicker;

    @FXML
    private ImageView imageView;

    private File imageFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (modelerSingleton.getInstance() != null) {
            Modeler modelerToUpdate = modelerSingleton.getInstance().getCurrentModeler();
            loadImageView(modelerToUpdate.getImage());
        } else if (clientSingleton.getInstance() != null) {
            Client clientToUpdate = clientSingleton.getInstance().getCurrentClient();
            loadImageView(clientToUpdate.getImage());
        }
    }

    private void loadImageView(byte[] imageData) {
        if (imageData != null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            Image image = new Image(bis);
            imageView.setImage(image);
        } else {
            System.out.println("imageData is null");
        }
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    public void updateUser() throws NoSuchAlgorithmException, IOException {
        if (modelerSingleton.getInstance() != null) {
            Modeler modelerToUpdate = modelerSingleton.getInstance().getCurrentModeler();
            Modeler modelerUpdated = new Modeler(userText.getText(), passwordText.getText(), "", "", emailText.getText(), bornDatePicker.getValue(), modelerToUpdate.getImage());
            if (passwordText.getText().length() < 16) {
                if (UserDAO.buildModeler().findUserByUser(userText.getText()) == null) {
                    if (JavaFXUtils.validateEmail(emailText.getText())) {
                        if (UserDAO.buildModeler().findUserByEmail(modelerUpdated.getEmail()) == null) {
                            modelerToUpdate.setUser(userText.getText());
                            modelerToUpdate.setPassword(JavaFXUtils.hashPassword(passwordText.getText()));
                            modelerToUpdate.setEmail(emailText.getText());
                            modelerToUpdate.setBornDate(bornDatePicker.getValue());

                            if (imageFile != null) {
                                byte[] imageData = new byte[(int) imageFile.length()];
                                try (FileInputStream fis = new FileInputStream(imageFile)) {
                                    ;
                                    fis.read(imageData);
                                }
                                modelerToUpdate.setImage(imageData);
                            }

                            UserDAO.buildModeler().save(modelerToUpdate);
                            modelerSingleton.getInstance(modelerToUpdate);
                            App.currentController.changeScene(Scenes.MODELERHOME, null);
                        } else {
                            JavaFXUtils.showErrorAlert("ERROR CREATING MODELER", "Modeler not created because Email is in use");
                        }
                    } else {
                        JavaFXUtils.showErrorAlert("ERROR CREATING MODELER", "Invalid email");
                    }
                } else {
                    JavaFXUtils.showErrorAlert("ERROR CREATING MODELER", "Modeler not created because User is in use");
                }
            } else {
                JavaFXUtils.showErrorAlert("ERROR CREATING MODELER", "Password is longer than 16 characters");
            }
        } else if (clientSingleton.getInstance() != null) {
            Client clientToUpdate = clientSingleton.getInstance().getCurrentClient();
            Client clientUpdated = new Client(userText.getText(), passwordText.getText(), "", "", emailText.getText(), bornDatePicker.getValue(), clientToUpdate.getImage());
            if (passwordText.getText().length() < 16) {
                if (UserDAO.buildClient().findUserByUser(userText.getText()) == null) {
                    if (JavaFXUtils.validateEmail(emailText.getText())) {
                        if (UserDAO.buildClient().findUserByEmail(clientUpdated.getEmail()) == null) {
                            clientToUpdate.setUser(userText.getText());
                            clientToUpdate.setPassword(JavaFXUtils.hashPassword(passwordText.getText()));
                            clientToUpdate.setEmail(emailText.getText());
                            clientToUpdate.setBornDate(bornDatePicker.getValue());

                            if (imageFile != null) {
                                byte[] imageData = new byte[(int) imageFile.length()];
                                try (FileInputStream fis = new FileInputStream(imageFile)) {
                                    ;
                                    fis.read(imageData);
                                }
                                clientToUpdate.setImage(imageData);
                            }

                            UserDAO.buildClient().save(clientToUpdate);
                            clientSingleton.getInstance(clientToUpdate);
                            App.currentController.changeScene(Scenes.CLIENTHOME, null);
                        } else {
                            JavaFXUtils.showErrorAlert("ERROR CREATING CLIENT", "Client not created because Email is in use");
                        }
                    } else {
                        JavaFXUtils.showErrorAlert("ERROR CREATING CLIENT", "Invalid email");
                    }
                } else {
                    JavaFXUtils.showErrorAlert("ERROR CREATING CLIENT", "Client not created because User is in use");
                }
            } else {
                JavaFXUtils.showErrorAlert("ERROR CREATING CLIENT", "Password is longer than 16 characters");
            }
        }
    }

    public void goBack() throws IOException {
        if (modelerSingleton.getInstance() != null) {
            App.currentController.changeScene(Scenes.MODELERHOME, null);
        } else if (clientSingleton.getInstance() != null) {
            App.currentController.changeScene(Scenes.CLIENTHOME, null);
        }
    }

    @FXML
    private void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) imageView.getScene().getWindow();
        imageFile = fileChooser.showOpenDialog(stage);
        if (imageFile != null) {
            try {
                InputStream is = new FileInputStream(imageFile);
                Image image = new Image(is);
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
