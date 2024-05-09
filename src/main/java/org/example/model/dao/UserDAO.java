package org.example.model.dao;

import org.example.model.connection.ConnectionMariaDB;
import org.example.model.entity.Client;
import org.example.model.entity.Modeler;
import org.example.model.entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO<T extends User> implements DAO<User, Integer> {
    private T _type;
    private static String INSERT = "INSERT INTO TABLE (user, password, name, surname, email, bornDate, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static String UPDATE = "UPDATE TABLE SET user = ?, password = ?, image = ? WHERE id=? ";
    private final static String FINDBYID = "SELECT x.id, x.name, x.price, x.id_modeler, x.user_modeler FROM TABLE AS x WHERE x.id=?";
    private final static String FINDUSERBYUSER = "SELECT x.id, x.user FROM TABLE AS x WHERE x.user = ?";
    private static String FINDALLUSER = "SELECT x.id, x.user FROM TABLE AS x";
    private static String DELETE = "DELETE FROM TABLE WHERE id = ?";

    public UserDAO(Class<T> clazz) {
        try {
            this._type = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User save(User entity) {
        User result = entity;
        if (entity == null) return result;
        if (entity.getId() == null) {
            //INSERT
            if (entity instanceof Modeler) {
                INSERT = INSERT.replaceAll("TABLE", "Modeler");
                UPDATE = UPDATE.replaceAll("TABLE", "Modeler");
            } else {
                INSERT = INSERT.replaceAll("TABLE", "Client");
                UPDATE = UPDATE.replaceAll("TABLE", "Client");
            }
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

                pst.setString(1, entity.getUser());
                pst.setString(2, entity.getPassword());
                pst.setString(3, entity.getName());
                pst.setString(4, entity.getSurname());
                pst.setString(5, entity.getEmail());
                pst.setDate(6, Date.valueOf(entity.getBornDate()));
                pst.setString(7, entity.getImage());
                pst.executeUpdate();

                //Si fuera autoincremental yo tendría que leer getGeneratedKeys() -> setId
                ResultSet resultSet = pst.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setId(resultSet.getInt(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //UPDATE
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                pst.setString(1, entity.getUser());
                pst.setString(2, entity.getPassword());
                pst.setString(3, entity.getName());
                pst.setString(4, entity.getSurname());
                pst.setString(5, entity.getEmail());
                pst.setDate(6, Date.valueOf(entity.getBornDate()));
                pst.setString(7, entity.getImage());
                pst.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        INSERT = INSERT.replaceAll("Modeler", "TABLE");
        UPDATE = UPDATE.replaceAll("Modeler", "TABLE");
        INSERT = INSERT.replaceAll("Client", "TABLE");
        UPDATE = UPDATE.replaceAll("Client", "TABLE");

        return result;
    }

    @Override
    public User delete(User entity) {
        if (entity == null || entity.getId() == null) return entity;
        if (entity instanceof Modeler) {
            DELETE = DELETE.replaceAll("TABLE", "Modeler");
        } else {
            DELETE = DELETE.replaceAll("TABLE", "Client");
        }
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setInt(1, entity.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            entity = null;
        }
        return entity;
    }

    @Override
    public User findById(Integer key) {
        User result = null;

        if (_type instanceof Modeler) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID.replace("TABLE", "Modeler"))) {
                pst.setInt(1, key);
                ResultSet res = pst.executeQuery();
                if (res.next()) {
                    result = new Modeler();
                    result.setId(res.getInt("Id"));
                    result.setUser(res.getString("User"));
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID.replace("TABLE", "Client"))) {
                pst.setInt(1, key);
                ResultSet res = pst.executeQuery();
                if (res.next()) {
                    result = new Client();
                    result.setId(res.getInt("Id"));
                    result.setUser(res.getString("User"));
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        if (_type instanceof Modeler) {
            FINDALLUSER = FINDALLUSER.replaceAll("TABLE", "Modeler");
        } else {
            FINDALLUSER = FINDALLUSER.replaceAll("TABLE", "Client");
        }
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALLUSER)) {
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                if (_type instanceof Modeler) {
                    Modeler mo = new Modeler();
                    mo.setId(res.getInt("id"));
                    mo.setUser(res.getString("user"));
                    result.add(mo);
                } else {
                    Client cli = new Client();
                    cli.setId(res.getInt("id"));
                    cli.setUser(res.getString("user"));
                    result.add(cli);
                }
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        FINDALLUSER = FINDALLUSER.replaceAll("Modeler", "TABLE");
        FINDALLUSER = FINDALLUSER.replaceAll("Client", "TABLE");

        return result;
    }

    public User findUserByUser(String key) {
        User result = null;
        if (_type instanceof Modeler) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDUSERBYUSER.replace("TABLE", "Modeler"))) {
                pst.setString(1, key);
                ResultSet res = pst.executeQuery();
                if (res.next()) {
                    result = new Modeler();
                    result.setId(res.getInt("id"));
                    result.setUser(res.getString("user"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDUSERBYUSER.replace("TABLE", "Client"))) {
                pst.setString(1, key);
                ResultSet res = pst.executeQuery();
                if (res.next()) {
                    result = new Client();
                    result.setId(res.getInt("id"));
                    result.setUser(res.getString("user"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return result;
    }

    @Override
    public void close() throws IOException {

    }


}


