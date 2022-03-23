package com.example.demo.src.video;

import static com.example.demo.config.BaseResponseStatus.*;

import java.util.List;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.profile.domain.Profile;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videos")
public class VideoController {

	@Autowired
	private final VideoService VideoService;
	@Autowired
	private final VideoProvider VideoProvider;
	@Autowired
	private final JwtService jwtService;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	public VideoController(VideoService VideoService, VideoProvider VideoProvider, JwtService jwtService) {
		this.VideoService = VideoService;
		this.VideoProvider = VideoProvider;
		this.jwtService = jwtService;
	}
}
