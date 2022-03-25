package com.example.demo.src.video;

import java.util.List;

import com.example.demo.src.bookmark.domain.GetBookmarkRes;
import com.example.demo.src.video.domain.GetVideoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.video.domain.Video;
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
    public void getGenreVideos() {

    }
}
