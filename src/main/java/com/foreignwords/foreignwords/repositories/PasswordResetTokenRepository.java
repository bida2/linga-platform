package com.foreignwords.foreignwords.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foreignwords.foreignwords.entities.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	PasswordResetToken findByToken(String token);
}
