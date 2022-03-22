package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.profilePhoto.domain.GetProfilePhotoRes;
import com.example.demo.src.profile.domain.PostProfileReq;
import com.example.demo.src.profile.domain.PostProfileRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private final ProfileService profileService;
    @Autowired
    private final ProfileProvider profileProvider;
    @Autowired
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProfileController(ProfileService profileService, ProfileProvider profileProvider, JwtService jwtService) {
        this.profileService = profileService;
        this.profileProvider = profileProvider;
        this.jwtService = jwtService;
    }


    @PostMapping("/manage")
    public BaseResponse<PostProfileRes> createProfile(@RequestBody PostProfileReq postProfileReq) {
        try {
            PostProfileRes postProfileRes = profileService.create(postProfileReq);
            return new BaseResponse<>(postProfileRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }




}
