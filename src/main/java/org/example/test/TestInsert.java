package org.example.test;


import org.example.model.dao.UserDAO;
import org.example.model.entity.*;

import java.time.LocalDate;



public class TestInsert {
    public static void main(String[] args) {
        UserDAO<Modeler> moDAO = new UserDAO<>(Modeler.class);
        Modeler mo = new Modeler("juanvi", "pepe123", "juan", "lopez", "popo@gmail.com", LocalDate.now(), "dddddd");
        System.out.println(moDAO.save(mo));

        UserDAO<Client> cliDAO = new UserDAO<>(Client.class);
        Basket basket = new Basket();
        Client cli = new Client("antonio02", "joseEnrique", "jose", "garcía", "litovaina@gmail.com", LocalDate.now(), "erererer", basket);
        System.out.println(cliDAO.save(cli));
    }
}