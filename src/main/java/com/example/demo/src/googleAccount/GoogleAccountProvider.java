package com.example.demo.src.googleAccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.utils.JwtService;

@Service
public class GoogleAccountProvider {

	private final JwtService jwtService;
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final GoogleAccountDao googleAccountDao;

	@Autowired
	public GoogleAccountProvider(GoogleAccountDao googleAccountDao, JwtService jwtService) {
		this.googleAccountDao = googleAccountDao;
		this.jwtService = jwtService;
	}
}