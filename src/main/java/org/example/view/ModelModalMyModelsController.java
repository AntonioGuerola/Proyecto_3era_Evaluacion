package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.App;
import org.example.model.entity.Model;
import org.example.model.singleton.modelSingleton;
import org.example.model.singleton.modelerSingleton;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the modal view of a model in "My Models" section.
 */
public class ModelModalMyModelsController extends Controller implements Initializable {
    @FXML
    private TextField modelNameText;

    @FXML
    private TextField modelPriceText;

    @FXML
    private TextField modelRatingText;

    @FXML
    private TextField modelModelerText;

    @FXML
    private TextArea modelDescriptionText;

    @FXML
    private ImageView imageView;

    /**
     * Initializer for some aspects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model selectedModel = modelSingleton.getInstance().getCurrentModel();
        if (selectedModel != null) {
            modelNameText.setText(selectedModel.getName());
            modelPriceText.setText(String.valueOf(selectedModel.getPrice()));
            modelRatingText.setText(String.valueOf(selectedModel.getPrice()));
            modelModelerText.setText(selectedModel.getModeler().getUser());
            modelDescriptionText.setText(selectedModel.getDescription());
            byte[] imageData = selectedModel.getImage();
            if (imageData != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageView.setImage(image);
            }
        }
    }

    /**
     * Change the scene to START when the controller is opened.
     */
    @Override
    public void onOpen(Object input) throws IOException {

    }

    /**
     * Do something when the controller is going to close.
     */
    @Override
    public void onClose(Object output) {

    }

    /**
     * Navigates back to the "My Models" section.
     *
     * @throws IOException If an error occurs during view change.
     */
    public void goBack() throws IOException {
        if (modelerSingleton.getInstance() != null) {
            App.currentController.changeScene(Scenes.MYMODELS, null);
        }
    }

    /**
     * Opens the model settings view.
     *
     * @throws IOException If an error occurs during view change.
     */
    public void toModelSettigs() throws IOException {
        App.currentController.changeScene(Scenes.MODELSETTINGS, null);
    }
}