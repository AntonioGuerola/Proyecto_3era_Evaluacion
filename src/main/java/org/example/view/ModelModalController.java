package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.App;
import org.example.model.dao.BasketDAO;
import org.example.model.entity.Basket;
import org.example.model.entity.Client;
import org.example.model.entity.Model;
import org.example.model.singleton.basketSingleton;
import org.example.model.singleton.clientSingleton;
import org.example.model.singleton.modelSingleton;
import org.example.model.singleton.modelerSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the modal view of a model.
 */
public class ModelModalController extends Controller implements Initializable {
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
     * Navigates back to the appropriate home view (modeler or client).
     *
     * @throws IOException If an error occurs during view change.
     */
    public void goBack() throws IOException {
        if (modelerSingleton.getInstance() != null) {
            App.currentController.changeScene(Scenes.MODELERHOME, null);
        } else if (clientSingleton.getInstance() != null) {
            App.currentController.changeScene(Scenes.CLIENTHOME, null);
        }
    }

    /**
     * Adds the selected model to the client's basket.
     *
     * @throws IOException If an error occurs during model addition.
     */
    public void addModelToBasket() throws IOException {
        if (modelerSingleton.getInstance() != null) {
            JavaFXUtils.showErrorAlert("FAILED TO ADD THE MODEL INTO THE BASKET", "To add the model to your basket, you have to be a client");
        } else if (clientSingleton.getInstance() != null) {
            Model selectedModel = modelSingleton.getInstance().getCurrentModel();
            Client currentClient = clientSingleton.getInstance().getCurrentClient();
            BasketDAO basketDAO = new BasketDAO();
            Basket clientBasket = basketDAO.findBasketByClient(currentClient);
            Basket basket;
            if (clientBasket != null) {
                basket = clientBasket;
            } else {
                basket = new Basket();
                basket.setClient(currentClient);
                basketDAO.save(basket);
            }
            basket.addModel(selectedModel);
            basketDAO.saveModelsInBasket(basket, selectedModel);
            basketSingleton.getInstance(clientBasket);
            App.currentController.changeScene(Scenes.CLIENTHOME, null);
        }
    }
}