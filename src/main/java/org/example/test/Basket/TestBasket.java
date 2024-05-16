package org.example.test.Basket;

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

        // Encontrar un cliente y un modelador existentes
        User user01 = cliDAO.findUserByUser("antonio0rtega02");
        User user02 = moDAO.findUserByUser("juanvi");

        // Convertir el usuario en cliente y modelador respectivamente
        Client client = (Client) user01;
        Modeler modeler = (Modeler) user02;

        // Crear una instancia de BasketDAO para operaciones de cesta
        BasketDAO basketDAO = new BasketDAO();

        // Encontrar un basket existente o crear uno nuevo si es necesario
        Basket basket = basketDAO.findByName("%cat%");
        if (basket == null) {
            // Si no se encuentra un basket con el nombre especificado, crear uno nuevo
            basket = new Basket();
            // Aquí puedes configurar más detalles del basket si es necesario
            basket = basketDAO.save(basket);
        }

        // Obtener algunos modelos para agregar al basket
        ModelDAO modelDAO = new ModelDAO();
        List<Model> models = modelDAO.findByModeler(user02);

        // Agregar modelos al basket
        for (Model model : models) {
            basket.addModel(model);
        }

        // Actualizar el basket en la base de datos después de agregar los modelos
        basketDAO.save(basket);

        // Verificar si los modelos se han agregado correctamente al basket
        System.out.println("Modelos agregados al basket:");
        for (Model model : basket.getModels().values()) {
            System.out.println(model.getName());
        }
    }
}
