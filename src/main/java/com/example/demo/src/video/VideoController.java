package com.example.demo.src.video;

import java.util.List;

import com.example.demo.src.bookmark.domain.GetBookmarkRes;
import com.example.demo.src.video.domain.GetVideoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public void getTopTenVideos() {

    }

    @GetMapping("/watching")
    public void getWatchingVideos() {

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
    public void getPopularVideos() {

    }

    @GetMapping("/genre/{genreIdx}")
    public void getGenreVideos() {

    }
}
