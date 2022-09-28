package com.wso2.serivce.greetingservice.impl;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.wso2.serivce.dto.MessageDTO;
import com.wso2.serivce.dto.ResponseDTO;
import com.wso2.serivce.exception.UserInformationsNotFound;
import com.wso2.serivce.greetingservice.GreetingService;

@Service
public class GreetingServiceImpl implements GreetingService {

	private static final Logger LOG = LogManager.getLogger(GreetingServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<String> getMessage(String message) {
		LOG.info("get message {} :");
		String msg = "";
		if (message != null && !message.isEmpty()) {
			String[] messageDetails = message.split(" ");
			if (messageDetails.length >= 3) {
				try {
					String temp = restGetMethod(message);
					if (temp != null) {
						msg += temp;
					}
				} catch (Exception e) {
					LOG.error("error message {} :" + e.getMessage());
				}

				try {
					String temp = restPostCall(message);
					if (temp != null) {
						msg = msg + " " + temp;
					}
				} catch (Exception e) {
					LOG.error("error message {} :" + e.getMessage());
				}
			} else {
				LOG.error("user information are missing {} : " + message);
				throw new UserInformationsNotFound("user information are missing {} :" + message);
			}
		} else {
			LOG.error("user information are missing {} : " + message);
			throw new UserInformationsNotFound("user information are missing {} :" + message);
		}

		return ResponseEntity.status(HttpStatus.OK).body(msg);

	}

	private String restPostCall(String message) {
		String url = "http://localhost:2020/service2/saveMessage";
		LOG.info("rest template post api call {} :");
		String[] temp = message.split(" ");
		MessageDTO dto = new MessageDTO();
		dto.setName(temp[1]);
		dto.setSurname(temp[2]);
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<MessageDTO> entity = new HttpEntity<MessageDTO>(dto, headers);
			String response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
			LOG.info("third microservice response {} :" + response);
			ResponseDTO resDTO = new Gson().fromJson(response, ResponseDTO.class);
			LOG.info("resDTO {} :" + resDTO);
			return (resDTO.getFullName());
		} catch (Exception e) {
			LOG.error("error message {} : " + e.getMessage());
		}

		return null;
	}

	private String restGetMethod(String message) {
		LOG.info("rest template get api call {}" + message);
		String[] temp = message.split(" ");
		String url = "http://localhost:2019/service1/message?message=" + temp[0];
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			String response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
			LOG.info("second microservice response {} : " + response);
			return response;
		} catch (Exception e) {
			LOG.error("error message {} : " + e.getMessage());
		}
		return null;
	}

}
