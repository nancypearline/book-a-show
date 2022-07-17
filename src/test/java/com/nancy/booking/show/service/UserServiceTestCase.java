package com.nancy.booking.show.service;

import com.nancy.booking.show.AbstractTestCase;
import com.nancy.booking.show.dao.UserDAO;
import com.nancy.booking.show.entity.User;
import com.nancy.booking.show.model.Role;
import com.nancy.booking.show.model.UserDTO;
import com.nancy.booking.show.util.EntityTransformer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class UserServiceTestCase extends AbstractTestCase {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    public UserService userService;

    private User user;

    private UserDTO userDTO;

    @Before
    public void setUp() {
        userDTO = new UserDTO();
        userDTO.setActive(true);
        userDTO.setPassword("alex");
        userDTO.setUsername("alex");
        userDTO.setRole(Role.ADMIN);
        user = EntityTransformer.toEntity(userDTO, new User());
        when(userDAO.findByUsernameAndPassword("alex", "alex")).thenReturn(user);
    }

    @Test
    public void testCreate() {
        userService.createUser(userDTO);
        verify(userDAO, times(1)).save(user);
    }

    @Test
    public void testFindUser() {
        UserDTO userDTO = userService.findUser("alex", "alex");
        assertNotNull(userDTO);
        assertEquals("alex", userDTO.getUsername());
        assertEquals("alex", userDTO.getPassword());
        assertTrue(userDTO.isActive());
    }
}
