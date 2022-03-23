package com.example.demo.src.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.utils.JwtService;

@Service
public class VideoProvider {

	private final VideoDao VideoDao;
	private final JwtService jwtService;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public VideoProvider(VideoDao VideoDao, JwtService jwtService) {
		this.VideoDao = VideoDao;
		this.jwtService = jwtService;
	}
}