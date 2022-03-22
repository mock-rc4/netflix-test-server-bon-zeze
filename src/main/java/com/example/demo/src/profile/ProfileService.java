package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.domain.PatchProfileReq;
import com.example.demo.src.profile.domain.PostProfileReq;
import com.example.demo.src.profile.domain.PostProfileRes;
import com.example.demo.src.profilePhoto.domain.PatchProfilePhotoReq;
import com.example.demo.src.profilePhoto.domain.ProfilePhoto;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ProfileService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProfileDao profileDao;
    private final ProfileProvider profileProvider;
    private final JwtService jwtService;

    @Autowired
    public ProfileService(ProfileDao profileDao, ProfileProvider profileProvider, JwtService jwtService) {
        this.profileDao = profileDao;
        this.profileProvider = profileProvider;
        this.jwtService = jwtService;
    }

    public PostProfileRes create(PostProfileReq postProfileReq) throws BaseException {
        try {
            int profileIdx = profileDao.create(postProfileReq);

            String jwt = jwtService.createJwt(profileIdx);
            return new PostProfileRes(profileIdx, jwt);
        } catch (Exception exception) {
            logger.error(exception.toString());
            throw new BaseException(POST_PROFILE_CREATE_ERROR);
        }
    }

    public ProfilePhoto manageProfilePhoto(PatchProfilePhotoReq patchProfilePhotoReq) throws BaseException {
        try {
            ProfilePhoto profilePhoto = profileDao.updateProfilePhoto(patchProfilePhotoReq);
            return profilePhoto;
        } catch (Exception exception) {
            throw new BaseException(PATCH_PROFILE_MANAGE_ERROR);
        }
    }

    public void manage(PatchProfileReq patchProfileReq) throws BaseException {
        try {
            int result = profileDao.updateProfile(patchProfileReq);
            if (result == 0) {
                throw new BaseException(PATCH_ACCOUNTS_MEMBERSHIP_UPDATE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(PATCH_PROFILE_MANAGE_ERROR);
        }
    }
}
