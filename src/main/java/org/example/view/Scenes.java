package org.example.view;

/**
 * Enumeration representing the scenes (views) in the application.
 */
public enum Scenes {
    ROOT("view/layout.fxml"),
    START("view/start.fxml"),
    SIGNIN("view/SignIn.fxml"),
    LOGIN("view/LogIn.fxml"),
    CLIENTHOME("view/ClientHome.fxml"),
    MODELERHOME("view/ModelerHome.fxml"),
    MYMODELS("view/MyModels.fxml"),
    INSERTMODEL("view/InsertModel.fxml"),
    MODELMODAL("view/ModeLModal.fxml"),
    MODELMODALBASKET("view/ModeLModalBasket.fxml"),
    MODELMODALMYMODELS("view/ModeLModalMyModels.fxml"),
    MODELSETTINGS("view/ModelSettings.fxml"),
    SEARCHMODELSCLIENT("view/SearchModelsClient.fxml"),
    SEARCHMODELSMODELER("view/SearchModelsModeler.fxml"),
    USERSETTINGS("view/UserSettings.fxml"),
    BASKET("view/Basket.fxml");;

    private String url;

    /**
     * Constructor for Scenes enumeration.
     * @param url The URL of the corresponding FXML file.
     */
    Scenes(String url) {
        this.url = url;
    }

    /**
     * Gets the URL of the scene.
     * @return The URL of the FXML file representing the scene.
     */
    public String getURL() {
        return url;
    }
}