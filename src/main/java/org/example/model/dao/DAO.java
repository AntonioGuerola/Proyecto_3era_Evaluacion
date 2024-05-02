package org.example.model.dao;

import org.example.model.entity.Client;
import org.example.model.entity.Modeler;
import org.example.model.entity.User;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T, K> extends Closeable {
    T save(T entity);
    T delete(T entity) throws SQLException;
    T findById(K key);
    List<T> findAll();
}