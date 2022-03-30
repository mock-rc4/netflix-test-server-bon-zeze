package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    INVALID_USER_MAIL_LOGIN(false,2003,"이 이메일 주소를 사용하는 계정을 찾을 수 없습니다."),
    INVALID_USER_PHONE_LOGIN(false,2003,"이 전화번호를 사용하는 계정을 찾을 수 없습니다. "),
    INVALID_LOGOUT_JWT(false,2003,"로그인 되어있지 않은 유저의 접근입니다."),


    // [POST] /accounts
    POST_ACCOUNTS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_ACCOUNTS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_ACCOUNTS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
	POST_ACCOUNTS_EMPTY_PASSWORD(false,2018,"비밀번호를 입력해주세요."),
	POST_ACCOUNTS_INVALID_PASSWORD(false,2019,"비밀번호 형식을 확인해주세요."),
    POST_ACCOUNTS_INVALID_PHONE_NUMBER(false,2020,"휴대폰번호 형식을 확인해주세요."),
    POST_ACCOUNTS_DEACTIVATED_ACCOUNT(false,2021,"계정이 존재하지 않거나, 탈퇴된 유저입니다."),
	POST_ACCOUNTS_EMPTY_MEMBERSHIP(false,2022,"멤버쉽을 입력해주세요."),
	POST_ACCOUNTS_MUST_AGREE_PERSONAL_INFORMATION(false,2040,"개인 정보 처리 방침에 동의하지 않았습니다."),

    // [PATCH] /accounts
    PATCH_ACCOUNTS_EMAIL_UPDATE_ERROR(false, 2023, "이메일 변경에 실패하였습니다."),
    PATCH_ACCOUNTS_PASSWORD_UPDATE_ERROR(false, 2023, "비밀번호 변경에 실패하였습니다."),
    PATCH_ACCOUNTS_PHONE_UPDATE_ERROR(false, 2023, "핸드폰 변경에 실패하였습니다."),
    PATCH_ACCOUNTS_MEMBERSHIP_UPDATE_ERROR(false, 2023, "멤버쉽 변경에 실패하였습니다."),


    // [POST] /profile
    POST_PROFILE_CREATE_ERROR(false,2030,"프로필 생성을 실패했습니다."),


    // [PATCH] /profile
    PATCH_PROFILE_MANAGE_ERROR(false,2031,"프로필 변경을 실패했습니다."),

    // [GET] /profilePhoto
    GET_PROFILE_PHOTO_ERROR(false,2032,"프로필 사진을 불러오는데 실패했습니다."),

	// [POST] /assessment
	POST_ASSESSMENTS_ALREADY_EXISTS(false,2098,"평가 레코드가 이미 존재합니다."),
	POST_ASSESSMENTS_DOES_NOT_EXISTS(false,2099,"평가 레코드가 존재하지 않습니다."),

	// [POST} /play
	POST_VIDEO_PLAY_ALREADY_EXISTS(false,2100,"재생 기록이 이미 존재합니다."),
	POST_VIDEO_PLAY_DOES_NOT_EXISTS(false,2101,"재생 기록이 존재하지 않습니다."),
	POST_VIDEO_PLAY_INVALID_CURRENT_PLAY_TIME(false,2102,"유효하지 않은 재생 시간입니다."),

	GET_VIDEO_INVALID_VIDEO_IDX(false,2400,"유효하지 않은 비디오 식별자입니다."),

    // [POST] /bookmark
    POST_BOOKMARK_ALREADY_EXISTS(false,2098,"찜하기에 대한 생성이 이미 존재합니다."),

    // [PATCH] /bookmark
    PATCH_BOOKMARK_STATUS_ERROR(false, 2024, "찜하기가 되어있지 않은 상태입니다."),

    // [GET] /video
    GET_VIDEO_SEARCH_ERROR(false, 2030, "검색 결과가 없습니다."),
    GET_VIDEOS_EXISTS_ERROR(false, 2031, "해당 비디오가 존재하지 않습니다."),

    // [GET] /alarm
    GET_ALARM_EXISTS_ERROR(false, 2031, "해당 알람은 존재하지 않습니다."),


	/**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    RESPONSE_CATEGORY_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    SAVE_SEARCH_ERROR(false, 3000, "검색 키워드 저장을 실패하였습니다."),

    // [POST] /accounts
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    FAILED_TO_LOGOUT(false,3014,"로그아웃을 실패하였습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /accounts/{accountIdx}
    MODIFY_FAIL_DEACTIVATE_ACCOUNT(false,4014,"계정 비활성화에 실패하였습니다."),
	//[PATCH] /profiles/{accountIdx}/{profileIdx}
	MODIFY_FAIL_DEACTIVATE_PROFILE(false,4030,"프로필 비활성화에 실패하였습니다."),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

	/** 이후 사용 예정입니다.
	 * 5000 : 외부 API 오류
	 */
	API_FAILED_REQUEST(false, 5000, "외부 API 요청에 실패하였습니다."),
	API_INVALID_HOST(false, 5001, "외부 API HOST에 대한 URL이 입력되지 않았습니다."),
	API_IS_EXPIRED_NAVER_ACCESS_TOKEN(false, 5002, "NAVER-ACCESS-TOKEN 정보를 불러올 수 없습니다. 또는 해당 토큰이 유효하지 않습니다."),
	API_IS_EXPIRED_GOOGLE_ACCESS_TOKEN(false, 5003, "GOOGLE-ACCESS-TOKEN 정보를 불러올 수 없습니다. 또는 해당 토큰이 유효하지 않습니다."),
	API_IS_EXPIRED_LINE_ACCESS_TOKEN(false, 5004, "LINE-ACCESS-TOKEN 정보를 불러올 수 없습니다. 또는 해당 토큰이 유효하지 않습니다."),

	/** 이후 사용 예정입니다.
	 * 6000 : 소셜 로그인 오류
	 */
	SOCIAL_CANNOT_FIND_NAVER_ACCOUNT(false, 6001, "사용 중이신 네이버 계정이 시스템 기록에 있는 계정과 일치하지 않습니다. Teamflix 이메일과 비밀번호를 사용하여 로그인해 주세요."),
	SOCIAL_MUST_AGREE_EMAIL(false, 6002, "이메일 정보 제공이 동의되어야 합니다."),
	SOCIAL_EXISTS_ACCOUNT(false, 6003, "이미 존재하는 계정입니다."),
	SOCIAL_CANNOT_FIND_GOOGLE_ACCOUNT(false, 6004, "사용 중이신 Google 계정이 시스템 기록에 있는 계정과 일치하지 않습니다. Teamflix 이메일과 비밀번호를 사용하여 로그인해 주세요."),
	SOCIAL_CANNOT_FIND_LINE_ACCOUNT(false, 6005, "사용 중이신 Line 계정이 시스템 기록에 있는 계정과 일치하지 않습니다. Teamflix 이메일과 비밀번호를 사용하여 로그인해 주세요."),


	/**
	 * 7000 : SMS 오류
	 */
	SMS_NO_ANY_CONTENT(false, 7000, "내용이 비었습니다."),
	SMS_INVALID_PHONE_NUMBER(false, 7001, "휴대폰 번호는 '-' 없이 10자거나 11자여야 합니다. ex)01012345678 ");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
