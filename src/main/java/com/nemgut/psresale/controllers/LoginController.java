package com.nemgut.psresale.controllers;

import com.nemgut.psresale.model.entities.User;
import com.nemgut.psresale.model.repositories.UserDAO;
import com.nemgut.psresale.services.MailService;
import com.nemgut.psresale.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.nemgut.psresale.upload.StorageService;

@Controller
public class LoginController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private StorageService storageService;

	@Autowired
	private UserDAO userRepo;

	@Autowired
	MailService mailService;

	@GetMapping("/")
	public String welcome() {
		return "redirect:/public/";
	}

	@GetMapping("/admin")
	public String admin(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "admin";
	}

	@PostMapping("/admin")
	public String admin(@RequestParam("idToDelete") String idToDelete) {
		System.out.println("!!!"+Long.parseLong(idToDelete));
		userRepo.deleteById(Long.parseLong(idToDelete));
		return "admin";
	}
	
	@GetMapping("/auth/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@GetMapping("/activate/{activationCode}")
	public String activateUser(Model model, @PathVariable String activationCode) {
		System.out.println("Пытаемся активировать контроллер");
		if(userService.activateUser(activationCode)) {
			model.addAttribute("message", "Пользователь активирован");
		} else {
			model.addAttribute("message", "Код активации не найден");
		}
		return "redirect:/public/";
	}

	@PostMapping("/auth/register")
	public String register(@ModelAttribute User user,
			@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			String imagen = storageService.store(file);
			user.setAvatar(MvcUriComponentsBuilder
					.fromMethodName(FilesController.class, "serveFile", imagen).build().toUriString());
		}
		userService.registry(user);
		return "redirect:/auth/login";
	}

}
