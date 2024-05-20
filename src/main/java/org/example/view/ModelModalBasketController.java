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
import org.example.model.entity.Modeler;
import org.example.model.singleton.basketSingleton;
import org.example.model.singleton.clientSingleton;
import org.example.model.singleton.modelSingleton;
import org.example.model.singleton.modelerSingleton;
import org.example.model.utils.JavaFXUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModelModalBasketController extends Controller implements Initializable {
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

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    public void goBack() throws IOException {
        if (clientSingleton.getInstance() != null) {
            App.currentController.changeScene(Scenes.CLIENTHOME, null);
        }
    }

    public void removeModel() throws IOException {
        if (clientSingleton.getInstance() != null) {
            Model selectedModel = modelSingleton.getInstance().getCurrentModel();
            Client currentClient = clientSingleton.getInstance().getCurrentClient();


            BasketDAO basketDAO = new BasketDAO();
            Basket clientBasket = basketDAO.findBasketByClient(currentClient);

            Basket basket;
            if (clientBasket != null) {
                basket = clientBasket;
            } else {
                // Si no, crear una nueva cesta
                basket = new Basket();
                basket.setClient(currentClient);
            }

            basket.removeModel(selectedModel);

            basketDAO.save(basket);

            basketSingleton.getInstance(clientBasket);
            App.currentController.changeScene(Scenes.BASKET, null);
        }
    }
}
