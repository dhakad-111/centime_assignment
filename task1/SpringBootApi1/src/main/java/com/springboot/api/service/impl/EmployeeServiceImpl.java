package com.springboot.api.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.api.dto.EmployeeDTO;
import com.springboot.api.dto.MessageDTO;
import com.springboot.api.exception.UserInformationsMissingException;
import com.springboot.api.model.Employee;
import com.springboot.api.repository.EmployeeRepository;
import com.springboot.api.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final static Logger LOG = LogManager.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public ResponseEntity<?> save(EmployeeDTO dto) {
		if (dto != null && !dto.getName().isEmpty() && !dto.getSurname().isEmpty()) {
			Mapper mapper = new DozerBeanMapper();
			Employee employee = mapper.map(dto, Employee.class);
			Employee saveEmp = employeeRepository.save(employee);
			if (saveEmp != null) {
				EmployeeDTO res = mapper.map(saveEmp, EmployeeDTO.class);
				MessageDTO msgDTO = new MessageDTO();
				msgDTO.setFullName(res.getName() + " " + res.getSurname());
				return ResponseEntity.status(HttpStatus.CREATED).body(msgDTO);
			} else {
				LOG.error("unable to connect server");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unable to connect server");
			}
		} else {
			LOG.error("user informations are missing {} :" + dto);
			throw new UserInformationsMissingException("user informations are missing {} :" + dto);
		}

	}

}
