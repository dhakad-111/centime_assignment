package com.microservices.dto;

import java.util.List;

import lombok.Data;

@Data
public class BDTO {

	private String name;
	private List<CDTO> subClasses;

}
