package br.com.araujo.socialnetwork.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> getByName(String name);

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t, String[] params);

    void delete(T t);
}
