package com.example.demo.src.video;

import java.util.List;

import com.example.demo.src.search.SearchService;
import com.example.demo.src.video.domain.GetVideoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.video.domain.*;
import com.example.demo.utils.JwtService;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private final VideoService videoService;
    @Autowired
    private final VideoProvider videoProvider;
    @Autowired
    private final SearchService searchService;
    @Autowired
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public VideoController(VideoService videoService, VideoProvider videoProvider,
                           SearchService searchService, JwtService jwtService) {
        this.videoService = videoService;
        this.videoProvider = videoProvider;
        this.searchService = searchService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("/{videoIdx}/contents")
    public BaseResponse<List<VideoContent.resDto>> getVideoContentsByVideoIdx(@PathVariable int videoIdx) {
        try {
            List<VideoContent.resDto> resDto = videoProvider.getVideoContentsByVideoIdx(videoIdx);
            return new BaseResponse<>(resDto);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{videoIdx}/{seasonNumber}/contents")
    public BaseResponse<List<VideoContent.resDto>> getVideoContentsByVideoIdxAndSeasonNumber(@PathVariable int videoIdx,
                                                                                             @PathVariable int seasonNumber) {
        try {
            List<VideoContent.resDto> resDto = videoProvider.getVideoContentsByVideoIdxAndSeasonNumber(videoIdx,
                    seasonNumber);
            return new BaseResponse<>(resDto);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{videoIdx}/season-episode-counts")
    public BaseResponse<List<Video.getEachSeasonEpisodeCountsResDto>> getEachSeasonEpisodeCounts(
            @PathVariable int videoIdx) {
        try {
            if (videoProvider.checkHasVideoIdx(videoIdx) == 0) {
                return new BaseResponse<>(BaseResponseStatus.GET_VIDEO_INVALID_VIDEO_IDX);
            }
            List<Video.getEachSeasonEpisodeCountsResDto> resDto = videoProvider.getEachSeasonEpisodeCounts(videoIdx);
            return new BaseResponse<>(resDto);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @GetMapping("/genre")
    public BaseResponse<List<Video.getVideoResDto>> getByGenre(@RequestParam int videoType,
                                                               @RequestParam String genre) {
        try {
            List<Video.getVideoResDto> resDto = videoProvider.getVideosByGenre(videoType, genre);
            return new BaseResponse<>(resDto);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/top")
    public BaseResponse<List<GetVideoRes>> getTopTenVideos() {
        try {
            List<GetVideoRes> getVideoResList = videoProvider.getTopTenVideos();
            return new BaseResponse<>(getVideoResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/watching/{profileIdx}")
    public BaseResponse<List<GetVideoRes>> getWatchingVideos(@PathVariable("profileIdx") int profileIdx) {
        try {
            List<GetVideoRes> getVideoResList = videoProvider.getWatchingVideos(profileIdx);
            return new BaseResponse<>(getVideoResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/new")
    public BaseResponse<List<GetVideoRes>> getNewVideos() {
        try {
            List<GetVideoRes> getVideoResList = videoProvider.getNewVideos();
            return new BaseResponse<>(getVideoResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/popular")
    public BaseResponse<List<GetVideoRes>> getPopularVideos() {
        try {
            List<GetVideoRes> getVideoResList = videoProvider.getPopularVideos();
            return new BaseResponse<>(getVideoResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

	@GetMapping("/the-most-searched")
	public BaseResponse<List<GetVideoRes>> getVideosByTheMostSearchedWord() {
		try {
			String theMostSearchedWord = videoProvider.getTheMostSearchedWord();
			List<GetVideoRes> getVideoResList = videoProvider.getVideosBySearch(theMostSearchedWord);
			return new BaseResponse<>(getVideoResList);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

    @GetMapping("/genre/{genreIdx}")
    public BaseResponse<List<GetVideoRes>> getGenreVideos(@PathVariable("genreIdx") int genreIdx) {
        try {
            List<GetVideoRes> getVideoResList = videoProvider.getGenreVideos(genreIdx);
            return new BaseResponse<>(getVideoResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/actors/{actorIdx}")
    public BaseResponse<List<Video.getVideoResDto>> getVideosByActor(@PathVariable("actorIdx") int actorIdx) {
        try {
            List<Video.getVideoResDto> result = videoProvider.getVideosByActor(actorIdx);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/characters/{characterIdx}")
    public BaseResponse<List<Video.getVideoResDto>> getVideosByCharacter(
            @PathVariable("characterIdx") int characterIdx) {
        try {
            List<Video.getVideoResDto> result = videoProvider.getVideosByCharacter(characterIdx);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/details/{videoIdx}/actors")
    public BaseResponse<List<VideoDetail.actorInfoResDto>> getActorsByVideoIdx(@PathVariable("videoIdx") int videoIdx) {
        try {
            List<VideoDetail.actorInfoResDto> result = videoProvider.getActorsByVideoIdx(videoIdx);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/details/{videoIdx}/genres")
    public BaseResponse<List<VideoDetail.genreInfoResDto>> getGenresByVideoIdx(@PathVariable("videoIdx") int videoIdx) {
        try {
            List<VideoDetail.genreInfoResDto> result = videoProvider.getGenresByVideoIdx(videoIdx);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/details/{videoIdx}/characters")
    public BaseResponse<List<VideoDetail.characterInfoResDto>> getCharactersByVideoIdx(
            @PathVariable("videoIdx") int videoIdx) {
        try {
            List<VideoDetail.characterInfoResDto> result = videoProvider.getCharactersByVideoIdx(videoIdx);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/{videoIdx}")
    public BaseResponse<List<Video.getVideoResDto>> getVideoDetailByVideoIdx(@PathVariable("videoIdx") int videoIdx) {
        try {
            List<Video.getVideoResDto> resDto = videoProvider.getVideoDetailByVideoIdx(videoIdx);
            return new BaseResponse<>(resDto);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/latest")
    public BaseResponse<List<GetVideoRes>> getThisWeekOpenVideos() {
        try {
            List<GetVideoRes> getVideoResList = videoProvider.getThisWeekOpenVideos();
            return new BaseResponse<>(getVideoResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/latest/next-week")
    public BaseResponse<List<GetVideoRes>> getNextWeekOpenVideos() {
        try {
            List<GetVideoRes> getVideoResList = videoProvider.getNextWeekOpenVideos();
            return new BaseResponse<>(getVideoResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //검색
    @GetMapping("/search")
    public BaseResponse<List<GetVideoRes>> getVideosBySearch(@RequestParam("q") String keyword) {
        try {
            searchService.saveSearch(keyword);
            List<GetVideoRes> getVideoResList = videoProvider.getVideosBySearch(keyword);
            return new BaseResponse<>(getVideoResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
