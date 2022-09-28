package com.springboot.api.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.api.dto.EmployeeDTO;
import com.springboot.api.service.EmployeeService;

@RestController
@RequestMapping("/service2")
public class ApisService2 {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/saveMessage")
	public ResponseEntity<?> saveEmployeeData(@RequestBody EmployeeDTO dto) {
		return employeeService.save(dto);
	}
}
