package org.example.model.entity;


import java.time.LocalDate;

public class Modeler extends User{


    public Modeler(String user, String password, String name, String surname, String email, LocalDate born_date, String image) {
        super(user,password,name,surname,email,born_date,image);
    }
    public Modeler(){
    }
    @Override
    public String toString() {
        return "Modeler{" + super.toString();
    }
}
