package com.softbistro.survey.confirm.url.component.interfacee;

import java.util.List;

import com.softbistro.survey.confirm.url.component.entity.Answer;

/**
 * Using for vote operations
 * 
 * @author zviproject
 *
 */
public interface IVote {

	/**
	 * Writing answers to questions in the database
	 * 
	 * @param uuid
	 * @return
	 */
	public org.springframework.http.ResponseEntity<Object> answerOnSurvey(String uuid, List<Answer> answers);
}
