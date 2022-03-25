package com.example.demo.src.facebookAccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.utils.JwtService;

@Service
public class FacebookAccountProvider {

	private final JwtService jwtService;
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final FacebookAccountDao facebookAccountDao;

	@Autowired
	public FacebookAccountProvider(FacebookAccountDao facebookAccountDao, JwtService jwtService) {
		this.facebookAccountDao = facebookAccountDao;
		this.jwtService = jwtService;
	}
}