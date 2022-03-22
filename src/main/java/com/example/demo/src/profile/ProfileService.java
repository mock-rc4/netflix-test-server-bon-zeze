package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.src.account.domain.Account;
import com.example.demo.src.profile.domain.GetProfileReq;
import com.example.demo.src.profile.domain.GetProfileRes;
import com.example.demo.src.profile.domain.Profile;
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

	// 프로필 삭제
	public void deactivate(Profile.DeactivateReqDto deactivateReqDto) throws BaseException {
		int result = 0;
		try {
			result = profileDao.deactivate(deactivateReqDto); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)
		} catch (Exception exception) {
			throw new BaseException(DATABASE_ERROR);
		}
		if (result == 0) {
			throw new BaseException(MODIFY_FAIL_DEACTIVATE_PROFILE);
		}
	}
}
