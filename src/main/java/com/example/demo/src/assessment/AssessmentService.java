package com.example.demo.src.assessment;

import static com.example.demo.config.BaseResponseStatus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.src.assessment.domain.Assessment;

@Service
public class AssessmentService {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final AssessmentDao assessmentDao;
	private final AssessmentProvider assessmentProvider;

	@Autowired
	public AssessmentService(AssessmentDao AssessmentDao, AssessmentProvider AssessmentProvider) {
		this.assessmentDao = AssessmentDao;
		this.assessmentProvider = AssessmentProvider;
	}

	public Assessment.createResDto createAssessment(Assessment.createOrModifyDto requestDto) throws BaseException {
		//  중복된 평가 레코드인지 확인
		if (assessmentProvider.checkHasAssessment(requestDto) == 1) {
			throw new BaseException(POST_ASSESSMENTS_ALREADY_EXISTS);
		}
		try {
			int assessmentIdx = assessmentDao.createAssessment(requestDto);
			return new Assessment.createResDto(assessmentIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public void modifyAssessment(Assessment.createOrModifyDto requestDto) throws BaseException {
		//  중복된 평가 레코드인지 확인
		if (assessmentProvider.checkHasAssessment(requestDto) == 0) {
			throw new BaseException(POST_ASSESSMENTS_DOES_NOT_EXISTS);
		}
		try {
			assessmentDao.modifyAssessment(requestDto);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}
}