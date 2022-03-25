package com.example.demo.src.facebookAccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.utils.JwtService;

@Service
public class FacebookAccountService {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final JwtService jwtService;
	private final FacebookAccountDao facebookAccountDao;
	private final FacebookAccountProvider facebookAccountProvider;

	@Autowired
	public FacebookAccountService(JwtService jwtService, FacebookAccountDao facebookAccountDao,
		FacebookAccountProvider facebookAccountProvider) {
		this.jwtService = jwtService;
		this.facebookAccountProvider = facebookAccountProvider;
		this.facebookAccountDao = facebookAccountDao;
	}
}