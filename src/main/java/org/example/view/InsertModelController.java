package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
 * Controller class for inserting a new model.
 */
public class InsertModelController extends Controller implements Initializable {
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

    }

    /**
     * Saves the model information to the database.
     *
     * @throws IOException If an error occurs during database access.
     */
    public void saveModel() throws IOException {
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
     * Loads an image file.
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
     * Returns to the modeler's home screen.
     *
     * @throws IOException If an error occurs during view change.
     */
    public void goBack() throws IOException {
        App.currentController.changeScene(Scenes.MODELERHOME, null);
    }
}