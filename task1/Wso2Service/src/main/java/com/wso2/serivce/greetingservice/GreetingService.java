package com.wso2.serivce.greetingservice;

import org.springframework.http.ResponseEntity;

public interface GreetingService {

	ResponseEntity<String> getMessage(String message);

}
