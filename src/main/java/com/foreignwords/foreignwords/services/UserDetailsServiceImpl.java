package com.foreignwords.foreignwords.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foreignwords.foreignwords.entities.Role;
import com.foreignwords.foreignwords.entities.User;
import com.foreignwords.foreignwords.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        	User user = userRepository.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException("No username found"));
            for (Role role : user.getRoles()){
                  grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);	
    }
}