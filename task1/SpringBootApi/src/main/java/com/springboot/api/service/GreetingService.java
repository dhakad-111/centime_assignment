package com.springboot.api.service;

import org.springframework.http.ResponseEntity;

public interface GreetingService {

	ResponseEntity<String> getMessage(String message);

}
