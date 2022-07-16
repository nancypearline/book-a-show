package com.nancy.booking.show.entity;

import com.nancy.booking.show.model.Role;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User extends BaseEntity {

    @Column(name="USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name="PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

}
