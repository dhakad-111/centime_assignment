package com.wso2.serivce.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wso2.serivce.greetingservice.GreetingService;

@RestController
@RequestMapping("/wso2/service")
public class Apis {

	@Autowired
	private GreetingService greetingService;

	@GetMapping("/greeting")
	public ResponseEntity<String> getMessage(@RequestParam String message) {
		return greetingService.getMessage(message);
	}

}
