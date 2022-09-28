package com.microservices.response;

import java.util.List;

import com.microservices.dto.InfoDTO;

import lombok.Data;

@Data
public class Response {
	
	private String name;
	private List<InfoDTO> subClasses;

}
