package com.example.demo.src.videoPlay;

import static com.example.demo.utils.ValidationRegex.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.videoPlay.domain.VideoPlay;

@RestController
@RequestMapping("/video-plays")
public class VideoPlayController {

	@Autowired
	private final VideoPlayService videoPlayService;
	@Autowired
	private final VideoPlayProvider videoPlayProvider;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	public VideoPlayController(VideoPlayService videoPlayService, VideoPlayProvider videoPlayProvider) {
		this.videoPlayService = videoPlayService;
		this.videoPlayProvider = videoPlayProvider;
	}

	@ResponseBody
	@PostMapping("/{profileIdx}/{videosIdx}")
	public BaseResponse<VideoPlay.createResDto> createVideoPlay(@PathVariable("profileIdx") int profileIdx,
		@PathVariable("videosIdx") int videosIdx) {
		try {
			VideoPlay.createResDto resDto = videoPlayService.createVideoPlay(
				new VideoPlay.createDto(profileIdx, videosIdx));
			return new BaseResponse<>(resDto);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	@ResponseBody
	@GetMapping("{profileIdx}/{videosIdx}")
	public BaseResponse<Float> getVideoPlayStatus(@PathVariable("profileIdx") int profileIdx,
		@PathVariable("videosIdx") int videosIdx) {
		try {
			float resDto = videoPlayProvider.getVideoPlayStatus(profileIdx, videosIdx);
			return new BaseResponse<>(resDto);
		} catch (BaseException exception) {
			return new BaseResponse<>((exception.getStatus()));
		}
	}

	@ResponseBody
	@PatchMapping("/{profileIdx}/{videosIdx}")
	public BaseResponse<String> modifyVideoPlay(@PathVariable("profileIdx") int profileIdx,
		@PathVariable("videosIdx") int videosIdx,
		@RequestBody VideoPlay.modifyReqDto requestDto) {
		try {
			float currentPlayTime = requestDto.getCurrentPlayTime();
			if (!isRegexVideoPlayTime(currentPlayTime)) {
				return new BaseResponse<>(BaseResponseStatus.POST_VIDEO_PLAY_INVALID_CURRENT_PLAY_TIME);
			}
			videoPlayService.modifyVideoPlay(
				new VideoPlay.modifyDto(profileIdx, videosIdx, requestDto.getCurrentPlayTime()));
			return new BaseResponse<>("재생 기록이 변경되었습니다.");
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	@ResponseBody
	@GetMapping("/play/{profileIdx}/{videoIdx}")
	public BaseResponse<VideoPlay.getVideoPlayStatusAtMainMenuResDto> getVideoPlayStatusAtMainMenu
		(@PathVariable("profileIdx") int profileIdx,
		@PathVariable("videoIdx") int videoIdx) {
		try {
			VideoPlay.getVideoPlayStatusAtMainMenuResDto resDto =
				videoPlayProvider.getVideoPlayStatusAtMainMenu(profileIdx, videoIdx);
			return new BaseResponse<>(resDto);
		} catch (BaseException exception) {
			return new BaseResponse<>((exception.getStatus()));
		}
	}
}