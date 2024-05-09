package org.example.test;

import org.example.model.dao.BasketDAO;
import org.example.model.dao.ModelDAO;
import org.example.model.dao.UserDAO;
import org.example.model.entity.*;

import java.util.ArrayList;
import java.util.List;

public class TestBasket {
    public static void main(String[] args) {
        UserDAO<Client> cliDAO = new UserDAO<>(Client.class);
        UserDAO<Modeler> moDAO = new UserDAO<>(Modeler.class);

        User user01 = cliDAO.findUserByUser("antonio02");
        User user02 = moDAO.findUserByUser("juanvi");

        Client cli = (Client) user01;

        ModelDAO modelDAO = new ModelDAO();
        List<Model> models = modelDAO.findByModeler(user02);


        Model model = models.get(0);

        BasketDAO basketDAO = new BasketDAO();
        Basket basket = basketDAO.findByName("%cat%");

        if (basket == null) {
            basket = new Basket();
            cli.setBasket(basket);
        }

        cli.getBasket().addModel(model);
        System.out.println(basketDAO.save(cli.getBasket()));

    }
}
