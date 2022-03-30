package com.example.demo.src.profile;

import static com.example.demo.config.BaseResponseStatus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.domain.PatchProfileReq;
import com.example.demo.src.profile.domain.PostProfileReq;
import com.example.demo.src.profile.domain.PostProfileRes;
import com.example.demo.src.profile.domain.Profile;
import com.example.demo.src.profilePhoto.domain.PatchProfilePhotoReq;
import com.example.demo.src.profilePhoto.domain.ProfilePhoto;
import com.example.demo.utils.JwtService;

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

	public int create(PostProfileReq postProfileReq) throws BaseException {
		try {
			return profileDao.create(postProfileReq);
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
