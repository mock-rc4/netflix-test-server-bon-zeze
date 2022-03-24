package com.example.demo.src.assessment;

import static com.example.demo.config.BaseResponseStatus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.BaseException;
import com.example.demo.src.assessment.domain.Assessment;

@Service
public class AssessmentProvider {

	private final AssessmentDao assessmentDao;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public AssessmentProvider(AssessmentDao assessmentDao) {
		this.assessmentDao = assessmentDao;
	}

	public int checkHasAssessment(Assessment.createOrModifyDto requestDto) throws BaseException {
		try {
			return assessmentDao.checkHasAssessment(requestDto);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}

	public int getAssessmentStatus(int profileIdx, int videoIdx) throws BaseException {
		try {
			return assessmentDao.getAssessmentStatus(profileIdx, videoIdx);
		} catch (Exception exception) {
			logger.error(exception.toString());
			throw new BaseException(DATABASE_ERROR);
		}
	}
}