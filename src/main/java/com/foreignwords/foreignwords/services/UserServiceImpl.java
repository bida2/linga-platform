package com.foreignwords.foreignwords.services;

import java.sql.Date;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.foreignwords.foreignwords.entities.PasswordResetToken;
import com.foreignwords.foreignwords.entities.User;
import com.foreignwords.foreignwords.repositories.PasswordResetTokenRepository;
import com.foreignwords.foreignwords.repositories.RoleRepository;
import com.foreignwords.foreignwords.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PasswordResetTokenRepository passResetRepo;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }
    
    @Override
    public User findUserByEmail(String email) {
    	return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user with given e-mail adress!"));
    }
    
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No username found"));
    }

	@Override
	public Optional<User> findUserByResetToken(String resetToken) {
		return userRepository.findByResetToken(resetToken);
	}

	@Override
	public void createUserPasswordResetToken(User user, String token, Date expiryDate) {
		PasswordResetToken myToken = new PasswordResetToken(user, token, expiryDate);
		passResetRepo.save(myToken);
	}
}