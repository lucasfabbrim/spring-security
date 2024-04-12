package br.com.lucas.modules.entities.role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;
    private String name;

    public Role(String name) {
        this.name = name;
    }

    @Getter
    public enum Type{
        MEMBER("member"),
        ADMINISTRATOR("admin");
        private final String name;

        Type(String name) {
            this.name = name;
        }
    }

}
