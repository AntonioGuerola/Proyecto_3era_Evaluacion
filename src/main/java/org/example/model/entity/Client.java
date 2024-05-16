package org.example.model.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Client extends User{

    public Client(String user, String password, String name, String surname, String email, LocalDate bornDate, String image) {
        super(user,password,name,surname,email,bornDate,image);
    }

    public Client(){
    }

    @Override
    public String toString() {
        return "Client{" +super.toString();
    }
}
