package com.example.demo.src.profile;

import static com.example.demo.config.BaseResponseStatus.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.domain.Profile;
import com.example.demo.utils.JwtService;

@Service
public class ProfileProvider {

	private final ProfileDao profileDao;
	private final JwtService jwtService;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public ProfileProvider(ProfileDao profileDao, JwtService jwtService) {
		this.profileDao = profileDao;
		this.jwtService = jwtService;
	}

	public List<Profile.getProfileInfoResDto> getProfiles(int accountIdx) throws BaseException {
		try {
			return profileDao.getProfiles(accountIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public List<Profile.getProfileInfoResDto> getProfilesByAccountIdx(int accountIdx) throws BaseException {
		try {
			return profileDao.getProfilesByAccountIdx(accountIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public Profile.getProfileInfoResDto getProfileInfo(int profileIdx) throws BaseException {
		try {
			return profileDao.getProfileInfo(profileIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

}
