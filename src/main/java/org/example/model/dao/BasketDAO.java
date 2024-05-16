package org.example.model.dao;

import org.example.model.connection.ConnectionMariaDB;
import org.example.model.entity.Basket;
import org.example.model.entity.BasketState;
import org.example.model.entity.Client;
import org.example.model.entity.Model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketDAO implements DAO<Basket, Integer> {

    private static final String INSERT = "INSERT INTO basket (finalPrice, id_client, basketState) VALUES (?, ?, ?)";
    private static final String INSERTMODELS = "INSERT INTO modelBasketRelation (id_basket,id_model) VALUES (?,?)";
    private static final String UPDATEFINALPRICE = "UPDATE basket SET finalPrice = ? WHERE id = ?";
    private static final String UPDATE = "DELETE FROM modelBasketRelation WHERE id_basket=?";
    private static final String DELETE = "DELETE FROM basket WHERE id = ?";
    private static final String DELETEMODELFROMBASKET = "DELETE FROM modelBasketRelation WHERE id_basket = ? AND id_model = ?";
    private static final String FINDBYID = "SELECT id, finalPrice, id_client, basketState FROM basket WHERE id = ?";
    //private static final String FINDALL = "SELECT id, finalPrice, model FROM basket";
    //private static final String FINDBYNAME = "SELECT id, finalPrice, model FROM basket WHERE name LIKE ?";
    private static final String FINDBYCLIENT = "SELECT id, finalPrice, basketState FROM basket WHERE id_client = ?";
    private static final String FINDMODELSBYBASKET = "SELECT m.id, m.name, m.price FROM model m, modelBasketRelation mb WHERE m.id = mb.id_model AND mb.id_basket = ?";


    private Connection conn;

    public BasketDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Basket save(Basket basket) {
        if (basket != null && basket.getId() <= 0) {
            try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                if (basket.getClient() != null) {
                    pst.setDouble(1, basket.getFinalPrice());
                    pst.setInt(2, basket.getClient().getId());
                    pst.setString(3, basket.getBasketState().name());
                    pst.executeUpdate();
                    ResultSet resultSet = pst.getGeneratedKeys();
                    if (resultSet.next()) {
                        basket.setId(resultSet.getInt(1));
                    }
                }

                HashMap<Integer, Model> basketModels = basket.getModels();
                if (basketModels != null) {
                    for (Map.Entry<Integer, Model> entry : basketModels.entrySet()) {
                        saveModelsInBasket(basket, entry.getValue());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            try (PreparedStatement pst = conn.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS)) {
                pst.setDouble(1, basket.getId());

                pst.executeUpdate();
                ResultSet resultSet = pst.getGeneratedKeys();
                if (resultSet.next()) {
                    basket.setId(resultSet.getInt(1));
                }

                HashMap<Integer, Model> basketModels = basket.getModels();
                if (basketModels != null) {
                    for (Map.Entry<Integer, Model> entry : basketModels.entrySet()) {
                        saveModelsInBasketUpdate(basket, entry.getValue());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return basket;
    }


    public boolean saveModelsInBasket(Basket basket, Model model) {
        boolean result = false;
        if (!basket.getModels().containsKey(model.getId())) {
            try (PreparedStatement pst2 = conn.prepareStatement(INSERTMODELS)) {
                pst2.setInt(1, basket.getId());
                pst2.setInt(2, model.getId());
                pst2.executeUpdate();
                result = true;

                basket.getModels().put(model.getId(), model);
                basket.setFinalPrice(calculateFinalPriceFromModels(basket));
                updateFinalPrice(basket);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            basket.setFinalPrice(calculateFinalPriceFromModels(basket));
        }
        return result;
    }


    public boolean saveModelsInBasketUpdate(Basket basket, Model model) {
        boolean result = false;
        if (basket.getModels().containsKey(model.getId())) {
            try (PreparedStatement pst2 = conn.prepareStatement(INSERTMODELS)) {
                pst2.setInt(1, basket.getId());
                pst2.setInt(2, model.getId());
                pst2.executeUpdate();
                result = true;

                basket.getModels().put(model.getId(), model);
                basket.setFinalPrice(calculateFinalPriceFromModels(basket));
                updateFinalPrice(basket);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            basket.setFinalPrice(calculateFinalPriceFromModels(basket));
        }
        return result;
    }


    public double calculateFinalPriceFromModels(Basket basket) {
        double totalPrice = 0;
        HashMap<Integer, Model> basketModels = basket.getModels();
        if (basketModels != null) {
            for (Map.Entry<Integer, Model> entry : basketModels.entrySet()) {
                Model model = entry.getValue();
                totalPrice += model.getPrice();
            }
        }
        return totalPrice;
    }

    public void updateFinalPrice(Basket basket) {
        try (PreparedStatement pst = conn.prepareStatement(UPDATEFINALPRICE)) {
            pst.setDouble(1, basket.getFinalPrice());
            pst.setInt(2, basket.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public boolean deleteModelFromBasket(Basket basket, Model model){
        boolean result = false;
        //result = basket.removeModel(model); // Utilizar el método removeModel() de Basket
        if (basket != null && basket.getId() != 0 && model != null) {
            result = true;
            if (result) {
                try (PreparedStatement pst = conn.prepareStatement(DELETEMODELFROMBASKET)) {
                    pst.setInt(1, basket.getId());
                    pst.setInt(2, model.getId());
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
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

    public static BasketDAO build() {
        return new BasketDAO();
    }

    public Basket findBasketByClient(Client client) {
        Basket basket = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYCLIENT)) {
            pst.setInt(1, client.getId());
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                basket = new Basket();
                basket.setId(resultSet.getInt("id"));
                basket.setFinalPrice(resultSet.getDouble("finalPrice"));
                basket.setBasketState(BasketState.valueOf(resultSet.getString("basketState")));

                HashMap<Integer, Model> includedModels = new HashMap<>();
                try (PreparedStatement pstInclude = conn.prepareStatement(FINDMODELSBYBASKET)) {
                    pstInclude.setInt(1, basket.getId());
                    ResultSet resultSetInclude = pstInclude.executeQuery();
                    while (resultSetInclude.next()) {
                        Model model = new Model();
                        model.setId(resultSetInclude.getInt("id"));
                        model.setName(resultSetInclude.getString("name"));
                        model.setPrice(resultSetInclude.getDouble("price"));
                        includedModels.put(model.getId(), model);
                    }
                }
                basket.setModels(includedModels);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return basket;
    }

}


