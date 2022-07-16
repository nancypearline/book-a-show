package com.nancy.booking.show.service;

import com.nancy.booking.show.dao.BaseDAO;

import java.util.Optional;

public abstract class BaseService<T> {
    BaseDAO<T> dao;

    protected BaseService(BaseDAO<T> dao) {
        this.dao = dao;
    }

    public T findById(Long id) {
        final Optional<T> optional = dao.findById(id);
        if(optional.isPresent())
            return optional.get();
        return null;
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
