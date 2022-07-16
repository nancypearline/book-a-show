package com.nancy.booking.show.dao;

import com.nancy.booking.show.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends BaseDAO<User>{
    User findByUsernameAndPassword(String userName, String password);
}
