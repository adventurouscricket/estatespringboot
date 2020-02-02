package com.mrhenry.controller;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import org.apache.log4j.Logger;

import com.mrhenry.service.IUserService;

@WebServlet(urlPatterns = "/admin-user")
public class UserController {
	
	final static Logger logger = Logger.getLogger(BuildingController.class);
	
	@Inject
	private IUserService userService;
	
}
