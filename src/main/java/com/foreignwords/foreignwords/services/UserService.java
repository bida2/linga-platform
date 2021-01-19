package com.foreignwords.foreignwords.services;



import java.sql.Date;
import java.util.Optional;

import com.foreignwords.foreignwords.entities.User;


public interface UserService {
	void save(User user);
	void createUserPasswordResetToken(User user, String token, Date expiryDate);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    public Optional<User> findUserByResetToken(String resetToken);
}
