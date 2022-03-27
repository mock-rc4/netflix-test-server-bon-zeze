package com.example.demo.src.video;

import java.util.List;

import com.example.demo.src.video.domain.GetVideoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.video.domain.Video;
import com.example.demo.src.video.domain.VideoContent;
import com.example.demo.utils.JwtService;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private final VideoService videoService;
    @Autowired
    private final VideoProvider videoProvider;
    @Autowired
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public VideoController(VideoService videoService, VideoProvider videoProvider, JwtService jwtService) {
        this.videoService = videoService;
        this.videoProvider = videoProvider;
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
	public BaseResponse<List<Video.getEachSeasonEpisodeCountsResDto>> getEachSeasonEpisodeCounts(@PathVariable int videoIdx) {
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
	public BaseResponse<List<Video.getVideoResDto>> getVideosByCharacter(@PathVariable("characterIdx") int characterIdx) {
		try {
			List<Video.getVideoResDto> result = videoProvider.getVideosByCharacter(characterIdx);
			return new BaseResponse<>(result);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}
}
