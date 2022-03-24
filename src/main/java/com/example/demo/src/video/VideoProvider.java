package com.example.demo.src.video;

import static com.example.demo.config.BaseResponseStatus.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.src.video.domain.Video;
import com.example.demo.utils.JwtService;

@Service
public class VideoProvider {

	private final VideoDao videoDao;
	private final JwtService jwtService;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public VideoProvider(VideoDao videoDao, JwtService jwtService) {
		this.videoDao = videoDao;
		this.jwtService = jwtService;
	}

	public List<Video.getVideoResDto> getVideosByGenre(int videoType, String genre) throws BaseException {
		try {
			return videoDao.getVideosByGenre(videoType, genre);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}
}