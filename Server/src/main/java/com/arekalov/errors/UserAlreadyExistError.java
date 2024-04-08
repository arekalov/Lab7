package com.arekalov.errors;

public class UserAlreadyExistError extends Exception{
    public UserAlreadyExistError(String answer) {
        super(answer);
    }
}
