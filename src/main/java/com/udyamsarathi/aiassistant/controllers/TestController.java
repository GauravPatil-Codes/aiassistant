package com.udyamsarathi.aiassistant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	
	@RequestMapping("/")
	public String test() {
		return "Hey, I am working Fine";
		
	}
	

}
