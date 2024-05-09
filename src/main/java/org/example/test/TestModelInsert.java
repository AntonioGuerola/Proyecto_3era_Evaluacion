package org.example.test;

import org.example.model.dao.ModelDAO;
import org.example.model.dao.UserDAO;
import org.example.model.entity.*;

import static org.example.model.entity.ModelCategory.HOUSE;

public class TestModelInsert {
    public static void main(String[] args) {
        UserDAO<Modeler> moDAO = new UserDAO<>(Modeler.class);

        User u = moDAO.findUserByUser("juanvi");

        Modeler m = (Modeler) u;

        Model model = new Model("Articulated Cat", 3.5, "A spectacular articulated cat from de head until the tail.",
                4.5, "eeetttteeee", "ooooooppppppooooo", HOUSE, m);

        ModelDAO modelDAO = new ModelDAO();
        System.out.println(modelDAO.save(model));
    }
}
