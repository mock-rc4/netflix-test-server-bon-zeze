package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.domain.GetProfileReq;
import com.example.demo.src.profile.domain.GetProfileRes;
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

    public GetProfileRes create(GetProfileReq getProfileReq) throws BaseException {
        try {
            int profileIdx = profileDao.create(getProfileReq);

            String jwt = jwtService.createJwt(profileIdx);
            return new GetProfileRes(profileIdx, jwt);
        } catch (Exception exception) {
            logger.error(exception.toString());
            throw new BaseException(POST_PROFILE_CREATE_ERROR);
        }
    }
}
