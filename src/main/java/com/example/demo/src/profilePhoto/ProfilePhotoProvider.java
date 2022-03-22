package com.example.demo.src.profilePhoto;

import com.example.demo.config.BaseException;
import com.example.demo.src.profilePhoto.domain.ProfilePhoto;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ProfilePhotoProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProfilePhotoDao profilePhotoDao;
    private final ProfilePhotoService profilePhotoService;
    private final JwtService jwtService;

    @Autowired
    public ProfilePhotoProvider(ProfilePhotoDao profilePhotoDao, ProfilePhotoService profilePhotoService, JwtService jwtService) {
        this.profilePhotoDao = profilePhotoDao;
        this.profilePhotoService = profilePhotoService;
        this.jwtService = jwtService;
    }

    public ProfilePhoto getProfilePhoto() throws BaseException {
        try {
            int maxIdx = profilePhotoDao.getProfilePhotoCount();
            int photoIdx = (int) ((Math.random() * maxIdx) + 1);
            ProfilePhoto profilePhoto = profilePhotoDao.getProfilePhoto(photoIdx);
            return profilePhoto;
        } catch (Exception exception) {
            throw new BaseException(GET_PROFILE_PHOTO_ERROR);
        }
    }

    public List<ProfilePhoto> getProfilePhotos() throws BaseException {
        try {
            List<ProfilePhoto> profilePhotos = profilePhotoDao.getProfilePhotos();
            return profilePhotos;
        } catch (Exception exception) {
            throw new BaseException(GET_PROFILE_PHOTO_ERROR);
        }
    }
}
