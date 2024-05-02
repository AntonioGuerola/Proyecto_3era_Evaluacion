package org.example.model.entity;


public class Modeler extends User{


    public Modeler(Integer id, String user, String password, String name, String surname, String mail, java.sql.Date bornDate, String img) {
        super(id,user,password,name,surname,mail,bornDate,img);
    }
    public Modeler(){
    }
    @Override
    public String toString() {
        return "Modeler{" + super.toString();
    }
}
