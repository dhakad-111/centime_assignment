package com.microservices.dto;

import java.util.List;

import lombok.Data;

@Data
public class ADTO {

	private String name;
	private List<BDTO> subClasses;

}
