package com.nancy.booking.show.service;

import com.nancy.booking.show.model.UserDTO;

public interface UserService {
    void createUser(UserDTO userDTO);

    UserDTO findUser(String userName, String password);
}
