package com.nemgut.psresale.services;

import com.nemgut.psresale.model.entities.User;
import com.nemgut.psresale.model.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User registry(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userDao.save(user);
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id).orElse(null);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findFirtsByEmail(email);
	}
	
}
