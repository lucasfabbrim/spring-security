package br.com.lucas.modules.entities.user;

import br.com.lucas.modules.entities.role.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID user_id;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "role_id"))
    private Set<Role> role;

    @CreationTimestamp
    private Instant createTimestamp;

    public User(String username, String password, Set<Role> role) {
        this.user_id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.role = role;
        this.createTimestamp = Instant.now();
    }

}
