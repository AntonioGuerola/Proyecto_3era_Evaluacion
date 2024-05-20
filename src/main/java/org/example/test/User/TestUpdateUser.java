package org.example.test.User;

import org.example.model.dao.UserDAO;
import org.example.model.entity.Client;
import org.example.model.entity.Modeler;

import java.time.LocalDate;

public class TestUpdateUser {
    public static void main(String[] args) {
        UserDAO<Modeler> moDAO = new UserDAO<>(Modeler.class);
        Modeler mo = (Modeler) moDAO.findById(5);

        mo.setUser("dragonLegacy48");
        mo.setPassword("ñokis");
        mo.setEmail("dragon_xenoverse@gmail.com");
        //mo.setImage("AAAAAAAAAAAAA");
        System.out.println(moDAO.save(mo));

        UserDAO<Client> cliDAO = new UserDAO<>(Client.class);
        Client cli = (Client) cliDAO.findById(1);

        cli.setUser("antonio0rtega02");
        cli.setPassword("joseEnrique");
        cli.setEmail("litovaina445@gmail.com");
        //cli.setImage("VCVCVCVCVCVC");
        System.out.println(cliDAO.save(cli));
    }
}
