package com.nancy.booking.show.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@ToString
public class UserRoleDTO implements Serializable {

    private static final long serialVersionUID = -4529295548607265809L;

    private Long userRoleId;

    private LocalDateTime createdDateTime;

    private LocalDateTime lastUpdatedDateTime;

    private Role role;

    private Set<UserDTO> users;
}
