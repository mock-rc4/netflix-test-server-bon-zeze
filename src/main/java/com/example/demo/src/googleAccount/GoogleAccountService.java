package com.example.demo.src.googleAccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.utils.JwtService;

@Service
public class GoogleAccountService {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private final JwtService jwtService;
	private final GoogleAccountDao googleAccountDao;
	private final GoogleAccountProvider googleAccountProvider;

	@Autowired
	public GoogleAccountService(JwtService jwtService, GoogleAccountDao googleAccountDao,
		GoogleAccountProvider googleAccountProvider) {
		this.jwtService = jwtService;
		this.googleAccountProvider = googleAccountProvider;
		this.googleAccountDao = googleAccountDao;
	}
}