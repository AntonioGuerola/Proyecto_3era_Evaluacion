package org.example.view;

public enum Scenes {
    ROOT("view/layout.fxml"),
    START("view/start.fxml"),
    SIGNIN("view/SignIn.fxml"),
    LOGIN("view/LogIn.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }

}