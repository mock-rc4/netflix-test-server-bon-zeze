package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    // 이메일 형식 체크
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

	// 휴대폰 번호 형식 체크
	public static boolean isRegexPhoneNumber(String target) {
		String regex = 	"^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
		return target.matches(regex);
	}

	// 비밀번호 형식 체크
	public static boolean isRegexPassword(String target) {
		String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
		return target.matches(regex);
	}

	// 재생 기록 형식 체크
	public static boolean isRegexVideoPlayTime(float target) {
		String regex = "^([0-9]{2,3})(\\.[0-9]{1,2})?$";
		return Float.toString(target).matches(regex);
	}
}

