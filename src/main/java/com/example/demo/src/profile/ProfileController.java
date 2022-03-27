package com.example.demo.src.profile;

import static com.example.demo.config.BaseResponseStatus.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.profile.domain.PatchProfileReq;
import com.example.demo.src.profile.domain.PostProfileReq;
import com.example.demo.src.profile.domain.PostProfileRes;
import com.example.demo.src.profile.domain.Profile;
import com.example.demo.src.profilePhoto.domain.PatchProfilePhotoReq;
import com.example.demo.src.profilePhoto.domain.ProfilePhoto;
import com.example.demo.utils.JwtService;

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
			logger.error(exception.toString());
			return new BaseResponse<>(exception.getStatus());
		}
	}

	@PatchMapping("/manage")
	public BaseResponse<String> manageProfile(@RequestBody PatchProfileReq patchProfileReq) {
		try {
			profileService.manage(patchProfileReq);
			return new BaseResponse<>("success update profile");
		} catch (BaseException exception) {
			logger.error(exception.toString());
			return new BaseResponse<>(exception.getStatus());
		}
	}

	@PatchMapping("/manage/photos")
	public BaseResponse<ProfilePhoto> manageProfilePhotos(@RequestBody PatchProfilePhotoReq patchProfilePhotoReq) {
		try {
			ProfilePhoto profilePhoto = profileService.manageProfilePhoto(patchProfilePhotoReq);
			return new BaseResponse<>(profilePhoto);
		} catch (BaseException exception) {
			logger.error(exception.toString());
			return new BaseResponse<>(exception.getStatus());
		}
	}

	@PatchMapping("{accountIdx}/{profileIdx}/deactivate")
	public BaseResponse<String> deactivateProfile(@PathVariable("accountIdx") int accountIdx,
		@PathVariable("profileIdx") int profileIdx
	) {
		try {

			int accountIdByJwt = jwtService.getUserIdx();
			if (accountIdx != accountIdByJwt) {
				return new BaseResponse<>(INVALID_USER_JWT);
			}
			Profile.DeactivateReqDto deactivateReqDto = new Profile.DeactivateReqDto(profileIdx);
			profileService.deactivate(deactivateReqDto);

			String result = "프로필이 비활성화 되었습니다.";
			return new BaseResponse<>(result);
		} catch (BaseException exception) {
			return new BaseResponse<>((exception.getStatus()));
		}
	}

	@ResponseBody
	@GetMapping("by-account-idx/{accountIdx}")
	public BaseResponse<List<Profile.getProfileInfoResDto>> getProfilesByAccountIdx(
		@PathVariable("accountIdx") int accountIdx) {
		try {
			List<Profile.getProfileInfoResDto> getProfileInfoResDto = profileProvider.getProfilesByAccountIdx(
				accountIdx);
			return new BaseResponse<>(getProfileInfoResDto);
		} catch (BaseException exception) {
			return new BaseResponse<>((exception.getStatus()));
		}
	}

	@ResponseBody
	@GetMapping("{profileIdx}")
	public BaseResponse<Profile.getProfileInfoResDto> getProfileInfo(@PathVariable("profileIdx") int profileIdx) {
		try {
			Profile.getProfileInfoResDto getProfileInfoResDto = profileProvider.getProfileInfo(profileIdx);
			return new BaseResponse<>(getProfileInfoResDto);
		} catch (BaseException exception) {
			return new BaseResponse<>((exception.getStatus()));
		}
	}

	@GetMapping("/name/{profileIdx}")
	public BaseResponse<String> getProfileName(@PathVariable("profileIdx") int profileIdx) {
		try {
			String name = profileProvider.getProfileName(profileIdx);
			return new BaseResponse<>(name);
		} catch (BaseException exception) {
			return new BaseResponse<>((exception.getStatus()));
		}
	}

	@GetMapping("/{profileIdx}/validate-age-grade/{videoIdx}")
	public BaseResponse<Boolean> checkIsNeedToValidateAgeGrade(@PathVariable("profileIdx") int profileIdx,
		@PathVariable("videoIdx") int videoIdx) {
		try {
			// 유효하면 true, 그렇지 않으면 false을 반환.
			boolean result = profileProvider.checkIsValidAgeGradeForAdultContents(profileIdx, videoIdx);
			return new BaseResponse<>(result);
		} catch (BaseException exception) {
			return new BaseResponse<>((exception.getStatus()));
		}
	}
}
