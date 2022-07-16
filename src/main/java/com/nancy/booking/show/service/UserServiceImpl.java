package com.nancy.booking.show.service;

import com.nancy.booking.show.dao.BaseDAO;
import com.nancy.booking.show.dao.UserDAO;
import com.nancy.booking.show.entity.User;
import com.nancy.booking.show.util.EntityTransformer;
import com.nancy.booking.show.model.UserDTO;
import com.nancy.booking.show.util.BookingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends BaseService<User> implements UserService{

    public UserServiceImpl(BaseDAO<User> dao) {
        super(dao);
    }

    @Autowired
    private UserDAO userDAO;

    @Override
    public void createUser(UserDTO userDTO) {
        if(userDTO != null && BookingUtil.isNotEmpty(userDTO.getUsername())) {
            userDAO.save(EntityTransformer.toEntity(userDTO, new User()));
        }
    }

    @Override
    public UserDTO findUser(String userName, String password) {
        return EntityTransformer.toDto(userDAO.findByUsernameAndPassword(userName, password), new UserDTO());
    }

}
