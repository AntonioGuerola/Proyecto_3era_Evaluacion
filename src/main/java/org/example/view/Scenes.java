package org.example.view;

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
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }

}