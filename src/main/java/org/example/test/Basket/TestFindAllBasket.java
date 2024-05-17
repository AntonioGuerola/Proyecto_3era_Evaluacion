package org.example.test.Basket;

import org.example.model.dao.BasketDAO;

public class TestFindAllBasket {
    public static void main(String[] args) {
        BasketDAO basketdao = new BasketDAO();
        System.out.println(basketdao.findAll());
    }
}
