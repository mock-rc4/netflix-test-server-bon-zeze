package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.domain.PostProfileReq;
import com.example.demo.src.profile.domain.PostProfileRes;
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
}
