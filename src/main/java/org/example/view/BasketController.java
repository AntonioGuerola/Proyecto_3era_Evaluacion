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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.App;
import org.example.model.entity.Basket;
import org.example.model.entity.Model;
import org.example.model.singleton.basketSingleton;
import org.example.model.singleton.modelSingleton;
import org.example.model.singleton.searchSingleton;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller class for managing the basket view.
 */
public class BasketController extends Controller implements Initializable {
    @FXML
    private TextField basketFinalPrice;

    @FXML
    private TextField searchText;

    @FXML
    private javafx.scene.control.TableView<Model> TableView;

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
        Basket basketToUse = basketSingleton.getInstance().getCurrentBasket();
        if (TableView.getItems().isEmpty()) {
            Map<Integer, Model> modelsMap = basketSingleton.getInstance().getCurrentBasket().getModels();
            List<Model> modelsList = new ArrayList<>(modelsMap.values());
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
                            App.currentController.changeScene(Scenes.MODELMODALBASKET, null);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                return row;
            });
        }
        if (basketToUse != null) {
            basketFinalPrice.setText(String.valueOf(basketToUse.getFinalPrice()));
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
     * @throws IOException If an error occurs while loading the view.
     */
    public void toUserSettings() throws IOException {
        App.currentController.changeScene(Scenes.USERSETTINGS, null);
    }

    /**
     * Navigates back to the client home view.
     *
     * @throws IOException If an error occurs while loading the view.
     */
    @FXML
    private void toGoBack() throws IOException {
        App.currentController.changeScene(Scenes.CLIENTHOME, null);
    }

    /**
     * Searches for a model based on the text entered in the search field.
     *
     * @throws IOException If an error occurs while loading the search view.
     */
    @FXML
    private void searchModel() throws IOException {
        searchSingleton.getInstance(searchText.getText());
        App.currentController.changeScene(Scenes.SEARCHMODELSCLIENT, null);
    }
}