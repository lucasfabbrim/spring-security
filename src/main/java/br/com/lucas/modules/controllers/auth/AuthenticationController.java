package br.com.lucas.modules.controllers.auth;

import br.com.lucas.modules.dto.UserDTO;
import br.com.lucas.modules.dto.auth.AuthenticationReponse;
import br.com.lucas.modules.dto.auth.AuthenticationRequest;
import br.com.lucas.modules.entities.role.Role;
import br.com.lucas.modules.entities.user.User;
import br.com.lucas.modules.exceptions.UserNotFound;
import br.com.lucas.modules.infra.security.TokenGenerate;
import br.com.lucas.modules.repositories.role.RoleRepository;
import br.com.lucas.modules.repositories.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping
public class AuthenticationController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerate tokenGenerate;

    @Autowired
    public AuthenticationController(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, TokenGenerate tokenService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerate = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationReponse> login(@Valid @RequestBody AuthenticationRequest dto){
        var user = this.userRepository.findByUsername(dto.username()).orElseThrow(() -> new UserNotFound());
        if(passwordEncoder.matches(dto.password(), user.getPassword())){
            var tokenAcess = this.tokenGenerate.generateKey(user);
            var reponse = new AuthenticationReponse(user.getUsername(), tokenAcess);
            return ResponseEntity.ok(reponse);
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody UserDTO dto){

        var role = this.roleRepository.findByName(Role.Type.MEMBER.getName());
        var user = this.userRepository.findByUsername(dto.username());

        if(!user.isPresent()){

            var newUser = dto.convertToUser();
            newUser.setUsername(dto.username());
            newUser.setPassword(passwordEncoder.encode(dto.password()));
            newUser.setRole(Set.of(role));
            this.userRepository.save(newUser);

            var tokenAcess = this.tokenGenerate.generateKey(newUser);
            var reponse = new AuthenticationReponse(newUser.getUsername(), tokenAcess);
            return ResponseEntity.ok(reponse);

        }
        return ResponseEntity.badRequest().build();
    }

}
