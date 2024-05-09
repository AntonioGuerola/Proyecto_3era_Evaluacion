package org.example.model.dao;

import org.example.model.connection.ConnectionMariaDB;
import org.example.model.entity.Basket;
import org.example.model.entity.Model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketDAO implements DAO<Basket, Integer> {

    private static final String INSERT = "INSERT INTO basket (finalPrice ) VALUES (?)";
    private static final String INSERTMODELS = "INSERT INTO incluye (cantidad,id_basket,id_model ) VALUES (?,?,?)";
    private static final String UPDATE = "DELETE FROM incluye WHERE id_basket=?";
    private static final String DELETE = "DELETE FROM basket WHERE id = ?";
    private static final String FINDBYID = "SELECT id, finalPrice, model FROM basket WHERE id = ?";
    private static final String FINDALL = "SELECT id, finalPrice, model FROM basket";
    private static final String FINDBYNAME = "SELECT id, finalPrice, model FROM basket WHERE name LIKE ?";
    //private static final String INSERTMODELINBASKET = "SELECT m.* FROM model m INNER JOIN incluye i ON m.id = i.id_model WHERE i.id_basket = ?";

    private Connection conn;

    public BasketDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Basket save(Basket basket) {
        if (basket != null && basket.getId() <= 0) {
            try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setDouble(1, basket.getFinalPrice());
                pst.executeUpdate();
                ResultSet resultSet = pst.getGeneratedKeys();
                if (resultSet.next()) {
                    basket.setId(resultSet.getInt(1));
                }
                //en el update por simplificar, boora primero todos los modelos.

                //Toca insertar los modelos que tiene la cesta
                for (Map.Entry<Model, Integer> entry : basket.getModels().entrySet()) {
                    Model model = entry.getKey();
                    int quantity = entry.getValue();
                    try (PreparedStatement pst2 = conn.prepareStatement(INSERTMODELS)) {
                        pst.setInt(1, quantity);
                        pst.setInt(2, basket.getId());
                        pst.setInt(3, model.getId());
                        pst.executeUpdate();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            try (PreparedStatement pst = conn.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS)) {
                pst.setDouble(1, basket.getFinalPrice());
                pst.executeUpdate();
                ResultSet resultSet = pst.getGeneratedKeys();
                if (resultSet.next()) {
                    basket.setId(resultSet.getInt(1));
                }
                for (Map.Entry<Model, Integer> entry : basket.getModels().entrySet()) {
                    Model model = entry.getKey();
                    int quantity = entry.getValue();
                    try (PreparedStatement pst2 = conn.prepareStatement(INSERTMODELS)) {
                        pst.setInt(1, quantity);
                        pst.setInt(2, basket.getId());
                        pst.setInt(3, model.getId());
                        pst.executeUpdate();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return basket;
    }

    @Override
    public Basket delete(Basket basket) {
        if (basket != null && basket.getId() != 0) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setInt(1, basket.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return basket;
    }

    @Override
    public Basket findById(Integer key) {
        Basket result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, key);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                result = new Basket();
                result.setId(resultSet.getInt("id"));
                result.setFinalPrice(resultSet.getDouble("finalPrice"));
                // Aquí puedes agregar la lógica para obtener los modelos incluidos en la cesta si es necesario
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Basket findByName(String key) {
        /*Basket result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYNAME)) {
            pst.setString(1, key);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                result = new Basket();
                result.setId(resultSet.getInt("id"));
                result.setFinalPrice(resultSet.getDouble("finalPrice"));
                ArrayList<Model> includedModels = new ArrayList<>();
                try (PreparedStatement pstInclude = conn.prepareStatement(INSERTMODELINBASKET)) {
                    pstInclude.setInt(1, result.getId());
                    ResultSet resultSetInclude = pstInclude.executeQuery();
                    while (resultSetInclude.next()) {
                        Model model = new Model();
                        model.setId(resultSetInclude.getInt("id"));
                        model.setName(resultSetInclude.getString("name"));
                        model.setPrice(resultSetInclude.getDouble("price"));
                        // Otras propiedades del modelo

                        includedModels.add(model);
                    }
                }
                result.setModels(includedModels);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return null; //result;
    }

    @Override
    public List<Basket> findAll() {
        /*List<Basket> baskets = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FINDALL)) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                Basket basket = new Basket();
                basket.setId(resultSet.getInt("id"));
                basket.setFinalPrice(resultSet.getDouble("finalPrice"));

                HashMap<Model,Integer> includedModels = new HashMap<>();
                try (PreparedStatement pstInclude = conn.prepareStatement(INSERTMODELINBASKET)) {
                    pstInclude.setInt(1, basket.getId());
                    ResultSet resultSetInclude = pstInclude.executeQuery();
                    while (resultSetInclude.next()) {
                        Model model = new Model();
                        model.setId(resultSetInclude.getInt("id"));
                        model.setName(resultSetInclude.getString("name"));
                        model.setPrice(resultSetInclude.getDouble("price"));
                        includedModels.addmodel(model);
                    }
                }
                basket.setModels(includedModels);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return null; //baskets;
    }

    @Override
    public void close() throws IOException {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


