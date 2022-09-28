package com.springboot.api.service;

import org.springframework.http.ResponseEntity;

import com.springboot.api.dto.EmployeeDTO;

public interface EmployeeService {

	ResponseEntity<?> save(EmployeeDTO dto);

}
