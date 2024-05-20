package org.example.model.dao;

import org.example.model.connection.ConnectionMariaDB;
import org.example.model.entity.Client;
import org.example.model.entity.Modeler;
import org.example.model.entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) implementation for handling CRUD operations on the User entity.
 *
 * @param <T> the type of User entity (Modeler or Client)
 */
public class UserDAO<T extends User> implements DAO<User, Integer> {
    private T _type;

    private static String INSERT = "INSERT INTO TABLE (user, password, name, surname, email, bornDate, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static String UPDATE = "UPDATE TABLE SET user = ?, password = ?, email = ?, bornDate = ?, image = ? WHERE id=? ";
    private static String FINDBYID = "SELECT x.id, x.user, x.bornDate FROM TABLE AS x WHERE x.id=?";
    private static String FINDUSERBYUSER = "SELECT x.id, x.user, x.password, x.name, x.surname, x.email, x.bornDate, x.image FROM TABLE AS x WHERE x.user = ?";
    private static String FINDUSERBYEMAIL = "SELECT x.id, x.user FROM TABLE AS x WHERE x.email = ?";
    private static String FINDPASSWORDBYUSER = "SELECT x.id, x.user, x,password FROM TABLE AS x WHERE x.password = ?";
    private static String FINDALLUSER = "SELECT x.id, x.user FROM TABLE AS x";
    private static String DELETE = "DELETE FROM TABLE WHERE id = ?";

    /**
     * Constructs a UserDAO instance for the given entity type.
     *
     * @param clazz the class type of the entity (Modeler or Client)
     */
    public UserDAO(Class<T> clazz) {
        try {
            this._type = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the given user entity to the database.
     *
     * @param entity the user entity to save
     * @return the saved user entity
     */
    @Override
    public User save(User entity) {
        User result = entity;
        if (entity == null) return result;
        if (entity.getId() == null) {
            String insertQuery = INSERT.replace("TABLE", getTableName());
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, entity.getUser());
                pst.setString(2, entity.getPassword());
                pst.setString(3, entity.getName());
                pst.setString(4, entity.getSurname());
                pst.setString(5, entity.getEmail());
                pst.setDate(6, Date.valueOf(entity.getBornDate()));
                pst.setBytes(7, entity.getImage());
                pst.executeUpdate();
                ResultSet resultSet = pst.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setId(resultSet.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
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
                    pst.setDate(4, Date.valueOf(entity.getBornDate()));
                } else {
                    pst.setNull(4, Types.DATE);
                }
                pst.setBytes(5, entity.getImage());
                pst.setInt(6, entity.getId());
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

    /**
     * Returns the table name based on the user type.
     *
     * @return the table name
     */
    private String getTableName() {
        if (_type instanceof Modeler) {
            return "Modeler";
        } else {
            return "Client";
        }
    }

    /**
     * Deletes the given user from the database.
     *
     * @param entity the user to delete
     * @return the deleted user
     */
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

    /**
     * Finds a user by its primary key.
     *
     * @param key the primary key of the user to find
     * @return the found user, or null if no user was found
     */
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
        } else if (_type instanceof Client) {
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

    /**
     * Finds all users in the database.
     *
     * @return a list of all users
     */
    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        if (_type instanceof Modeler) {
            FINDALLUSER = FINDALLUSER.replaceAll("TABLE", "Modeler");
        } else if (_type instanceof Client) {
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
                } else if (_type instanceof Client) {
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

    /**
     * Finds a user by its username.
     *
     * @param key the username of the user to find
     * @return the found user, or null if no user was found
     */
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
                    result.setPassword(res.getString("password"));
                    result.setName(res.getString("name"));
                    result.setSurname(res.getString("surname"));
                    result.setEmail(res.getString("email"));
                    result.setBornDate(res.getDate("bornDate").toLocalDate());
                    result.setImage(res.getBytes("image"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (_type instanceof Client) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDUSERBYUSER.replace("TABLE", "Client"))) {
                pst.setString(1, key);
                ResultSet res = pst.executeQuery();
                if (res.next()) {
                    result = new Client();
                    result.setId(res.getInt("id"));
                    result.setUser(res.getString("user"));
                    result.setPassword(res.getString("password"));
                    result.setName(res.getString("name"));
                    result.setSurname(res.getString("surname"));
                    result.setEmail(res.getString("email"));
                    Date bornDate = res.getDate("bornDate");
                    result.setImage(res.getBytes("image"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        FINDUSERBYUSER = FINDUSERBYUSER.replaceAll("Modeler", "TABLE");
        FINDUSERBYUSER = FINDUSERBYUSER.replaceAll("Client", "TABLE");
        return result;
    }

    /**
     * Finds a user by its email.
     *
     * @param key the email of the user to find
     * @return the found user, or null if no user was found
     */
    public User findUserByEmail(String key) {
        User result = null;
        if (_type instanceof Modeler) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDUSERBYEMAIL.replace("TABLE", "Modeler"))) {
                pst.setString(1, key);
                ResultSet res = pst.executeQuery();
                if (res.next()) {
                    result = new Modeler();
                    result.setId(res.getInt("id"));
                    result.setEmail(res.getString("email"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (_type instanceof Client) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDUSERBYEMAIL.replace("TABLE", "Client"))) {
                pst.setString(1, key);
                ResultSet res = pst.executeQuery();
                if (res.next()) {
                    result = new Client();
                    result.setId(res.getInt("id"));
                    result.setEmail(res.getString("email"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        FINDUSERBYEMAIL = FINDUSERBYEMAIL.replaceAll("Modeler", "TABLE");
        FINDUSERBYEMAIL = FINDUSERBYEMAIL.replaceAll("Client", "TABLE");
        return result;
    }

    /**
     * Closes the DAO, releasing any resources it holds.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {

    }

    /**
     * Creates a new instance of UserDAO specialized for handling Modeler entities.
     *
     * @return a UserDAO instance for Modeler entities
     */
    public static UserDAO<Modeler> buildModeler() {
        return new UserDAO<Modeler>(Modeler.class);
    }

    /**
     * Creates a new instance of UserDAO specialized for handling Client entities.
     *
     * @return a UserDAO instance for Client entities
     */
    public static UserDAO<Client> buildClient() {
        return new UserDAO<Client>(Client.class);
    }
}


