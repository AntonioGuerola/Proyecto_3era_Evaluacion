package org.example.test.Basket;

import org.example.model.dao.BasketDAO;
import org.example.model.dao.ModelDAO;
import org.example.model.dao.UserDAO;
import org.example.model.entity.Basket;
import org.example.model.entity.Client;
import org.example.model.entity.Model;
import org.example.model.entity.User;

import java.util.List;

public class TestBasket2 {
    public static void main(String[] args) {
        ModelDAO modelDAO = new ModelDAO();
        List<Model> catModels = modelDAO.findByName("cat");
        List<Model> logoModels = modelDAO.findByName("logo");
        if (!catModels.isEmpty()) {
            UserDAO<User> userDAO = new UserDAO<>(User.class);
            User user = userDAO.findUserByUser("antonio0rtega02");
            if (user != null) {
                BasketDAO basketDAO = new BasketDAO();
                Basket existingBasket = basketDAO.findBasketByClient((Client) user);
                Basket basket;
                if (existingBasket != null) {
                    basket = existingBasket;
                } else {
                    basket = new Basket();
                    basket.setClient((Client) user);
                    basketDAO.save(basket);
                }
                basket.addModel(catModels.get(0));
                basket.addModel(logoModels.get(0));
                basketDAO.saveModelsInBasket(basket, catModels.get(0));
                basketDAO.saveModelsInBasket(basket, logoModels.get(0));
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println(basket.toString());
                System.out.println("");
                System.out.println("");
                System.out.println("El modelo \"" + catModels.get(0).getName() + "\" se ha agregado al basket del cliente \"" + user.getUser() + "\" correctamente.");
                System.out.println("El modelo \"" + logoModels.get(0).getName() + "\" se ha agregado al basket del cliente \"" + user.getUser() + "\" correctamente.");
            } else {
                System.out.println("No se encontró al cliente llamado \"antonio02\".");
            }
        } else {
            System.out.println("No se encontró ningún modelo que contenga la palabra \"cat\" en su nombre.");
        }
        /*List<Model> logoModels = modelDAO.findByName("logo");
        if (!logoModels.isEmpty()) {
            UserDAO<User> userDAO = new UserDAO<>(User.class);
            User user2 = userDAO.findUserByUser("pikagame");
            if (user2 != null) {
                BasketDAO basketDAO = new BasketDAO();
                Basket existingBasket2 = basketDAO.findBasketByClient((Client) user2);
                Basket basket2;
                if (existingBasket2 != null) {
                    basket2 = existingBasket2;
                } else {
                    basket2 = new Basket();
                    basket2.setClient((Client) user2);
                    basketDAO.save(basket2);
                }
                basket2.addModel(logoModels.get(0));
                basketDAO.saveModelsInBasket(basket2, logoModels.get(0));
                System.out.println(basket2.toString());
                System.out.println("El modelo \"" + logoModels.get(0).getName() + "\" se ha agregado al basket del cliente \"" + user2.getUser() + "\" correctamente.");
            } else {
                System.out.println("No se encontró al cliente llamado \"pikagame\".");
            }
        } else {
            System.out.println("No se encontró ningún modelo que contenga la palabra \"logo\" en su nombre.");
        }*/
    }
}
