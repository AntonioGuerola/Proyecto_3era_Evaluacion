package org.example.model.entity;


import java.time.LocalDate;

public class Modeler extends User{


    public Modeler(String user, String password, String name, String surname, String email, LocalDate bornDate, String image) {
        super(user,password,name,surname,email,bornDate,image);
    }
    public Modeler(){
    }
    @Override
    public String toString() {
        return "Modeler{" + super.toString();
    }
}
