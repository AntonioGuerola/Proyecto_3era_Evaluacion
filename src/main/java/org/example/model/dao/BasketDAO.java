package org.example.model.dao;

import org.example.model.connection.ConnectionMariaDB;
import org.example.model.entity.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BasketDAO is a Data Access Object (DAO) class that provides CRUD operations
 * for the Basket entity in the database. It includes methods for saving, updating,
 * deleting, and finding Basket instances, as well as managing the relationship between
 * baskets and models.
 */
public class BasketDAO implements DAO<Basket, Integer> {
    private static final String INSERT = "INSERT INTO basket (finalPrice, id_client, basketState) VALUES (?, ?, ?)";
    private static final String INSERTMODELS = "INSERT INTO modelBasketRelation (id_basket,id_model) VALUES (?,?)";
    private static final String UPDATEFINALPRICE = "UPDATE basket SET finalPrice = ? WHERE id = ?";
    private static final String UPDATE = "DELETE FROM modelBasketRelation WHERE id_basket=?";
    private static final String DELETE = "DELETE FROM basket WHERE id = ?";
    private static final String DELETEMODELFROMBASKET = "DELETE FROM modelBasketRelation WHERE id_basket = ? AND id_model = ?";
    private static final String FINDBYID = "SELECT id, finalPrice, id_client, basketState FROM basket WHERE id = ?";
    private static final String FINDALL = "SELECT id, finalPrice, id_client, basketState FROM basket";
    private static final String FINDBYCLIENT = "SELECT id, finalPrice, basketState FROM basket WHERE id_client = ?";
    private static final String FINDMODELSBYBASKET = "SELECT m.id, m.name, m.price, m.description, m.rating, m.image, m.user_modeler FROM model m, modelBasketRelation mb WHERE m.id = mb.id_model AND mb.id_basket = ?";

    private Connection conn;

    /**
     * Constructs a BasketDAO object and initializes the database connection.
     */
    public BasketDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    /**
     * Saves the given Basket instance to the database. If the basket has an ID less than or equal to zero,
     * it inserts a new record. Otherwise, it updates the existing record.
     *
     * @param basket the Basket instance to save
     * @return the saved Basket instance with its ID updated if it was newly inserted
     */
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

    /**
     * Saves the relationship between the given Basket and Model instances in the database.
     * Updates the basket's final price and returns whether the operation was successful.
     *
     * @param basket the Basket instance
     * @param model the Model instance to add to the basket
     * @return true if the model was successfully added to the basket, false otherwise
     */
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

    /**
     * Updates the relationship between the given Basket and Model instances in the database.
     * Updates the basket's final price and returns whether the operation was successful.
     *
     * @param basket the Basket instance
     * @param model the Model instance to update in the basket
     * @return true if the model was successfully updated in the basket, false otherwise
     */
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

    /**
     * Calculates the total final price of all models in the given basket.
     *
     * @param basket the Basket instance
     * @return the total final price of all models in the basket
     */
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

    /**
     * Updates the final price of the given Basket instance in the database.
     *
     * @param basket the Basket instance with the updated final price
     */
    public void updateFinalPrice(Basket basket) {
        try (PreparedStatement pst = conn.prepareStatement(UPDATEFINALPRICE)) {
            pst.setDouble(1, basket.getFinalPrice());
            pst.setInt(2, basket.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the given Basket instance from the database.
     *
     * @param basket the Basket instance to delete
     * @return the deleted Basket instance
     */
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

    /**
     * Deletes the relationship between the given Basket and Model instances from the database.
     *
     * @param basket the Basket instance
     * @param model the Model instance to remove from the basket
     * @return true if the model was successfully removed from the basket, false otherwise
     */
    public boolean deleteModelFromBasket(Basket basket, Model model) {
        boolean result = false;
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

    /**
     * Finds and returns a Basket instance by its ID.
     *
     * @param key the ID of the Basket instance to find
     * @return the found Basket instance, or null if not found
     */
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Finds and returns a list of all Basket instances in the database.
     *
     * @return a list of all Basket instances
     */
    @Override
    public List<Basket> findAll() {
        UserDAO<Client> cliDAO = new UserDAO<>(Client.class);
        List<Basket> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Basket basket = new Basket();
                basket.setId(res.getInt("id"));
                basket.setFinalPrice(res.getDouble("finalPrice"));
                basket.setClient((Client) cliDAO.findById(res.getInt("id_client")));
                basket.setBasketState(BasketState.valueOf(res.getString("basketState")));
                result.add(basket);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Closes the database connection.
     *
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Constructs and returns a new BasketDAO instance.
     *
     * @return a new BasketDAO instance
     */
    public static BasketDAO build() {
        return new BasketDAO();
    }

    /**
     * Finds and returns a Basket instance by its associated client.
     *
     * @param client the Client instance associated with the Basket
     * @return the found Basket instance, or null if not found
     */
    public Basket findBasketByClient(Client client) {
        Basket basket = null;
        UserDAO<Modeler> uDAO = new UserDAO<>(Modeler.class);
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
                        model.setDescription(resultSetInclude.getString("description"));
                        model.setRating(resultSetInclude.getDouble("rating"));
                        model.setImage(resultSetInclude.getBytes("image"));
                        model.setModeler((Modeler) uDAO.findUserByUser(resultSetInclude.getString("user_modeler")));
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


