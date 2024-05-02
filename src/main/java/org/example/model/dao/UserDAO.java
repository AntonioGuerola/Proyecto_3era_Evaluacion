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
    private static String INSERT = "INSERT INTO TABLE (user, password, name, surname, email, born_date, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static String UPDATE = "UPDATE TABLE SET user = ?, password = ?, image = ? WHERE id=? ";
    private static String FINDUSERBYID = "SELECT x.id, x.user FROM TABLE AS x WHERE x.id = ?";
    private static String FINDALLUSER = "SELECT x.id, x.user FROM TABLE AS x";
    private static String DELETE = "DELETE FROM TABLE WHERE id = ?";

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
                pst.setDate(6, Date.valueOf(entity.getBorn_date()));
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
                pst.setDate(6, Date.valueOf(entity.getBorn_date()));
                pst.setString(7, entity.getImage());
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
        if (entity instanceof Modeler) {
            DELETE = DELETE.replaceAll("TABLE", "Modeler");
        } else {
            DELETE = DELETE.replaceAll("TABLE", "Client");
        }
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
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
        return result;
    }

    public User findUserById(Integer key) {
        User result = null;
        if (_type instanceof Modeler) {
            FINDALLUSER = FINDALLUSER.replaceAll("TABLE", "Modeler");
        } else {
            FINDALLUSER = FINDALLUSER.replaceAll("TABLE", "Client");
        }

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDUSERBYID)) {
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


