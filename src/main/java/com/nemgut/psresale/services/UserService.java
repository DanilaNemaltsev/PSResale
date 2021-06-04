
package com.nemgut.psresale.services;

import com.nemgut.psresale.model.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService {
	
	public User registry(User user);
	
	public User findById(Long id);
	
	public User findByEmail(String email);

	public Optional<User> findByActivationCode(String activationCode);
}
