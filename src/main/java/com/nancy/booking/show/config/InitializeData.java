package com.nancy.booking.show.config;

import com.nancy.booking.show.controller.UserController;
import com.nancy.booking.show.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitializeData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserController userController;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createNewUser("james", Role.BUYER.name(),"james");
        createNewUser("jack", Role.BUYER.name(),"jack");
        createNewUser("robert", Role.BUYER.name(),"robert");
        createNewUser("michael", Role.BUYER.name(),"michael");
        createNewUser("andrew", Role.BUYER.name(),"andrew");
        createNewUser("mark", Role.ADMIN.name(),"mark");
        createNewUser("sam", Role.ADMIN.name(),"sam");
        createNewUser("vinay", Role.ADMIN.name(),"vinay");
        createNewUser("mano", Role.ADMIN.name(),"mano");
        createNewUser("cathy", Role.ADMIN.name(),"cathy");
    }

    private void createNewUser(String userName, String userRole, String password) {
        userController.createUser(userName, userRole, password);
    }
}
