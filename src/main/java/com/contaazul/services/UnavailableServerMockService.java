package com.contaazul.services;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class UnavailableServerMockService {

	private Map<String, Boolean> cities = new HashMap<>();

	public void up(String cityName) {
		cities.put( cityName, false );
	}

	public void down(String cityName) {
		cities.put( cityName, true );
	}

	public boolean isDown(String cityName) {
		return ofNullable( cities.get( cityName ) ).orElse( false );
	}

}
