package com.nemgut.psresale.services;

import com.nemgut.psresale.model.entities.User;
import com.nemgut.psresale.model.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDao;

	@Autowired
	MailService mailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User registry(User user) {
		user.setActivated(false);
		user.setActivationCode(UUID.randomUUID().toString());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		mailService.sendGreetingMessage(user);
		return userDao.save(user);
	}

	public boolean activateUser(String activationCode) {
		System.out.println("Пытаемся активировать");
		Optional<User> u = userDao.findByActivationCode(activationCode);
		if(u.isPresent()) {
			User user = u.get();
			user.setActivationCode(null);
			user.setActivated(true);
			userDao.save(user);
			return true;
		}
		return false;
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id).orElse(null);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findFirtsByEmail(email);
	}

	@Override
	public Optional<User> findByActivationCode(String activationCode) {
		return userDao.findByActivationCode(activationCode);
	}
}
