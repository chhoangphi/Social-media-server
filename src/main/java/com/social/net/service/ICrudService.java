package com.social.net.service;

import java.util.List;
import java.util.Optional;

public interface ICrudService<T, U> {
    List<T> getAll();

    Optional<T> getDataById(U id);

    Optional<T> insertData(T data);

    Optional<T> updateData(U id, T data);

    void deleteData(U id);
}
