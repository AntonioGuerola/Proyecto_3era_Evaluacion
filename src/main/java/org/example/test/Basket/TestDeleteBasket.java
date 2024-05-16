package org.example.test.Basket;

import org.example.model.dao.BasketDAO;
import org.example.model.dao.ModelDAO;
import org.example.model.dao.UserDAO;
import org.example.model.entity.Basket;
import org.example.model.entity.Client;
import org.example.model.entity.Model;
import org.example.model.entity.User;

import java.util.List;

public class TestDeleteBasket {
    public static void main(String[] args) {
        // Encontrar al cliente "antonio02"
        UserDAO<User> userDAO = new UserDAO<>(User.class);
        User user = userDAO.findUserByUser("antonio0rtega02");

        if (user != null) {
            // Encontrar la cesta del cliente "antonio02"
            BasketDAO basketDAO = new BasketDAO();
            Basket basket = basketDAO.findBasketByClient((Client) user);

            if (basket != null) {
                System.out.println("El cliente \"" + user.getUser() + "\" tiene la siguiente cesta antes de eliminar el modelo:");
                System.out.println(basket);

                // Buscar el modelo con nombre "cat" en el ModelDAO
                ModelDAO modelDAO = new ModelDAO();
                List<Model> catModels = modelDAO.findByName("cat");

                if (!catModels.isEmpty()) {
                    Model catModel = catModels.get(0); // Tomar el primer modelo encontrado

                    if (basket.getModels().containsKey(catModel.getId())) {
                        // Eliminar el modelo "cat" del basket
                        basketDAO.deleteModelFromBasket(basket, catModel);
                        basket.removeModel(catModel);

                        System.out.println("El cliente \"" + user.getUser() + "\" tiene la siguiente cesta después de eliminar el modelo:");
                        //System.out.println(basket);

                        // Guardar la cesta actualizada
                        basketDAO.save(basket);

                        System.out.println("El modelo \"" + catModel.getName() + "\" se ha eliminado del basket del cliente \"" + user.getUser() + "\" correctamente.");
                    } else {
                        System.out.println("El cliente \"" + user.getUser() + "\" no tiene el modelo \"cat\" en su basket.");
                    }
                } else {
                    System.out.println("No se encontró ningún modelo que contenga la palabra \"cat\" en su nombre.");
                }
            } else {
                System.out.println("El cliente \"" + user.getUser() + "\" no tiene ningún basket.");
            }
        } else {
            System.out.println("No se encontró al cliente llamado \"antonio02\".");
        }
    }
}
