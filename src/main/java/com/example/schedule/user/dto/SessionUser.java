package com.example.schedule.user.dto;

import com.example.schedule.user.entity.User;
import lombok.Getter;

@Getter
public class SessionUser {
    private final Long id;
    private final String name;
    private final String email;
    private final String password;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
