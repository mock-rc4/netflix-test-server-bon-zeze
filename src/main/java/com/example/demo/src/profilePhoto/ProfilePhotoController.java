package com.example.demo.src.profilePhoto;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.profilePhoto.domain.ProfilePhoto;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfilePhotoController {

    @Autowired
    private final ProfilePhotoService profilePhotoService;
    @Autowired
    private final ProfilePhotoProvider profilePhotoProvider;
    @Autowired
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProfilePhotoController(ProfilePhotoService profilePhotoService, ProfilePhotoProvider profilePhotoProvider, JwtService jwtService) {
        this.profilePhotoService = profilePhotoService;
        this.profilePhotoProvider = profilePhotoProvider;
        this.jwtService = jwtService;
    }

    @GetMapping("/manage")
    public BaseResponse<ProfilePhoto> getProfilePhoto() {
        try {
            ProfilePhoto profilePhoto = profilePhotoProvider.getProfilePhoto();
            return new BaseResponse<>(profilePhoto);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/manage/photos")
    public BaseResponse<List<ProfilePhoto>> getProfilePhotos() {
        try {
            List<ProfilePhoto> profilePhoto = profilePhotoProvider.getProfilePhotos();
            return new BaseResponse<>(profilePhoto);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
