package br.com.lucas.modules.infra.security;

import br.com.lucas.modules.entities.user.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenGenerate {

    @Value("${jwt.private.key}")
    private String privateKey;

    public String generateKey(User user){
       try{

           var now = Instant.now();
           var expiresIn = 300L;

           Algorithm algorithm = Algorithm.HMAC256(privateKey);
           var token = JWT.create()
                   .withIssuer("security-auth")
                   .withSubject(user.getUsername())
                   .withExpiresAt(now.plusSeconds(expiresIn))
                   .sign(algorithm);
           return token;
       }catch (JWTCreationException e){
           return null;
       }
    }
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            return JWT.require(algorithm)
                    .withIssuer("security-auth")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTVerificationException exception) {
            return null;
        }
    }
}
