package br.com.lucas.modules.dto;

import br.com.lucas.modules.entities.role.Role;
import br.com.lucas.modules.entities.user.User;

import java.util.Set;

public record UserDTO(String username,
                      String password,
                      Set<Role> role) {

    public User convertToUser(){
        return new User(
                this.username,
                this.password,
                this.role
        );
    }
}
