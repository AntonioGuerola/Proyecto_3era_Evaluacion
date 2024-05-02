/**package org.example.model.dao;

import org.example.model.connection.ConnectionMariaDB;
import org.example.model.entity.Model;
import org.example.model.entity.ModelCategory;
import org.example.model.entity.Modeler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDAO implements DAO<Model, String> {
    private static final String INSERT = "INSERT INTO Model (name, price, description, rating, image, model, category, modeler) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Model SET name = ?, price = ?, description = ?, image = ?  WHERE id=?";
    private static final String FINDALL = "SELECT mo.id, mo.name, mo.price, mo.description, mo.rating, mo.image, mo.model, mo.category, mo.modeler FROM Model AS mo";
    private static final String FINDBYID = "SELECT mo.id, mo.name, mo.price, mo.description, mo.rating, mo.image, mo.model, mo.category, mo.modeler FROM Model AS mo WHERE mo.id=?";
    private static final String DELETE = "DELETE FROM Model WHERE id=?";
    private static final String FINDBYMODELER = "SELECT mo.id, mo.name, mo.price, mo.description, mo.rating, mo.image, mo.model, mo.category, mo.modeler FROM Model AS mo WHERE mo.modeler = ?";
    private Connection conn;

    public ModelDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Model save(Model entity) {
        Model result = entity;
        if (entity != null) {
            Integer id = entity.getId();
            if (id != null) {
                Model isInDataBase = findById(id);
                if (isInDataBase == null) {
                    //INSERT
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1, entity.getName());
                        pst.setDouble(2, entity.getPrice());
                        pst.setString(3, entity.getDescription());

                        private int id;
                        private String name;
                        private double price;
                        private String description;
                        private double rating;
                        private String image;
                        private String model;
                        private ModelCategory category;
                        private Modeler modeler;

                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    //UPDATE
                    try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setString(1, entity.getTitle());
                        pst.setString(2, entity.getIsbn());
                        //faltan líneas
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return result;
    }

    @Override
    public Model delete(Model entity) {
        try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
            pst.setString(1, entity.getIsbn());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            entity = null;
        }
        return entity;
    }

    @Override
    public Model findById(Integer key) {
        Model result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setString(1, key);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Model b = new Model();
                    b.setIsbn(res.getString("isbn"));
                    //Eager
                    b.setAuthor(AuthorDAO.build().findById(res.getString("id_author")));
                    b.setTitle(res.getString("title"));
                    result = b;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Model> findAll() {
        List<Model> result = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Model b = new Model();
                    b.setIsbn(res.getString("isbn"));
                    //b.setAuthor(res.getString("author"));
                    b.setTitle(res.getString("title"));
                    result.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Model> findByAuthor(Author a) {
        List<Model> result = new ArrayList<>();
        if (a == null || a.getDni() == null) return result;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYAUTHOR)) {
            pst.setString(1, a.getDni());
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Model b = new Model();
                    b.setIsbn(res.getString("isbn"));
                    b.setAuthor(a);
                    b.setTitle(res.getString("title"));
                    result.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws IOException {

    }

    public static ModelDAO build(){
        return new ModelDAO();
    }

}
*/