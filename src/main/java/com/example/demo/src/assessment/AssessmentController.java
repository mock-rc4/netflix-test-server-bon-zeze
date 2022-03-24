package com.example.demo.src.assessment;

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
import com.example.demo.src.assessment.domain.Assessment;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {

	@Autowired
	private final AssessmentService assessmentService;
	@Autowired
	private final AssessmentProvider assessmentProvider;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	public AssessmentController(AssessmentService assessmentService, AssessmentProvider assessmentProvider) {
		this.assessmentService = assessmentService;
		this.assessmentProvider = assessmentProvider;
	}

	@ResponseBody
	@PostMapping("/{profileIdx}/{videoIdx}")
	public BaseResponse<Assessment.createResDto> createAssessment(@PathVariable("profileIdx") int profileIdx,
		@PathVariable("videoIdx") int videoIdx,
		@RequestBody Assessment.createReqDto requestDto) {
		try {
			Assessment.createResDto postLogoutRes = assessmentService.createAssessment(
				new Assessment.createOrModifyDto(profileIdx, videoIdx, requestDto.getStatus()));
			return new BaseResponse<>(postLogoutRes);
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

	@ResponseBody
	@GetMapping("{profileIdx}/{videoIdx}")
	public BaseResponse<Integer> getAssessmentStatus(@PathVariable("profileIdx") int profileIdx,
		@PathVariable("videoIdx") int videoIdx) {
		try {
			int resDto = assessmentProvider.getAssessmentStatus(profileIdx, videoIdx);
			return new BaseResponse<>(resDto);
		} catch (BaseException exception) {
			return new BaseResponse<>((exception.getStatus()));
		}
	}

	@ResponseBody
	@PatchMapping("/{profileIdx}/{videoIdx}")
	public BaseResponse<String> createAssessment(@PathVariable("profileIdx") int profileIdx,
		@PathVariable("videoIdx") int videoIdx,
		@RequestBody Assessment.modifyReqDto requestDto) {
		try {
			assessmentService.modifyAssessment(
				new Assessment.createOrModifyDto(profileIdx, videoIdx, requestDto.getStatus()));
			return new BaseResponse<>("평가가 변경되었습니다.");
		} catch (BaseException exception) {
			return new BaseResponse<>(exception.getStatus());
		}
	}

}
