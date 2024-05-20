package org.example.test.User;

import org.example.model.dao.UserDAO;
import org.example.model.entity.Client;
import org.example.model.entity.Modeler;

public class TestDeleteUSer {
    public static void main(String[] args) {
        UserDAO<Modeler> moDAO = new UserDAO<>(Modeler.class);
        Modeler mo = (Modeler) moDAO.findUserByUser("aqua85nevada");
        System.out.println(moDAO.delete(mo));
        UserDAO<Client> cliDAO = new UserDAO<>(Client.class);
        Client cli = (Client) cliDAO.findUserByUser("super_homer");
        System.out.println(cliDAO.delete(cli));
    }
}
