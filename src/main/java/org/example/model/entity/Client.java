package org.example.model.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Client extends User{
    private Basket basket;

    public Client(String user, String password, String name, String surname, String email, LocalDate bornDate, String image, Basket basket) {
        super(user,password,name,surname,email,bornDate,image);
        this.basket = basket;
    }

    public Client(){
    }


    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }



    @Override
    public String toString() {
        return "Client{" +super.toString();
    }
}
