package org.example;

import org.example.model.dao.UserDAO;
import org.example.model.entity.Modeler;
import org.example.model.entity.User;

public class borrame {
    public static void main(String[] args) {
        UserDAO<Modeler> userDAO = new UserDAO<>(Modeler.class);
        User u = userDAO.findById(2);
        System.out.println(u);
    }
}
