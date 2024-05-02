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
    private final static String INSERT = "INSERT INTO ? (user, password, name, surname, mail, bornDate, img) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE ? SET user = ?, password = ?, img = ? WHERE id=? ";
    private final static String FINDUSERBYID = "SELECT x.id, x.user FROM ? AS x WHERE x.id = ?";
    private final static String FINDALLUSER = "SELECT x.id, x.user FROM ? AS x";
    private final static String DELETE = "DELETE FROM ? WHERE id = ?";

    @Override
    public User save(User entity) {
        User result = entity;
        if (entity == null) return result;
        if (entity.getId() == null) {
            //INSERT
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                if (entity instanceof Modeler) {
                    pst.setString(1, "modeler");
                } else {
                    pst.setString(1, "client");
                }
                pst.setString(2, entity.getUser());
                pst.setString(3, entity.getPassword());
                pst.setString(4, entity.getName());
                pst.setString(5, entity.getSurname());
                pst.setString(6, entity.getMail());
                pst.setDate(7, Date.valueOf(entity.getBornDate()));
                pst.setString(8, entity.getImg());
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
                if (entity instanceof Modeler) {
                    pst.setString(1, "modeler");
                } else {
                    pst.setString(1, "client");
                }
                pst.setString(2, entity.getUser());
                pst.setString(3, entity.getPassword());
                pst.setString(4, entity.getName());
                pst.setString(5, entity.getSurname());
                pst.setString(6, entity.getMail());
                pst.setDate(7, Date.valueOf(entity.getBornDate()));
                pst.setString(8, entity.getImg());
                pst.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public User delete(User entity) {
        if (entity == null || entity.getId() == null) return entity;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            if (entity instanceof Modeler) {
                pst.setString(1, "modeler");
            } else {
                pst.setString(1, "client");
            }
            pst.setInt(2, entity.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            entity = null;
        }
        return entity;
    }

    @Override
    public User findById(Integer key) {
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALLUSER)) {
            if (_type instanceof Modeler) {
                pst.setString(1, "modeler");
            } else {
                pst.setString(1, "client");
            }

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
        return result;
    }

    public User findUserById(Integer key) {
        User result = null;

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDUSERBYID)) {
            if (_type instanceof Modeler) {
                pst.setString(1, "modeler");
            } else {
                pst.setString(1, "client");
            }
            pst.setInt(2, key);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result.setId(res.getInt("id"));
                result.setUser(res.getString("user"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws IOException {

    }

    public static UserDAO build() {
        return new UserDAO();
    }
}


