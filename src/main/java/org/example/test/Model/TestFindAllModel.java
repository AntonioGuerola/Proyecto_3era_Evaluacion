package org.example.test.Model;

import org.example.model.dao.ModelDAO;
import org.example.model.dao.UserDAO;
import org.example.model.entity.Client;
import org.example.model.entity.Modeler;

public class TestFindAllModel {
    public static void main(String[] args) {
        ModelDAO modelDAO = new ModelDAO();
        System.out.println(modelDAO.findAll());
    }
}
