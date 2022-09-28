package com.microservices.dto;

import lombok.Data;

@Data
public class InformationDTO {

	private Integer id;
	private Integer parentId;
	private String name;
	private String color;

}
