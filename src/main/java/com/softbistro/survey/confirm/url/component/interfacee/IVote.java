package com.softbistro.survey.confirm.url.component.interfacee;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.softbistro.survey.confirm.url.component.entity.Answer;
import com.softbistro.survey.confirm.url.component.entity.VotePage;

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
	public ResponseEntity<Object> answerOnSurvey(String uuid, List<Answer> answers);

	/**
	 * Response for site with information about questions
	 * 
	 * @param uuid
	 * @return
	 */
	public ResponseEntity<List<VotePage>> getVotePage(String uuid);
}
