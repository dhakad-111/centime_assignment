package com.springboot.api.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.api.service.GreetingService;

@RestController
@RequestMapping("/service1")
public class Apis {

	@Autowired
	private GreetingService greetingService;
	
	/*
	 * @GetMapping("/message") public ResponseEntity<?>
	 * getMessage(HttpServletRequest request){
	 * 
	 * return greetingService.getMessage(request); }
	 */
	@GetMapping("/message")
	public ResponseEntity<String> getMessage(@RequestParam String message){
		
		return greetingService.getMessage(message);
	}
	
}
