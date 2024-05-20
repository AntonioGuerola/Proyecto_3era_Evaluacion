package org.example.test.Model;

import org.example.model.dao.ModelDAO;

public class TestFindAllModel {
    public static void main(String[] args) {
        ModelDAO modelDAO = new ModelDAO();
        System.out.println(modelDAO.findAll());
    }
}
