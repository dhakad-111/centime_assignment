package com.microservices.serivce;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.microservices.dto.ADTO;
import com.microservices.dto.InformationDTO;
import com.microservices.response.Response;

public interface InformationService {

	ResponseEntity<String> saveUserData(List<ADTO> dto);

	ResponseEntity<InformationDTO> findByUserId(Integer id);

	ResponseEntity<List<InformationDTO>> findByParentId(Integer id);

	ResponseEntity<List<Response>> findAll();

}
