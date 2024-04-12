package br.com.lucas.modules.dto.auth;

public record AuthenticationRequest(String username,
                                    String password) {
}
