package com.example.demo.src.video;


import com.example.demo.config.BaseException;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class VideoService {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final VideoDao VideoDao;
	private final VideoProvider VideoProvider;
	private final JwtService jwtService;

	@Autowired
	public VideoService(VideoDao VideoDao, VideoProvider VideoProvider, JwtService jwtService) {
		this.VideoDao = VideoDao;
		this.VideoProvider = VideoProvider;
		this.jwtService = jwtService;
	}
}