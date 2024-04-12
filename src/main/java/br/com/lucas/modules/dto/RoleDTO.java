package br.com.lucas.modules.dto;

import br.com.lucas.modules.entities.role.Role;

public record RoleDTO(
        String name) {

    public Role convertToRole(){
        return new Role(
                name
        );
    }

}
