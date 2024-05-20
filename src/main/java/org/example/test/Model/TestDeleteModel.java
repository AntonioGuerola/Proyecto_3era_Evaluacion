package org.example.test.Model;

import org.example.model.dao.ModelDAO;
import org.example.model.dao.UserDAO;
import org.example.model.entity.Model;
import org.example.model.entity.Modeler;
import java.util.ArrayList;

public class TestDeleteModel {
    public static void main(String[] args) {
        ModelDAO modelDAO = new ModelDAO();
        UserDAO<Modeler> moDAO = new UserDAO<>(Modeler.class);
        Modeler modeler = (Modeler) moDAO.findById(1);
        ArrayList<Model> models = (ArrayList<Model>) modelDAO.findByModeler(modeler);
        Model modelToDelete = modelDAO.findById(3);
        System.out.println(modelDAO.delete(modelToDelete));
        for (Model model : models) {
            System.out.println((modelDAO.delete(model)));
        }
    }
}
