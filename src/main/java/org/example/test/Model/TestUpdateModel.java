package org.example.test.Model;

import org.example.model.dao.ModelDAO;
import org.example.model.dao.UserDAO;
import org.example.model.entity.Client;
import org.example.model.entity.Model;
import org.example.model.entity.Modeler;
import org.example.model.entity.User;

public class TestUpdateModel {
    public static void main(String[] args) {
        ModelDAO modelDAO = new ModelDAO();

        Model model = modelDAO.findById(2);

        model.setName("Articulated Tester Cat");
        model.setPrice(1.5);
        model.setDescription("A spectacular articulated cat from de head until the tail to tester any printer.");
        model.setImage(null);

        System.out.println(modelDAO.save(model));

    }
}
