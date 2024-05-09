package org.example.test;


import org.example.model.dao.UserDAO;
import org.example.model.entity.*;

import java.time.LocalDate;



public class TestInsert {
    public static void main(String[] args) {
        UserDAO<Modeler> moDAO = new UserDAO<>(Modeler.class);
        Modeler mo1 = new Modeler("juanvi", "pepe123", "juan", "lopez", "popo@gmail.com", LocalDate.now(), "dddddd");
        Modeler mo2 = new Modeler("aqua85nevada", "78549612plo", "juan jose", "gomez", "nevado@gmail.com", LocalDate.now(), "8521plk96");
        Modeler mo3 = new Modeler("onix55", "trau852mea", "francisco", "garcia", "pokedios@gmail.com", LocalDate.now(), "cwegnqaneq");
        Modeler mo4 = new Modeler("mars33gaming", "alchoooooooo52", "cristobal", "marin", "maaarrtin@gmail.com", LocalDate.now(), "vewj4qW484");
        Modeler mo5 = new Modeler("dragon_legacy", "ñokis", "paco", "estrada", "xenoverse@gmail.com", LocalDate.now(), "4SV9R4W8");
        System.out.println(moDAO.save(mo1));
        System.out.println(moDAO.save(mo2));
        System.out.println(moDAO.save(mo3));
        System.out.println(moDAO.save(mo4));
        System.out.println(moDAO.save(mo5));

        UserDAO<Client> cliDAO = new UserDAO<>(Client.class);
        Client cli1 = new Client("antonio02", "joseEnrique", "jose", "garcía", "litovaina@gmail.com", LocalDate.now(), "erererer", null);
        Client cli2 = new Client("pikagame", "brush789", "ivan", "antunez", "nobitanobi@gmail.com", LocalDate.now(), "vinobuwnagwgw8", null);
        Client cli3 = new Client("super_homer", "t00th", "pepe", "santo", "giganteelmejor@gmail.com", LocalDate.now(), "4v64e84aww", null);
        System.out.println(cliDAO.save(cli1));
        System.out.println(cliDAO.save(cli2));
        System.out.println(cliDAO.save(cli3));
    }
}