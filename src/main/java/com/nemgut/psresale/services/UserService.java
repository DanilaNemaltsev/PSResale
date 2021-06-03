
package com.nemgut.psresale.services;

import com.nemgut.psresale.model.entities.User;

public interface UserService {
	
	public User registry(User user);
	
	public User findById(Long id);
	
	public User findByEmail(String email);

}
