package br.com.lucas.modules.exceptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(){
        super("User not found");
    }
}
