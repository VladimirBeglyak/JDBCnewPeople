package DAO;

import java.util.List;

public interface BaseDao <T> {
    boolean add(T t);

    boolean delete(T t);

    T findById(int id);

    List<T> findAll();

    boolean update(T t,int roleId,int jobId);
}
