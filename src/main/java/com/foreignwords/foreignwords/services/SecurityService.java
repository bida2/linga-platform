package com.foreignwords.foreignwords.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}