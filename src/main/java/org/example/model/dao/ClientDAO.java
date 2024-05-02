package org.example.model.dao;

import org.example.model.connection.ConnectionMariaDB;
import org.example.model.entity.Modeler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements DAO<Modeler> {
    private final static String INSERT = "INSERT INTO modeler (user, password, name, surname, mail, bornDate, img) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE modeler SET user = ?, password = ?, img = ? WHERE id=? ";
    private final static String FINDALL = "SELECT mo.id, mo.user FROM modeler AS mo";
    private final static String FINDBYID = "SELECT mo.id, mo.user FROM modeler AS mo WHERE mo.id = ?";
    private final static String DELETE = "DELETE FROM modeler WHERE id = ?";

    @Override
    public Modeler save(Modeler entity) {
        Modeler result = entity;
        if (entity == null) return result;
        if (entity.getId() == null) {
            //INSERT
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, entity.getUser());
                pst.setString(2, entity.getPassword());
                pst.setString(3, entity.getName());
                pst.setString(4, entity.getSurname());
                pst.setString(5, entity.getMail());
                pst.setDate(6, entity.getBornDate());
                pst.setString(7, entity.getImg());
                pst.executeUpdate();

                //Si fuera autoincremental yo tendría que leer getGeneratedKeys() -> setDni
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
                pst.setString(5, entity.getMail());
                pst.setDate(6, entity.getBornDate());
                pst.setString(7, entity.getImg());
                pst.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Modeler delete(Modeler entity) {
        if (entity == null || entity.getId() == null) return entity;
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
    public Modeler findById(Integer key) {
        Modeler result = null;

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            pst.setInt(1, key);
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
    public List<Modeler> findAll() {
        List<Modeler> result = new ArrayList<>();

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Modeler mo = new Modeler();
                mo.setId(res.getInt("id"));
                mo.setUser(res.getString("user"));
                result.add(mo);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws IOException {

    }

    public static ClientDAO build() {
        return new ClientDAO();
    }
}


