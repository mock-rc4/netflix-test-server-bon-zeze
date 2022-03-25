package com.example.demo.src.video;

import static com.example.demo.config.BaseResponseStatus.*;

import java.util.List;

import com.example.demo.src.video.domain.GetVideoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.video.domain.Video;
import com.example.demo.src.video.domain.VideoContent;
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

	public List<VideoContent.resDto> getVideoContentsByVideoIdx(int videoIdx) throws BaseException {
		try {
			return videoDao.getVideoContentsByVideoIdx(videoIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}
	public List<VideoContent.resDto> getVideoContentsByVideoIdxAndSeasonNumber(int videoIdx, int seasonNumber) throws BaseException {
		try {
			return videoDao.getVideoContentsByVideoIdxAndSeasonNumber(videoIdx, seasonNumber);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Video.getEachSeasonEpisodeCountsResDto> getEachSeasonEpisodeCounts(int videoIdx) throws BaseException {
		try {
			return videoDao.getEachSeasonEpisodeCounts(videoIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public int checkHasVideoIdx(int videoIdx) throws BaseException {
		try {
			return videoDao.checkHasVideoIdx(videoIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

    public List<GetVideoRes> getNewVideos() throws BaseException {
        try {
            return videoDao.getNewVideos();
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetVideoRes> getTopTenVideos() throws BaseException {
        try {
            return videoDao.getTopTenVideos();
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetVideoRes> getPopularVideos() throws BaseException {
        try {
            return videoDao.getPopularVideos();
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetVideoRes> getWatchingVideos(int profileIdx) throws BaseException {
        try {
            return videoDao.getWatchingVideos(profileIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetVideoRes> getGenreVideos(int genreIdx) throws BaseException {
        try {
            return videoDao.getGenreVideos(genreIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}