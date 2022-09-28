package com.springboot.api.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.api.service.GreetingService;

@Service
public class GreetingServiceImpl implements GreetingService {

	private static final Logger LOG = LogManager.getLogger(GreetingServiceImpl.class);

	@Override
	public ResponseEntity<String> getMessage(String message) {
		
		//String message = request.getHeader("message");
		LOG.info("service2 data {} : "+message);
		if (message != null) {
			return ResponseEntity.status(HttpStatus.OK).body(message);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
	}

}
