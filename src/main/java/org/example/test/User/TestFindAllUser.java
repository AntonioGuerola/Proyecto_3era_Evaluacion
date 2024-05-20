package org.example.test.User;

import org.example.model.dao.UserDAO;
import org.example.model.entity.Client;
import org.example.model.entity.Modeler;

public class TestFindAllUser {
    public static void main(String[] args) {
        UserDAO<Modeler> moDAO = new UserDAO<>(Modeler.class);
        UserDAO<Client> cliDAO = new UserDAO<>(Client.class);
        System.out.println(moDAO.findAll());
        System.out.println(cliDAO.findAll());

    }
}
