package com.example.demo.src.video;

import static com.example.demo.config.BaseResponseStatus.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;

import com.example.demo.src.video.domain.GetVideoRes;
import com.example.demo.src.video.domain.Video;
import com.example.demo.src.video.domain.VideoContent;
import com.example.demo.src.video.domain.VideoDetail;
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

	public List<Video.getVideoResDto> getVideosByActor(int actorIdx) throws BaseException {
        try {
            return videoDao.getVideosByActor(actorIdx);
        } catch (Exception exception) {
            logger.error(exception.toString());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Video.getVideoResDto> getVideosByCharacter(int characterIdx) throws BaseException {
        try {
            return videoDao.getVideosByCharacter(characterIdx);
        } catch (Exception exception) {
            logger.error(exception.toString());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<VideoDetail.actorInfoResDto> getActorsByVideoIdx(int videoIdx) throws BaseException {
        try {
            return videoDao.getActorsByVideoIdx(videoIdx);
        } catch (Exception exception) {
            logger.error(exception.toString());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<VideoDetail.genreInfoResDto> getGenresByVideoIdx(int videoIdx) throws BaseException {
        try {
            return videoDao.getGenresByVideoIdx(videoIdx);
        } catch (Exception exception) {
            logger.error(exception.toString());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<VideoDetail.characterInfoResDto> getCharactersByVideoIdx(int videoIdx) throws BaseException {
        try {
            return videoDao.getCharactersByVideoIdx(videoIdx);
        } catch (Exception exception) {
            logger.error(exception.toString());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Video.getVideoResDto> getVideoDetailByVideoIdx(int videoIdx) throws BaseException {
        try {
            return videoDao.getVideoDetailByVideoIdx(videoIdx);
        } catch (Exception exception) {
            logger.error(exception.toString());
            throw new BaseException(DATABASE_ERROR);
        }
    }

	public List<GetVideoRes> getThisWeekOpenVideos() throws BaseException {
		try {
			List<GetVideoRes> result = videoDao.getThisWeekOpenVideos();
			if (result.isEmpty()) {
				throw new BaseException(GET_VIDEOS_EXISTS_ERROR);
			}
			return result;
		} catch (BaseException exception) {
			throw new BaseException(GET_VIDEOS_EXISTS_ERROR);
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<GetVideoRes> getNextWeekOpenVideos() throws BaseException {
		try {
			List<GetVideoRes> result = videoDao.getNextWeekOpenVideos();
			if (result.isEmpty()) {
				throw new BaseException(GET_VIDEOS_EXISTS_ERROR);
			}
			return result;
		} catch (BaseException exception) {
			throw new BaseException(GET_VIDEOS_EXISTS_ERROR);
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
	}

    public List<GetVideoRes> getVideosBySearch(String keyword) throws BaseException {
        try {
            //제목 기준 찾기
            List<GetVideoRes> resultByTitle = videoDao.getVideosByMovieTitle(keyword);
            if (!resultByTitle.isEmpty()) {
                return resultByTitle;
            }
            //사람 기준 찾기
            List<GetVideoRes> resultByActor = videoDao.getVideosByActorName(keyword);
            if (!resultByActor.isEmpty()) {
                return resultByActor;
            }
            //장르 기준 찾기
            List<GetVideoRes> resultByGenre = videoDao.getVideosByGenreName(keyword);
            if (!resultByGenre.isEmpty()) {
                return resultByGenre;
            } else {
                throw new BaseException(GET_VIDEO_SEARCH_ERROR);
            }
        } catch (BaseException exception) {
            throw new BaseException(GET_VIDEO_SEARCH_ERROR);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

	public List<Video.getVideoResDto> getAwardVideosByGenre(int videoType, String genre) throws BaseException {
		try {
			return videoDao.getAwardVideosByGenre(videoType, genre);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Video.getVideoResDto> getVideosByDirector(int directorIdx) throws BaseException {
		try {
			return videoDao.getVideosByDirector(directorIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<VideoDetail.directorInfoResDto> getDirectorsByVideoIdx(int videoIdx) throws BaseException {
		try {
			return videoDao.getDirectorsByVideoIdx(videoIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}
}