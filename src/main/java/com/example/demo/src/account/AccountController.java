package com.example.demo.src.account;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.Constant.*;
import static com.example.demo.utils.ValidationRegex.*;

import java.util.List;
import java.util.Objects;

import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.account.domain.PatchAccountReq;
import com.example.demo.src.account.domain.PostAccountRes;
import com.example.demo.src.account.domain.PostLoginReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.account.domain.Account;
import com.example.demo.utils.JwtService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private final AccountService accountService;
    @Autowired
    private final AccountProvider accountProvider;
    @Autowired
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AccountController(AccountService accountService, AccountProvider accountProvider,
                             JwtService jwtService) {
        this.accountService = accountService;
        this.accountProvider = accountProvider;
        this.jwtService = jwtService;
    }


	@ResponseBody
	@GetMapping("sign-in-level")
	public BaseResponse<String> getAccountSignInStatusByEmail(@RequestParam String email) {
		try {
			if (email == null) {
				return new BaseResponse<>(BaseResponseStatus.POST_ACCOUNTS_EMPTY_EMAIL);
			}
			if (!isRegexEmail(email)) {
				return new BaseResponse<>(POST_ACCOUNTS_INVALID_EMAIL);
			}

			if (accountProvider.checkIsDuplicatedEmail(email) == 0) {
				return new BaseResponse<>("0");
			} else if (accountProvider.checkHasMembership(email) == null) {
				return new BaseResponse<>("1");
			}
			return new BaseResponse<>("2");
		} catch (BaseException exception) {
			return new BaseResponse<>((exception.getStatus()));
		}
	}


	@PostMapping("/sign-up")
	public BaseResponse<Account.createResDto> createAccount(@RequestBody Account.createReqDto requestDto) {
	    try {
	        String email = requestDto.getEmail();
	        if (!isRegexEmail(email)) {
	            return new BaseResponse<>(POST_ACCOUNTS_INVALID_EMAIL);
	        }
	        if (!isRegexPassword(requestDto.getPassword())) {
	            return new BaseResponse<>(POST_ACCOUNTS_INVALID_PASSWORD);
	        }
	        Account.createResDto createResDto = accountService.createAccount(requestDto);
	        return new BaseResponse<>(createResDto);
	    } catch (BaseException exception) {
	        logger.error(exception.toString());
	        return new BaseResponse<>(exception.getStatus());
	    }
	}


    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostAccountRes> login(@RequestBody PostLoginReq postLoginReq) {
        try {
            PostAccountRes postLoginRes = accountProvider.login(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/logout/{accountIdx}")
    public BaseResponse<PostAccountRes> logout(@PathVariable("accountIdx") int accountIdx) {
        try {
            //JWT. 로그인 회원 확인
            int accountIdxByJwt = jwtService.getUserIdx();
            if (accountIdxByJwt != accountIdx) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_LOGOUT_JWT);
            }

            PostAccountRes postLogoutRes = accountProvider.logout(accountIdx);
            return new BaseResponse<>(postLogoutRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    @ResponseBody
    @PatchMapping("/{accountIdx}/deactivate")
    public BaseResponse<String> deactivateAccount(@PathVariable("accountIdx") int accountIdx
    ) {
        try {
            int accountIdByJwt = jwtService.getUserIdx();
            if (accountIdx != accountIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            Account.DeactivateReqDto deactivateReqDto = new Account.DeactivateReqDto(accountIdx);
            accountService.deactivateAccount(deactivateReqDto);

            String result = "계정이 비활성화 되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<Account.getAccountsDto>> getAccounts(@RequestParam(required = false) String membership) {
        try {
            if (membership == null) { //해당 레코드가 없으면 전체 계정 정보를 불러온다.
                List<Account.getAccountsDto> getAccountsDto = accountProvider.getAccounts();
                return new BaseResponse<>(getAccountsDto);
            }

            // query string인 membership이 있을 경우, 조건을 만족하는 계정 정보를 불러온다.
            List<Account.getAccountsDto> getAccountsRes = accountProvider.getAccountsByMembership(membership);
            return new BaseResponse<>(getAccountsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{accountIdx}")
    public BaseResponse<Account.getResDto> getAccount(@PathVariable("accountIdx") int accountIdx) {
        try {
            Account.getResDto getResDto = accountProvider.getAccount(accountIdx);
            return new BaseResponse<>(getResDto);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @PatchMapping("/password")
    public BaseResponse<String> updatePassword(@RequestBody PatchAccountReq patchAccountReq) {
        try {
            if (!isRegexPassword(patchAccountReq.getUpdateParam())) {
                return new BaseResponse<>(POST_ACCOUNTS_INVALID_EMAIL);
            }

            int accountIdxByJwt = jwtService.getUserIdx();
            if (accountIdxByJwt != patchAccountReq.getAccountIdx()) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }

            accountService.updatePassword(patchAccountReq);
            return new BaseResponse<>("update password success");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @PatchMapping("/email")
    public BaseResponse<String> updateEmail(@RequestBody PatchAccountReq patchAccountReq) {
        try {
            if (!isRegexEmail(patchAccountReq.getUpdateParam())) {
                return new BaseResponse<>(POST_ACCOUNTS_INVALID_EMAIL);
            }

            int accountIdxByJwt = jwtService.getUserIdx();
            if (accountIdxByJwt != patchAccountReq.getAccountIdx()) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }

            accountService.updateEmail(patchAccountReq);
            return new BaseResponse<>("update email success");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/phonenumber")
    public BaseResponse<String> updatePhoneNumber(@RequestBody PatchAccountReq patchAccountReq) {
        try {
            if (!isRegexPhoneNumber(patchAccountReq.getUpdateParam())) {
                return new BaseResponse<>(POST_ACCOUNTS_INVALID_EMAIL);
            }

            int accountIdxByJwt = jwtService.getUserIdx();
            if (accountIdxByJwt != patchAccountReq.getAccountIdx()) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }

            accountService.updatePhoneNumber(patchAccountReq);
            return new BaseResponse<>("update phone success");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/update/membership")
    public BaseResponse<String> updateMemberShip(@RequestBody PatchAccountReq patchAccountReq) {
        try {
            int accountIdxByJwt = jwtService.getUserIdx();
            if (accountIdxByJwt != patchAccountReq.getAccountIdx()) {
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }

            accountService.updateMemberShip(patchAccountReq);
            return new BaseResponse<>("update membership success");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}