package com.example.demo.src.videoPlay;

import static com.example.demo.config.BaseResponseStatus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.src.videoPlay.domain.VideoPlay;

@Service
public class VideoPlayProvider {

	private final VideoPlayDao videoPlayDao;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public VideoPlayProvider(VideoPlayDao videoPlayDao) {
		this.videoPlayDao = videoPlayDao;
	}

	public int checkHasVideoPlay(VideoPlay.checkHasDuplicatedVideoPlayReqDto requestDto) throws BaseException {
		try {
			return videoPlayDao.checkHasVideoPlay(requestDto);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public float getVideoPlayStatus(int profileIdx, int videosIdx) throws BaseException {
		try {
			return videoPlayDao.getVideoPlayStatus(profileIdx, videosIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}
}