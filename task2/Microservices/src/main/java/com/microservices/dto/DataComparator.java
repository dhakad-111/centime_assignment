package com.microservices.dto;

import java.util.Comparator;

public class DataComparator implements Comparator<InformationDTO> {

	@Override
	public int compare(InformationDTO a, InformationDTO b) {
		if (a.getParentId() > b.getParentId()) {
			return 1;
		} else if (a.getParentId() < b.getParentId()) {
			return -1;
		} else {
			return 0;
		}
	}

}
