package com.microservices.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.dto.ADTO;
import com.microservices.dto.InformationDTO;
import com.microservices.response.Response;
import com.microservices.serivce.InformationService;

@RestController
@RequestMapping(value = "/microservices")
public class Apis {

	@Autowired
	private InformationService informationService;

	@PostMapping(value = "/saveInfo")
	public ResponseEntity<String> saveUserData(@RequestBody List<ADTO> dto) {
		return informationService.saveUserData(dto);
	}
	
	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<InformationDTO> findByUserId(@PathVariable Integer id){
		return informationService.findByUserId(id);
	}
	
	@GetMapping(value = "/findByParentId/{id}")
	public ResponseEntity<List<InformationDTO>> findByParentId(@PathVariable Integer id){
		return informationService.findByParentId(id);
	}
	
	@GetMapping(value = "/findAll")
	public ResponseEntity<List<Response>> findAll(){
		return informationService.findAll();
	}

}
