package com.nancy.booking.show.controller;

import com.nancy.booking.show.model.Role;
import com.nancy.booking.show.model.UserDTO;
import com.nancy.booking.show.service.UserService;
import com.nancy.booking.show.util.BookingUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@Slf4j
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * This method creates user during application startup(only for testing using in-memory db)
     * @param userName
     * @param userRole
     * @param password
     */
    public void createUser(String userName, String userRole, String password) {
        if(BookingUtil.isNotEmpty(userName) && BookingUtil.isNotEmpty(userRole) && BookingUtil.isNotEmpty(password)) {
            //TODO Encrypt and save password
            UserDTO userDTO = UserDTO.builder().username(userName).role(Role.valueOf(userRole))
                    .createdDateTime(LocalDateTime.now()).lastUpdatedDateTime(LocalDateTime.now()).isActive(true)
                    .password(password).build();
            userService.createUser(userDTO);
            logger.debug("User created with name {}", userName);
        } else {
            logger.error("User not created due to missing inputs");
        }
    }

    /**
     * Finds a user
     * @param userName
     * @param password
     * @return
     */
    public UserDTO findUser(String userName, String password) {
        if(BookingUtil.isNotEmpty(userName) && BookingUtil.isNotEmpty(password)) {
            return userService.findUser(userName, password);
        }
        return null;
    }

}
