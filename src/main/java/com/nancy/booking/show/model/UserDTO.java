package com.nancy.booking.show.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -176330603542099772L;

    private Long userId;

    private LocalDateTime createdDateTime;

    private LocalDateTime lastUpdatedDateTime;

    private String username;

    private String password;

    private Role role;

    private boolean isActive;

}
