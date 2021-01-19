package com.foreignwords.foreignwords.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foreignwords.foreignwords.entities.User;


public interface UserRepository extends JpaRepository<User, Long> {
	 Optional<User> findByUsername(String username);
	 User getById(Long id);
	 Optional<User> findByEmail(String email);
	 Optional<User> findByResetToken(String resetToken);
}
