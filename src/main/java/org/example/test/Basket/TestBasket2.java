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
        // Buscar un modelo que contenga la palabra "cat" en su nombre
        ModelDAO modelDAO = new ModelDAO();
        List<Model> catModels = modelDAO.findByName("cat");
        List<Model> logoModels = modelDAO.findByName("logo");

        if (!catModels.isEmpty()) {
            // Encontrar al cliente "antonio02"
            UserDAO<User> userDAO = new UserDAO<>(User.class);
            User user = userDAO.findUserByUser("antonio0rtega02");

            if (user != null) {
                // Buscar la cesta del cliente
                BasketDAO basketDAO = new BasketDAO();
                Basket existingBasket = basketDAO.findBasketByClient((Client) user);

                // Si el cliente ya tiene una cesta, usar esa
                Basket basket;
                if (existingBasket != null) {
                    basket = existingBasket;
                } else {
                    // Si no, crear una nueva cesta
                    basket = new Basket();
                    basket.setClient((Client) user);
                    basketDAO.save(basket);
                }

                // Agregar el primer modelo encontrado al basket
                basket.addModel(catModels.get(0));
                basket.addModel(logoModels.get(0));

                // Guardar la cesta
                basketDAO.saveModelsInBasket(basket, catModels.get(0));
                basketDAO.saveModelsInBasket(basket, logoModels.get(0));

                //System.out.println(basketDAO.calculateFinalPriceFromModels(basket));

                System.out.println("");
                System.out.println("");

                //System.out.println(basket.getFinalPrice());

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
            // Encontrar al cliente "antonio02"
            UserDAO<User> userDAO = new UserDAO<>(User.class);
            User user2 = userDAO.findUserByUser("pikagame");

            if (user2 != null) {
                // Buscar la cesta del cliente
                BasketDAO basketDAO = new BasketDAO();
                Basket existingBasket2 = basketDAO.findBasketByClient((Client) user2);

                // Si el cliente ya tiene una cesta, usar esa
                Basket basket2;
                if (existingBasket2 != null) {
                    basket2 = existingBasket2;
                } else {
                    // Si no, crear una nueva cesta
                    basket2 = new Basket();
                    basket2.setClient((Client) user2);
                    basketDAO.save(basket2);
                }

                // Agregar el primer modelo encontrado al basket
                basket2.addModel(logoModels.get(0));

                // Guardar la cesta
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
