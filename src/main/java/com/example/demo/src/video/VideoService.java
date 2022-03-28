package com.example.demo.src.video;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final VideoDao videoDao;
    private final VideoProvider videoProvider;
    private final JwtService jwtService;

    @Autowired
    public VideoService(VideoDao videoDao, VideoProvider videoProvider, JwtService jwtService) {
        this.videoDao = videoDao;
        this.videoProvider = videoProvider;
        this.jwtService = jwtService;
    }


}