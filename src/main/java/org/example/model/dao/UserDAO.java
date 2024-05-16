package org.example.model.dao;

import org.example.model.connection.ConnectionMariaDB;
import org.example.model.entity.Basket;
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
    private static String UPDATE = "UPDATE TABLE SET user = ?, password = ?, bornDate = ?, image = ? WHERE id=? ";
    private static String FINDBYID = "SELECT x.id, x.user, x.bornDate FROM TABLE AS x WHERE x.id=?";
    private static String FINDUSERBYUSER = "SELECT x.id, x.user FROM TABLE AS x WHERE x.user = ?";
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
            String insertQuery = INSERT.replace("TABLE", getTableName());
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

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
            String updateQuery = UPDATE.replace("TABLE", getTableName());
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(updateQuery)) {
                pst.setString(1, entity.getUser());

                if (entity.getPassword() != null) {
                    pst.setString(2, entity.getPassword());
                } else {
                    pst.setNull(2, Types.INTEGER);
                }

                pst.setString(3, entity.getEmail());

                if (entity.getBornDate() != null) {
                    pst.setDate(3, Date.valueOf(entity.getBornDate()));
                } else {
                    pst.setNull(3, Types.DATE);
                }

                pst.setString(4, entity.getImage());
                pst.setInt(5, entity.getId());
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

    private String getTableName() {
        if (_type instanceof Modeler) {
            return "Modeler";
        } else {
            return "Client";
        }
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

        DELETE = DELETE.replaceAll("Modeler", "TABLE");
        DELETE = DELETE.replaceAll("Client", "TABLE");

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
                    Date bornDate = res.getDate("bornDate");
                    if (bornDate != null) {
                        result.setBornDate(bornDate.toLocalDate());
                    }
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
                    Date bornDate = res.getDate("bornDate");
                    if (bornDate != null) {
                        result.setBornDate(bornDate.toLocalDate());
                    }
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        FINDBYID = FINDBYID.replaceAll("Modeler", "TABLE");
        FINDBYID = FINDBYID.replaceAll("Client", "TABLE");

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

        FINDUSERBYUSER = FINDUSERBYUSER.replaceAll("Modeler", "TABLE");
        FINDUSERBYUSER = FINDUSERBYUSER.replaceAll("Client", "TABLE");

        return result;
    }

    @Override
    public void close() throws IOException {

    }

}


