package org.example.model.dao;

import org.example.model.connection.ConnectionMariaDB;
import org.example.model.entity.Model;
import org.example.model.entity.ModelCategory;
import org.example.model.entity.Modeler;
import org.example.model.entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelDAO implements DAO <Model, Integer>{
    private static final String INSERT ="INSERT INTO Model (name, price, description, rating, image, model, category, id_modeler, user_modeler) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE ="UPDATE Model SET name = ?, price = ?, description = ?, image = ? WHERE id = ?";
    private static final String FINDALL ="SELECT mo.id, mo.name, mo.price, mo.rating, mo.id_modeler, mo.user_modeler, mo.image FROM Model AS mo";
    private static final String FINDBYID ="SELECT mo.id, mo.name, mo.price, mo.id_modeler, mo.user_modeler FROM Model AS mo WHERE mo.id=?";
    private static final String FINDBYMODELER ="SELECT mo.id, mo.name, mo.price, mo.rating, mo.id_modeler, mo.user_modeler, mo.image FROM Model AS mo WHERE mo.id_modeler = ?";
    private static final String FINDBYNAME ="SELECT mo.id, mo.name, mo.price, mo.rating, mo.id_modeler, mo.user_modeler, mo.image FROM Model AS mo WHERE mo.name LIKE ?";
    private static final String DELETE ="DELETE FROM Model WHERE id = ?";

    private Connection conn;
    
    public ModelDAO(){
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Model save(Model model) {
        Model result = model;
        if(model!=null){
            Integer id= model.getId();
            if(id!=null){
                Model isInDataBase = findById(id);
                if(isInDataBase==null){
                    //INSERT
                    try(PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1,model.getName());
                        pst.setDouble(2,model.getPrice());
                        pst.setString(3,model.getDescription());
                        pst.setDouble(4,model.getRating());
                        pst.setBytes(5,model.getImage());
                        pst.setString(6,model.getModel());
                        pst.setString(7,model.getCategory().name());
                        pst.setInt(8,model.getModeler().getId());
                        pst.setString(9,model.getModeler().getUser());
                        pst.executeUpdate();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }else{
                    //UPDATE
                    try(PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setString(1,model.getName());
                        pst.setDouble(2,model.getPrice());
                        pst.setString(3,model.getDescription());
                        pst.setBytes(4,model.getImage());
                        pst.setInt(5, model.getId());
                        pst.executeUpdate();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Model delete(Model model) {
        if(model!=null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setInt(1, model.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                model = null;
            }
        }
        return model;
    }

    @Override
    public Model findById(Integer key) {
        UserDAO<Modeler> uDAO = new UserDAO<>(Modeler.class);
        Model result = null;
        try(PreparedStatement pst = conn.prepareStatement(FINDBYID)){
            pst.setInt(1,key);
            try(ResultSet res = pst.executeQuery()){
                if(res.next()){
                    Model model = new Model();
                    model.setId(res.getInt("id"));
                    model.setName(res.getString("name"));
                    model.setPrice(res.getDouble("price"));
                    model.setModeler((Modeler) uDAO.findById(res.getInt("id_modeler")));
                    model.setModeler((Modeler) uDAO.findUserByUser(res.getString("user_modeler")));
                    result = model;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Model> findAll() {
        UserDAO<Modeler> uDAO = new UserDAO<>(Modeler.class);
        ArrayList<Model> result = new ArrayList<>();
        try(PreparedStatement pst = conn.prepareStatement(FINDALL)){
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
                    Model model = new Model();
                    model.setId(res.getInt("id"));
                    model.setName(res.getString("name"));
                    model.setPrice(res.getDouble("price"));
                    model.setRating(res.getDouble("rating"));
                    //Eager
                    model.setModeler((Modeler) uDAO.findById(res.getInt("id_modeler")));
                    model.setModeler((Modeler) uDAO.findUserByUser(res.getString("user_modeler")));
                    model.setImage(res.getBytes("image"));
                    result.add(model);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }

    public List<Model> findByModeler(User user) {
        UserDAO<Modeler> uDAO = new UserDAO<>(Modeler.class);
        ArrayList<Model> result = new ArrayList<>();
        if(user ==null || user.getId()==null) return result;
        try(PreparedStatement pst = conn.prepareStatement(FINDBYMODELER)){
            pst.setInt(1, user.getId());
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
                    Model model = new Model();
                    model.setId(res.getInt("id"));
                    model.setName(res.getString("name"));
                    model.setPrice(res.getDouble("price"));
                    model.setRating(res.getDouble("rating"));
                    //Eager
                    model.setModeler((Modeler) uDAO.findById(res.getInt("id_modeler")));
                    model.setModeler((Modeler) uDAO.findUserByUser(res.getString("user_modeler")));
                    model.setImage(res.getBytes("image"));
                    result.add(model);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Model> findByName(String key) {
        UserDAO<Modeler> uDAO = new UserDAO<>(Modeler.class);
        ArrayList<Model> result = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FINDBYNAME)) {
            pst.setString(1, "%" + key + "%"); // Agregamos '%' para buscar nombres que contengan la palabra clave
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Model model = new Model();
                    model.setId(res.getInt("id"));
                    model.setName(res.getString("name"));
                    model.setPrice(res.getDouble("price"));
                    model.setRating(res.getDouble("rating"));
                    //Eager
                    model.setModeler((Modeler) uDAO.findById(res.getInt("id_modeler")));
                    model.setModeler((Modeler) uDAO.findUserByUser(res.getString("user_modeler")));
                    model.setImage(res.getBytes("image"));
                    result.add(model);
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
