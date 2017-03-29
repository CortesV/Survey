package com.softbistro.survey.confirm.url.component.interfacee;

import java.util.List;

import com.softbistro.survey.confirm.url.component.entity.Answer;
import com.softbistro.survey.response.Response;

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
	public Response answerOnSurvey(String uuid, List<Answer> answers);
}
