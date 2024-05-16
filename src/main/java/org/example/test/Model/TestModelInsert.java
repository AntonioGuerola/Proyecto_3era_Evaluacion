package org.example.test.Model;

import org.example.model.dao.ModelDAO;
import org.example.model.dao.UserDAO;
import org.example.model.entity.*;

import static org.example.model.entity.ModelCategory.HOUSE;
import static org.example.model.entity.ModelCategory.MOBILE_HOLDER;

public class TestModelInsert {
    public static void main(String[] args) {
        UserDAO<Modeler> moDAO = new UserDAO<>(Modeler.class);

        User u1 = moDAO.findUserByUser("juanvi");
        User u2 = moDAO.findUserByUser("onix55");

        Modeler m1 = (Modeler) u1;
        Modeler m2 = (Modeler) u2;

        Model model1 = new Model("Articulated Cat", 3.5, "A spectacular articulated cat from de head until the tail.",
                4.5, "eeetttteeee", "ooooooppppppooooo", HOUSE, m1);
        Model model2 = new Model("Benchy", 0, "The allknown boat for tester any printer",
                5, "4c6s4v9sdv", "19vsf19svs", MOBILE_HOLDER, m2);

        Model model3 = new Model("Betis mobile holder", 0.5, "A mobile holder with the form of the Real Betis shield.",
                1.6, "75d9ss49cd", "cs6d4s8v4v", MOBILE_HOLDER, m1);

        Model model4 = new Model("Hunger Games Logo", 1.6, "The Hunger Games logo for decorate the wall",
                3, "c194w645v", "cS4V4", HOUSE, m2);

        ModelDAO modelDAO = new ModelDAO();
        System.out.println(modelDAO.save(model1));
        System.out.println(modelDAO.save(model2));
        System.out.println(modelDAO.save(model3));
        System.out.println(modelDAO.save(model4));
    }
}
