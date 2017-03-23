package com.softbistro.survey.confirm.survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softbistro.survey.confirm.survey.component.entity.Answer;
import com.softbistro.survey.confirm.survey.component.interfacee.IVote;
import com.softbistro.survey.response.Response;

@Service
public class VoteService {
	@Autowired
	private IVote iVote;

	/**
	 * Writing answers to questions in the database
	 * 
	 * @param uuid
	 * @return
	 */
	public Response answerOnSurvey(String uuid, List<Answer> answers) {
		return iVote.answerOnSurvey(uuid, answers);
	}
}
