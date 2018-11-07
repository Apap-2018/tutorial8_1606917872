package com.apap.tutorial8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial8.model.PasswordModel;
import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user) {
		userService.addUser(user);
		return "home";
	}
	
	@RequestMapping(value="/updatePassword", method=RequestMethod.GET)
	private String updatePassword(@ModelAttribute UserRoleModel user) {
		return "updatePassword";
	}
	
	@RequestMapping(value="/updatePassword", method=RequestMethod.POST)
	private String updatePasswordSubmit(@ModelAttribute PasswordModel passwordForm, Model model) {
		UserRoleModel user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if(passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
			
			if(userService.isMatch(passwordForm.getOldPassword(), user.getPassword())) {
				user.setPassword(passwordForm.getNewPassword());
				userService.addUser(user);
				return "success";
			}
			
			else {
				return "updatePassword";
			}		
			
		}
		else {
			return "updatePassword";
		}
	}
}
