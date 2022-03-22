package com.example.demo.src.profile;

import static com.example.demo.config.BaseResponseStatus.*;

import java.util.List;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.profile.domain.GetProfileReq;
import com.example.demo.src.profile.domain.GetProfileRes;
import com.example.demo.src.profile.domain.Profile;
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


    @GetMapping("/create")
    public BaseResponse<GetProfileRes> createProfile(@RequestBody GetProfileReq getProfileReq) {
        try {
            GetProfileRes getProfileRes = profileService.create(getProfileReq);
            return new BaseResponse<>(getProfileRes);
        } catch (BaseException exception) {
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
	public BaseResponse<List<Profile.getProfileInfoResDto>> getProfilesByAccountIdx(@PathVariable("accountIdx") int accountIdx) {
		try {
			List<Profile.getProfileInfoResDto> getProfileInfoResDto = profileProvider.getProfilesByAccountIdx(accountIdx);
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


}
