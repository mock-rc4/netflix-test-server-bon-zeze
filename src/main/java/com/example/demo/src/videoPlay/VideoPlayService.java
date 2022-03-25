package com.example.demo.src.videoPlay;

import static com.example.demo.config.BaseResponseStatus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.src.videoPlay.domain.VideoPlay;

@Service
public class VideoPlayService {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final VideoPlayDao videoPlayDao;
	private final VideoPlayProvider videoPlayProvider;

	@Autowired
	public VideoPlayService(VideoPlayDao videoPlayDao, VideoPlayProvider videoPlayProvider) {
		this.videoPlayDao = videoPlayDao;
		this.videoPlayProvider = videoPlayProvider;
	}

	public VideoPlay.createResDto createVideoPlay(VideoPlay.createDto requestDto) throws BaseException {
		//  중복된 재생 기록인지 확인
		if (videoPlayProvider.checkHasVideoPlay(
			new VideoPlay.checkHasDuplicatedVideoPlayReqDto(requestDto.getProfileIdx(),
				requestDto.getVideosIdx())) == 1) {
			throw new BaseException(POST_VIDEO_PLAY_ALREADY_EXISTS);
		}
		try {
			int VideoPlayIdx = videoPlayDao.createVideoPlay(requestDto);
			return new VideoPlay.createResDto(VideoPlayIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public void modifyVideoPlay(VideoPlay.modifyDto requestDto) throws BaseException {
		//  중복된 재생 기록인지 확인
		if (videoPlayProvider.checkHasVideoPlay(new VideoPlay.checkHasDuplicatedVideoPlayReqDto(
			requestDto.getProfileIdx(), requestDto.getVideosIdx())) == 0) {
			throw new BaseException(POST_VIDEO_PLAY_DOES_NOT_EXISTS);
		}
		try {
			videoPlayDao.modifyVideoPlay(requestDto);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}
}