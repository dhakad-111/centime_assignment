package com.microservices.serivce.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.microservices.dto.ADTO;
import com.microservices.dto.BDTO;
import com.microservices.dto.CDTO;
import com.microservices.dto.DataComparator;
import com.microservices.dto.InfoDTO;
import com.microservices.dto.InformationDTO;
import com.microservices.exception.InformationNotFoundException;
import com.microservices.model.Informations;
import com.microservices.repo.InformationRepository;
import com.microservices.response.Response;
import com.microservices.serivce.InformationService;

@Service
public class InformationsServiceImpl implements InformationService {

	@Autowired
	private InformationRepository informationRepository;

	private static final Logger LOG = LogManager.getLogger(InformationsServiceImpl.class);

	private List<String> colors = Arrays.asList("red", "green", "white", "yellow", "blue", "lighblue", "lighgreen",
			"grey", "lightgrey", "red", "green", "white", "yellow", "blue", "lighblue");

	@Override
	public ResponseEntity<String> saveUserData(List<ADTO> dto) {
		LOG.info("Save User Data {} :");

		List<InformationDTO> ans = formatData(dto);
		Collections.sort(ans, new DataComparator());
		ans = setColors(ans);
		List<Informations> infoList = convertDTOToEntity(ans);
		Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
		LOG.info("json data " + gson.toJson(infoList));
		List<Informations> saveInfo = informationRepository.saveAll(infoList);
		if (saveInfo != null) {
			LOG.info("data successfully created");
			return ResponseEntity.status(HttpStatus.CREATED).body("data successfully created!..");
		} else {
			LOG.error("server not available");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server not available!..");
		}
	}

	private List<Informations> convertDTOToEntity(List<InformationDTO> ans) {
		Mapper mapper = new DozerBeanMapper();
		List<Informations> list = new ArrayList<>();
		for (InformationDTO x : ans) {
			Informations info = mapper.map(x, Informations.class);
			list.add(info);
		}
		return list;
	}

	private List<InformationDTO> setColors(List<InformationDTO> ans) {
		int ind = 0;
		for (InformationDTO x : ans) {
			if (colors.size() > ind) {
				x.setColor(colors.get(ind++));
			}
		}
		return ans;
	}

	private List<InformationDTO> formatData(List<ADTO> dto) {
		Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
		LOG.info(gson.toJson(dto));
		List<InformationDTO> ans = new ArrayList<>();
		int id = 0;
		if (dto != null && !dto.isEmpty()) {
			for (int i = 0; i < dto.size(); i++) {
				id++;
				List<BDTO> list = dto.get(i).getSubClasses();
				if (list != null && !list.isEmpty()) {
					for (int j = 0; j < list.size(); j++) {
						List<CDTO> list2 = list.get(j).getSubClasses();
						if (list2 != null && !list2.isEmpty()) {
							for (int k = 0; k < list2.size(); k++) {
								InformationDTO temp = new InformationDTO();
								temp.setParentId(id);
								temp.setName(list2.get(k).getName());
								ans.add(temp);
							}
						}
						InformationDTO temp = new InformationDTO();
						temp.setParentId(id);
						temp.setName(list.get(j).getName());
						ans.add(temp);
					}
				}
				InformationDTO temp = new InformationDTO();
				temp.setParentId(0);
				temp.setName(dto.get(i).getName());
				ans.add(temp);
			}
		}
		LOG.info(gson.toJson(ans));

		return ans;
	}

	@Override
	public ResponseEntity<InformationDTO> findByUserId(Integer id) {
		LOG.info("user id " + id);
		Informations infoData = informationRepository.findById(id).get();
		if (infoData != null) {
			Mapper mapper = new DozerBeanMapper();
			InformationDTO dto = mapper.map(infoData, InformationDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} else {
			LOG.error("information not found " + id);
			throw new InformationNotFoundException("user information not found " + id);
		}
	}

	@Override
	public ResponseEntity<List<InformationDTO>> findByParentId(Integer id) {
		List<Informations> data = informationRepository.findByParentId(id);
		List<InformationDTO> response = new ArrayList<>();
		if (data != null) {
			DozerBeanMapper mapper = new DozerBeanMapper();
			for (Informations x : data) {
				InformationDTO dto = mapper.map(x, InformationDTO.class);
				response.add(dto);
			}
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			LOG.error("information not found " + id);
			throw new InformationNotFoundException("user information not found " + id);
		}

	}

	@Override
	public ResponseEntity<List<Response>> findAll() {
		List<Informations> listData = informationRepository.findAll();
		if (listData != null) {
			return mappedDataParentChile(listData);
		} else {
			LOG.error("information not found ");
			throw new InformationNotFoundException("user information not found ");
		}
	}

	private ResponseEntity<List<Response>> mappedDataParentChile(List<Informations> listData) {

		List<InformationDTO> ans = new ArrayList<>();
		Mapper mapper = new DozerBeanMapper();
		for (Informations x : listData) {
			InformationDTO dto = mapper.map(x, InformationDTO.class);
			ans.add(dto);
		}
		Map<Integer, List<InformationDTO>> table = new HashMap<>();
		for (InformationDTO x : ans) {
			if (x.getParentId() == 0) {
				table.putIfAbsent(x.getId(), new ArrayList<>());
				List<InformationDTO> temp = table.get(x.getId());
				temp.add(x);
				table.put(x.getId(), temp);
			}
		}
		for (InformationDTO x : ans) {
			if (table.containsKey(x.getParentId())) {
				List<InformationDTO> temp = table.get(x.getParentId());
				temp.add(x);
				table.put(x.getParentId(), temp);
			}
		}
		List<Response> response = new ArrayList<>();
		for (Map.Entry<Integer, List<InformationDTO>> x : table.entrySet()) {
			List<InformationDTO> list = table.get(x.getKey());
			Response res = new Response();
			res.setName(list.get(0).getName());
			List<InfoDTO> temp = new ArrayList<>();
			for (int i = 1; i < list.size(); i++) {
				temp.add(mapper.map(list.get(i), InfoDTO.class));
			}
			res.setSubClasses(temp);
			response.add(res);
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

}
