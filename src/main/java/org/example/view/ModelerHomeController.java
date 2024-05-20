package org.example.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import org.example.App;
import org.example.model.dao.ModelDAO;
import org.example.model.entity.Model;
import org.example.model.singleton.modelSingleton;
import javafx.scene.image.ImageView;
import org.example.model.singleton.searchSingleton;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the modeler's home view.
 */
public class ModelerHomeController extends Controller implements Initializable {
    @FXML
    private TextField searchText;

    @FXML
    private TableView<Model> TableView;

    @FXML
    private TableColumn<Model, Integer> modelId;

    @FXML
    private TableColumn<Model, String> modelName;

    @FXML
    private TableColumn<Model, Double> modelPrice;

    @FXML
    private TableColumn<Model, Double> modelRating;

    @FXML
    private TableColumn<Model, String> modelModeler;

    @FXML
    private TableColumn<Model, ImageView> modelImage;

    private ObservableList<Model> observableList;

    /**
     * Initializer for some aspects.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (TableView.getItems().isEmpty()) {
            List<Model> modelsList = ModelDAO.build().findAll();
            this.observableList = FXCollections.observableArrayList(modelsList);
            TableView.setItems(observableList);
            modelId.setCellValueFactory(models -> new SimpleIntegerProperty(models.getValue().getId()).asObject());
            modelName.setCellValueFactory(models -> new SimpleStringProperty(models.getValue().getName()));
            modelPrice.setCellValueFactory(models -> new SimpleDoubleProperty(models.getValue().getPrice()).asObject());
            modelRating.setCellValueFactory(models -> new SimpleDoubleProperty(models.getValue().getRating()).asObject());
            modelModeler.setCellValueFactory(models -> new SimpleStringProperty(models.getValue().getModeler().getUser()));
            modelImage.setCellValueFactory(models -> {
                byte[] imageData = models.getValue().getImage();
                if (imageData != null) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    Image image = new Image(bis);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(150);
                    imageView.setPreserveRatio(true);
                    return new SimpleObjectProperty<>(imageView);
                } else {
                    System.out.println("visualData es null");
                    return null;
                }
            });
            TableView.setRowFactory(tv -> {
                TableRow<Model> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (!row.isEmpty()) {
                        Model rowData = row.getItem();
                        modelSingleton.getInstance(rowData);
                        try {
                            App.currentController.changeScene(Scenes.MODELMODAL, null);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                return row;
            });
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
     * Navigates to the user settings view.
     *
     * @throws IOException If an error occurs during view change.
     */
    public void toUserSettings() throws IOException {
        App.currentController.changeScene(Scenes.USERSETTINGS, null);
    }

    /**
     * Navigates to the insert model view.
     *
     * @throws IOException If an error occurs during view change.
     */
    public void toInsertModel() throws IOException {
        App.currentController.changeScene(Scenes.INSERTMODEL, null);
    }

    /**
     * Navigates to the my models view.
     *
     * @throws IOException If an error occurs during view change.
     */
    public void toMyModels() throws IOException {
        App.currentController.changeScene(Scenes.MYMODELS, null);
    }

    /**
     * Closes the application.
     *
     * @throws IOException If an error occurs during application closure.
     */
    @FXML
    private void close() throws IOException {
        System.exit(0);
    }

    /**
     * Searches for models based on the provided text.
     *
     * @throws IOException If an error occurs during view change.
     */
    @FXML
    private void searchModel() throws IOException {
        searchSingleton.getInstance(searchText.getText());
        App.currentController.changeScene(Scenes.SEARCHMODELSMODELER, null);
    }
}