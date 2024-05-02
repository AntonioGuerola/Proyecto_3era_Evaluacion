package org.example.test;

import org.example.model.dao.UserDAO;
import org.example.model.entity.Modeler;
import org.example.model.entity.User;

import java.time.LocalDate;

public class TestInsert {
    public static void main(String[] args) {
        Modeler mo = new Modeler("juanvi", "pepe123", "juan", "lopez", "popo@gmail.com", LocalDate.now(), "dddddd");
        UserDAO uDAO = new UserDAO();
        System.out.println(uDAO.save(mo));
    }
}