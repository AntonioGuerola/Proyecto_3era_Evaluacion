package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.App;
import org.example.model.dao.ModelDAO;
import org.example.model.entity.Model;
import org.example.model.entity.ModelCategory;
import org.example.model.singleton.modelSingleton;
import org.example.model.singleton.modelerSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the model settings view.
 */
public class ModelSettingsController extends Controller implements Initializable {
    @FXML
    private TextField modelNameText;

    @FXML
    private TextField modelPriceValue;

    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<>();

    @FXML
    private ImageView imageView;

    private File imageFile;

    @FXML
    private TextArea modelDescriptionText;

    /**
     * Change the scene to START when the controller is opened.
     */
    @Override
    public void onOpen(Object input) throws IOException {
        choiceBox.getItems().add("House");
        choiceBox.getItems().add("Lithophany");
        choiceBox.getItems().add("Mobile holder");
    }

    /**
     * Do something when the controller is going to close.
     */
    @Override
    public void onClose(Object output) {

    }

    /**
     * Initializer for some aspects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model selectedModel = modelSingleton.getInstance().getCurrentModel();
        if (selectedModel != null) {
            byte[] imageData = selectedModel.getImage();
            if (imageData != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageView.setImage(image);
            }
        }
    }

    /**
     * Updates the model with the new information provided.
     *
     * @throws IOException If an error occurs during model update.
     */
    public void updateModel() throws IOException {
        double priceValue;
        try {
            priceValue = Double.parseDouble(modelPriceValue.getText());
        } catch (NumberFormatException e) {
            JavaFXUtils.showErrorAlert("ERROR", "The price has to be a valid number");
            return;
        }
        ModelCategory selectedCategory;
        switch (choiceBox.getValue()) {
            case "House":
                selectedCategory = ModelCategory.HOUSE;
                break;
            case "Lithophany":
                selectedCategory = ModelCategory.LITHOPHANY;
                break;
            case "Mobile holder":
                selectedCategory = ModelCategory.MOBILE_HOLDER;
                break;
            default:
                JavaFXUtils.showErrorAlert("ERROR", "Invalid category");
                return;
        }
        if (imageFile != null) {
            byte[] imageData = new byte[(int) imageFile.length()];
            FileInputStream fis = new FileInputStream(imageFile);
            fis.read(imageData);
            fis.close();
            Model modelToAdd = new Model(modelNameText.getText(), priceValue, modelDescriptionText.getText(), 0, imageData, "", selectedCategory, modelerSingleton.getInstance().getCurrentModeler());
            ModelDAO.build().save(modelToAdd);
            modelSingleton.getInstance(modelToAdd);
            App.currentController.changeScene(Scenes.MODELERHOME, null);
        }
    }

    /**
     * Loads an image to be associated with the model.
     */
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

    /**
     * Navigates back to the "My Models" section.
     *
     * @throws IOException If an error occurs during navigation.
     */
    public void goBack() throws IOException {
        App.currentController.changeScene(Scenes.MYMODELS, null);
    }
}